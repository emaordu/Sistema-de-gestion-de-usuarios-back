package com.grupo1.demo.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.grupo1.demo.config.Views;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@JsonView(Views.NoCrudView.class)
public class UserResponse {
    private String userName;
    private String firstName;
    private String lastName;
    
    public UserResponse(String userName, String firstName, String lastName) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
