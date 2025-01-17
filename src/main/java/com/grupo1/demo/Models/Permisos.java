package com.grupo1.demo.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.grupo1.demo.config.Views;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "permisos")
public class Permisos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permisos" , nullable = false)
    private long id_permiso;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario", nullable = false)
    @JsonBackReference 
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sistema", nullable = false)
    private Sistema sistema;

    // Getter para el sistemaId
    @JsonProperty("systemId")
    @JsonView(Views.CrudView.class)
    public Long getSistemaId() {
        return sistema != null ? sistema.getId() : null;
    }

    // Getter para el sistemaNombre
    @JsonProperty("name")
    @JsonView(Views.CrudView.class)
    public String getSistemaNombre() {
        return sistema != null ? sistema.getNombre() : null;
    }
}
