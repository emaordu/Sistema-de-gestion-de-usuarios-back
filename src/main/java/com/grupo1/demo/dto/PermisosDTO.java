package com.grupo1.demo.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupo1.demo.config.Views;

import lombok.Data;

@Data
@JsonView(Views.CrudView.class)
public class PermisosDTO {
    private Long systemId;  // ID del sistema relacionado
    private String name;    // Nombre del sistema relacionado
}