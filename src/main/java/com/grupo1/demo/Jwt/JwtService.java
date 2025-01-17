package com.grupo1.demo.Jwt;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grupo1.demo.Models.Token;
import com.grupo1.demo.Models.Usuario;
import com.grupo1.demo.Repositories.TokenRepository;
import com.grupo1.demo.Repositories.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    // Genera una clave aleatoria para la firma del token
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expirationTimeMs = 1000 * 60 * 30; // 30 min

    
    /**
     * Genera un token y lo asocia a un usuario.
     */
    public String generateAndStoreToken(Usuario usuario) {
        // Si ya existe un token para este usuario, eliminarlo
        Token existingToken = tokenRepository.findByUser_username(usuario.getUsername());
        if (existingToken != null) {
            tokenRepository.delete(existingToken);
        }

        // Generar el nuevo token
        String tokenValue = generateToken(usuario.getUsername(), usuario.getId());

        // Crear y persistir el token en la base de datos
        Token token = new Token();
        token.setToken(tokenValue);
        token.setExpiresAt(new Date(System.currentTimeMillis() + expirationTimeMs));
        token.setUser(usuario);
        tokenRepository.save(token);

        return tokenValue;
    }

    /**
     * Genera un token JWT con claims personalizados.
     */
    public String generateToken(String username, Long userId) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId) // Agregar el userId como claim personalizado
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Obtiene el username almacenado en el token.
     */
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Verifica si el token es válido para un usuario específico.
     */
    public boolean isTokenValid(String token, Usuario usuario) {
        final String username = getUsernameFromToken(token);
        Token storedToken = tokenRepository.findByUser_username(usuario.getUsername());

        // Verificar que el token sea válido y no haya expirado
        return storedToken != null &&
               storedToken.getToken().equals(token) &&
               username.equals(usuario.getUsername()) &&
               !isTokenExpired(token);
    }

    /**
     * Valida si el token ha expirado.
     */
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    /**
     * Extrae un claim específico del token.
     */
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Obtiene del token la fecha de caducidad del token 
     * @param token el token del que va a extraer la fecha de caducidad
     * @return la fecha de caducidad del token
     */
    public Date getExpiration(String token) {
        // Extracts the expiration claim from the token
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae todos los claims del token.
     */
    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Metodo para verificar si el header de autorizacion contiene un token valido
     * y si pertenece a un usuario existente de la base de datos
     * @param authHeader es el header de autorizacion que debe incluir el token en el formato "Bearer {token}" 
     * @return "True" si el header contiene un token válido y el usario asociado existe, de lo contrario "False".
     */
    public boolean isAuthenticationValid(String authHeader){
        //Verifica que el header no sea nulo y tenga un formato válido
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;
        }

        //Extrae el token
        String token = authHeader.substring(7);

        //Verifica que el token no esté expirado
        if (verifyTokenExpiresAt(token)) {
            return false; // Si el token ha expirado, retorna false
        }

        //Obtiene el nombre de usuario del token
        String userName = getUsernameFromToken(token);

        //Obtiene el usuario de la base de datos con el nombre que obtuvo del token
        Usuario usuario = userRepository.findByUsername(userName);
        /*
         * Valida el token del usuario
         * Verifica que el usuario existe y luego que el token es valido para ese usuario
         */
        return usuario != null && isTokenValid(token, usuario); 
    }

    /**
     * Elimina un token de la base de datos.
     * @param authHeader El Header de autenticación que contiene el token
     * @return {@code True} si el token fue eliminado, {@code False} si no se encontró el token
     */
    public ResponseEntity<String> logout(String authHeader) {

        //Verifica que el header no sea nulo y tenga un formato válido
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.badRequest().body("Token invalido");
        }

        //Extrae el token del header quitando el prefijo "Bearer "
        String token = authHeader.substring(7);

        //Intenta eliminar el token de la base de datos
        boolean deleted = deleteToken(token);

        //Si el token fue eliminado, devuelve una respuesta de éxito
        if (deleted) {
            return ResponseEntity.ok("Token eliminado");
        } else {
            
            //Si no se encontró el token, devuelve un 404, indicando que no se encontró el token
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token no encontrado");
        }
    }
    
    /**
     * Elimina un token de la base de datos.
     * @param token el token a eliminar
     * @return {@code True} si el token fue eliminado, {@code False} si no se encontró el token
     */
    private boolean deleteToken(String token) {
        
        // Busco el token en la base de datos
        Token storedToken = tokenRepository.findByToken(token);

        // Si lo encuentro lo elimino
        if (storedToken != null) {
            tokenRepository.delete(storedToken);
            return true; // Indica que fue eliminado
        }

        // Si no lo encuentro, devuelve falso
        return false;
    }

    /**
     * Verifica si el token ha expirado.
     * @param token El token a verificar
     * @return {@code True} si el token ha expirado, {@code False} en caso contrario
     */
    public boolean verifyTokenExpiresAt (String token){
        
        //Obtenemos la fecha de caducidad del token
        Date expiresDate = getClaim(token, Claims::getExpiration);
        
        //Obtenemos la fecha actual
        Date currentDate = new Date();
        
        //Verificamos si la fecha de caducidad del token es menor a la fecha actual
        if (expiresDate.before(currentDate)) {
            return true;
        }
        return false;
    }

}