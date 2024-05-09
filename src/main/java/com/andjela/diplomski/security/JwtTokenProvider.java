package com.andjela.diplomski.security;

import com.andjela.diplomski.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${app.jwt.secret}")
    private String jwtSecret;

//    private SecretKey key= Keys.hmacShaKeyFor(jwtSecret.getBytes());


    @Value("${app.jwt.expiration-minutes}")
    private Long jwtExpirationTimeMinutes;

    public String generateJwtToken(Authentication authentication) {
        return JwtUtils.generateJwtToken(authentication, jwtSecret, jwtExpirationTimeMinutes);
    }

    //Added
    public String getEmailFromJwtToken(String jwt) {
        jwt = jwt.substring(7);
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(jwt).getBody();
        String email = String.valueOf(claims.get("sub"));
        return email;
    }

    @CacheEvict("jwtCache")
    public void revokeJwtToken(String jwtToken) {
        log.info("Removing JWT from cache " + jwtToken);
    }
}
