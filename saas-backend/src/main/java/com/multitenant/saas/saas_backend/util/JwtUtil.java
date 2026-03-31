package com.multitenant.saas.saas_backend.util;

import com.multitenant.saas.saas_backend.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;


@Component
public class JwtUtil {

    private static final String SECRET_KEY = "your-256-bit-secret-your-256-bit-secret"; // min 32 chars
    private final long EXPIRATION = 1000 * 60 * 60; // 1 hour

//    private Key getSignKey() {
//        return Keys.hmacShaKeyFor(SECRET.getBytes());
//    }

    public String generateToken(User user) {

        Map<String, Object> claims = new HashMap<>();

        // ✅ ADD TENANT HERE
        claims.put("tenant", user.getTenant().getName());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractTenant(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("tenant", String.class);
    }
}
