package com.grupo1.demo.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private List<Long> sistemaIds; // IDs de sistemas
}
