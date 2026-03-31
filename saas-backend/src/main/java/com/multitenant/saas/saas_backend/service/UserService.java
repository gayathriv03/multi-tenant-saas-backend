package com.multitenant.saas.saas_backend.service;

import com.multitenant.saas.saas_backend.dto.LoginRequest;
import com.multitenant.saas.saas_backend.dto.LoginResponse;
import com.multitenant.saas.saas_backend.dto.SignupRequest;
import com.multitenant.saas.saas_backend.entity.User;

public interface UserService {

    User signup(SignupRequest request);

    LoginResponse login(LoginRequest request);
}