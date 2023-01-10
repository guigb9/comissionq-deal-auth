package com.barbozaguiii.comissiondealauth.controller.request;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserCreationRequest {
    @NotBlank
    private String login;

    @NotBlank
    private String userPassword;

    @NotBlank
    @Email
    private String email;
}
