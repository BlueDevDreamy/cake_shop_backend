package com.andjela.diplomski.service.auth;

import com.andjela.diplomski.dto.auth.LoginRequestDto;
import com.andjela.diplomski.dto.auth.LoginResponseDto;
import com.andjela.diplomski.entity.RefreshJwtToken;
import com.andjela.diplomski.security.JwtTokenProvider;
import com.andjela.diplomski.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    public LoginResponseDto login(LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtTokenProvider.generateJwtToken(authentication);

        Claims claims = JwtUtils.parseJwtClaims(accessToken, jwtSecret);
        Date expiration = claims.getExpiration();
        RefreshJwtToken refreshJwtToken = refreshTokenService.createRefreshToken(authentication);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(expiration)
                .refreshTokenExpiresAt(Date.from(refreshJwtToken.getExpiryDate().toInstant()))
                .refreshToken(refreshJwtToken.getToken())
                .roles(authentication.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toSet()))
                .build();
    }
}
