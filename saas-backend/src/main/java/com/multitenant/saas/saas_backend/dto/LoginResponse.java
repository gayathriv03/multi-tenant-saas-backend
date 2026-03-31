package com.multitenant.saas.saas_backend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String message;
}