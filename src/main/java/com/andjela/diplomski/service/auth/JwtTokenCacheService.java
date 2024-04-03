package com.andjela.diplomski.service.auth;

import org.springframework.stereotype.Service;

@Service
public class JwtTokenCacheService {
    public String blackListJwt(String jwt) {
        return jwt;
    }

    public String getJwtBlackList(String jwt) {
        return null;
    }
}
