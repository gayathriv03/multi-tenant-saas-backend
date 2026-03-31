package com.multitenant.saas.saas_backend.service.impl;

import com.multitenant.saas.saas_backend.config.TenantContext;
import com.multitenant.saas.saas_backend.dto.LoginRequest;
import com.multitenant.saas.saas_backend.dto.LoginResponse;
import com.multitenant.saas.saas_backend.dto.SignupRequest;
import com.multitenant.saas.saas_backend.entity.Role;
import com.multitenant.saas.saas_backend.entity.Tenant;
import com.multitenant.saas.saas_backend.entity.User;
import com.multitenant.saas.saas_backend.exception.InvalidCredentialsException;
import com.multitenant.saas.saas_backend.exception.UserAlreadyExistsException;
import com.multitenant.saas.saas_backend.repository.TenantRepository;
import com.multitenant.saas.saas_backend.repository.UserRepository;
import com.multitenant.saas.saas_backend.service.UserService;
import com.multitenant.saas.saas_backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TenantRepository tenantRepository;

    @Override
    public User signup(SignupRequest request) {

        //Check if email already exists (within tenant ideally)
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("Email already exists");
                });

        // Find existing tenant OR create new one
        Tenant tenant = tenantRepository.findAll()
                .stream()
                .filter(t -> t.getName().equals(request.getTenantName()))
                .findFirst()
                .orElseGet(() -> {
                    Tenant newTenant = Tenant.builder()
                            .name(request.getTenantName())
                            .build();
                    return tenantRepository.save(newTenant);
                });

        // Create user
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .tenant(tenant)
                .build();

        return userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        //Fetch user based on email + tenant
        User user = userRepository.findByEmailAndTenant_Name(
                request.getEmail(),
                request.getTenantName()
        ).orElseThrow(() -> new RuntimeException("User not found"));

        //Validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        // Generate JWT (with tenant inside)
        String token = jwtUtil.generateToken(user);

        return new LoginResponse(token, "Login successful");
    }

    // Tenant-based user fetch
    public List<User> getUsersByTenant() {

        String tenant = TenantContext.getTenant();

        return userRepository.findByTenant_Name(tenant);
    }
}