package com.grupo1.demo.Auth;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupo1.demo.config.Views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonView(Views.NoCrudView.class)
public class LoginResponse {
    private String token;       // Token de autenticación.
    private String userId;      // ID del usuario autenticado.
    private long expiresIn;     // Tiempo de expiración del token en segundos.
}
