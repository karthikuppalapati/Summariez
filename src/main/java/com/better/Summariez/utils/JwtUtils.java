package com.better.Summariez.utils;

import com.better.Summariez.services.BookServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Component
public class JwtUtils {

    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(JwtUtils.class);

    @Value("${jwt.tokenValiditySeconds}")
    private Long jwtValidityInSecs;

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(String emailId) {
        Key key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());

        return Jwts.builder()
                .setSubject(emailId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtValidityInSecs * 1000))
                .signWith(SignatureAlgorithm.HS512, key)
//                .signWith()
                .compact();
    }

    public Claims getClaims(String token) {
        try {
            String jwtToken = token.split(" ")[1];
            Key key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());
            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwtToken)
                    .getBody();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public boolean isValidToken(Claims claims) {
        if(claims != null && !claims.isEmpty()) {
            return claims.getExpiration().after(new Date(System.currentTimeMillis()));
        }
        return false;
    }

    public String getEmailFromToken(String token) {
        Claims claims = getClaims(token);
        return isValidToken(claims) ? claims.getSubject() : null;
    }
}
