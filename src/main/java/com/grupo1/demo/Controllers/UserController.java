

package com.grupo1.demo.Controllers;


import com.grupo1.demo.Jwt.JwtService;
import com.grupo1.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupo1.demo.Auth.AuthRequest;
import com.grupo1.demo.Auth.AuthResponse;
import com.grupo1.demo.Auth.LoginRequest;
import com.grupo1.demo.Auth.LoginResponse;
import com.grupo1.demo.Services.UserService;
import com.grupo1.demo.config.Views;
import com.grupo1.demo.dto.UsuarioDTO;

@RestController
@RequestMapping("/cuentas/API")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepository userRepository;

    /** ENDPOINTS NO CRUD (NO INCLUYE Permisos) **/


    //Loguearse utilizando un token JWT.
    @PostMapping("/login")
    @JsonView(Views.NoCrudView.class)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    //Registrar un nuevo usuario.
    @PostMapping("/register")
    @JsonView(Views.NoCrudView.class)
    public ResponseEntity<?> register(@RequestBody UsuarioDTO usuarioDTO){
        return userService.addUser(usuarioDTO);
    }

    // Autorizar el usuario a un sistema
    @PostMapping("/authorize")
    @JsonView(Views.NoCrudView.class)
    public ResponseEntity<AuthResponse> authorize(@RequestBody AuthRequest authRequest){
        return userService.authUser(authRequest);
    }

    // Obtener un usuario en concreto utilizando el token y su id
    @GetMapping("/users/{userId}")
    @JsonView(Views.NoCrudView.class)
    public ResponseEntity<?> getUserById(@PathVariable("userId") long userId, @RequestParam ("token") String authHeader) {
        return userService.getUserByToken(authHeader, userId);
    }    

    /** ENDPOINTS CRUD (INCLUYE Permisos) **/

    @GetMapping("/isTokenValid")
    public ResponseEntity<?> isTokenValid(@RequestHeader("Authorization") String authHeader) {
        if (!jwtService.isAuthenticationValid(authHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado");
        }
        return ResponseEntity.ok("Token válido");
    }

    // Obtener todos los usuarios de la base de datos
    @GetMapping("/users")
    @JsonView(Views.CrudView.class)
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String authHeader){

        //Valido el token
        if (!jwtService.isAuthenticationValid(authHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autorizado");
        }
        //Si el token es valido devuelve la lista de los usuarios de la base de datos
        return userService.getAllUsers();
    }   

    // Añadir un usuario 
    @PostMapping("/users")
    @JsonView(Views.CrudView.class)
    public ResponseEntity<?> createUser(@RequestBody UsuarioDTO usuarioDTO , @RequestHeader("Authorization") String authHeader){
        
        //Valido el token
        if (!jwtService.isAuthenticationValid(authHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autorizado");
        }

        //Si el token es valido añade un nuevo usuario
        return userService.addUser(usuarioDTO);
    }
    

    // Editar un usuario utilizando su id
    @PutMapping("/users/{userId}")
    @JsonView(Views.CrudView.class)
    public ResponseEntity<?> editUser(@PathVariable("userId") long userId, @RequestBody UsuarioDTO updatedUser, 
    @RequestHeader("Authorization") String authHeader) {

        //Valido el token
        if (!jwtService.isAuthenticationValid(authHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autorizado");
        }
        // Llamar al servicio para editar el usuario
        return userService.editUser(userId, updatedUser);
    }

    // Eliminar un usuario utilizando su id 
    @DeleteMapping("/users/{userId}")
    @JsonView(Views.CrudView.class)
    public ResponseEntity<?> deleteStudent(@PathVariable("userId") long userId , @RequestHeader("Authorization") String authHeader){

        //Valido la autenticacion
        if (!jwtService.isAuthenticationValid(authHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autorizado");
        }
        //Si el token es valido entonces se puede eliminar un usuario por id
        return userService.deleteUserById(userId);
    }
    
    //Cerrar sesion
    @PostMapping("/logout")
    @JsonView(Views.CrudView.class)
    public ResponseEntity<?> deleteToken(@RequestHeader("Authorization") String authHeader){
        return jwtService.logout(authHeader);
    }
}