package com.andjela.diplomski.security;

import com.andjela.diplomski.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-minutes}")
    private Long jwtExpirationTimeMinutes;

    public String generateJwtToken(Authentication authentication) {
        return JwtUtils.generateJwtToken(authentication, jwtSecret, jwtExpirationTimeMinutes);
    }

    @CacheEvict("jwtCache")
    public void revokeJwtToken(String jwtToken) {
        log.info("Removing JWT from cache " + jwtToken);
    }
}
