package com.excellentbook.excellentbook.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value(value = "${app.jwt-secret}")
    private String jwtSecret;

    @Value(value = "${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    //generate token
    public String generateToken(Authentication authentication) {

        String username = authentication.getName();

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // validate JWT token

        public boolean validateToken (String token){

            try {
                Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
                return true;
            } catch (MalformedJwtException ex) {
                throw new RuntimeException("Invalid JWT signature");
            } catch (ExpiredJwtException ex) {
                throw new RuntimeException("Invalid JWT signature");
            } catch (UnsupportedJwtException ex) {
                throw new RuntimeException("Invalid JWT signature");
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException("Invalid JWT signature");
            }
        }
    }