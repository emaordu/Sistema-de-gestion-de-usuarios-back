package com.grupo1.demo.dto;

import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupo1.demo.config.Views;

@Data
@JsonView(Views.CrudView.class)
public class UsuarioResponseDTO {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private List<PermisosDTO> permisos; // Lista de permisos
}
