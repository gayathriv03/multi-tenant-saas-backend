package com.multitenant.saas.saas_backend.controller;

import com.multitenant.saas.saas_backend.dto.LoginRequest;
import com.multitenant.saas.saas_backend.dto.LoginResponse;
import com.multitenant.saas.saas_backend.dto.SignupRequest;
import com.multitenant.saas.saas_backend.entity.User;
import com.multitenant.saas.saas_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public User signup(@RequestBody SignupRequest request) {
        return userService.signup(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}