package com.grupo1.demo.Auth;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupo1.demo.config.Views;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
@JsonView(Views.NoCrudView.class)
public class AuthResponse {
    private boolean authorized;
}
