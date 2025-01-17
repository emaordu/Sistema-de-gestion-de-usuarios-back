package com.grupo1.demo.Models;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.grupo1.demo.config.Views;
import com.grupo1.demo.dto.UsuarioDTO;
@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonView(Views.NoCrudView.class) // Incluido en ambas vistas
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    @JsonView(Views.NoCrudView.class) // Incluido en ambas vistas
    private String username;

    @Column(name = "password", nullable = false)
    @JsonView(Views.NoCrudView.class) // Incluido en ambas vistas
    private String password;

    @Column(name = "firstName", nullable = false)
    @JsonView(Views.NoCrudView.class) // Incluido en ambas vistas
    private String firstName;

    @Column(name = "lastName", nullable = false)
    @JsonView(Views.NoCrudView.class) // Incluido en ambas vistas
    private String lastName;

    // Permisos solo presentes en la vista CRUD
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(Views.CrudView.class)
    private List<Permisos> permisos = new ArrayList<>();

    @JsonIgnore //Notacion para que a la hora de mandar la request en el json no se tenga en cuenta
    @OneToOne(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonView(Views.NoCrudView.class)
    private Token token;

    // Constructor que recibe un UsuarioDTO
    public Usuario(UsuarioDTO usuarioDTO) {
        this.firstName = usuarioDTO.getFirstName();
        this.lastName = usuarioDTO.getLastName();
        this.username = usuarioDTO.getUsername();
        this.password = usuarioDTO.getPassword();
    }
    public Usuario(){        
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
