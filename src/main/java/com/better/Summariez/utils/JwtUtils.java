package com.better.Summariez.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    @Value("${jwt.tokenValiditySeconds}")
    private Long jwtValidityInSecs;

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(String emailId) {
        return Jwts.builder()
                .setSubject(emailId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtValidityInSecs * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public void validateToken(String token) {

    }
}
