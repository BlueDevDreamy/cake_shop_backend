package com.andjela.diplomski.security;

import com.andjela.diplomski.entity.User;
import com.andjela.diplomski.exception.ApiValidationException;
import com.andjela.diplomski.exception.AppException;
import com.andjela.diplomski.repository.UserRepository;
import com.andjela.diplomski.service.auth.JwtTokenCacheService;
import com.andjela.diplomski.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JWTTokenValidatorFilter extends OncePerRequestFilter {
    private final String jwtSecret;
    private final UserRepository userRepository;
    private final JwtTokenCacheService jwtTokenCacheService;

    public JWTTokenValidatorFilter(
            String jwtSecret,
            UserRepository userRepository,
            JwtTokenCacheService jwtTokenCacheService) {
        this.jwtSecret = jwtSecret;
        this.userRepository = userRepository;
        this.jwtTokenCacheService = jwtTokenCacheService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = JwtUtils.getJwtTokenFromRequest(request);
        if (jwt != null) {
            try {

                String blacklistedToken = jwtTokenCacheService.getJwtBlackList(jwt);
                if (blacklistedToken != null) {
                    throw new AppException("Token is blacklisted");
                }

                Claims claims = JwtUtils.parseJwtClaims(jwt, jwtSecret);
                String issuer = claims.getIssuer();
                if(issuer == null || !issuer.equals(JwtUtils.ISSUER)) {
                    throw new ApiValidationException("ISSUER does not match!");
                }
                String purpose = (String) claims.get("purpose");
                if(purpose == null || !purpose.equals(JwtUtils.CLAIM_PURPOSE_AUTH)) {
                    throw new ApiValidationException("Purpose claim does not match!");
                }
                String username = claims.getSubject();
                String authorities = (String) claims.get("authorities");

                User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Not found username " + username));
                SecurityUser securityUser = new SecurityUser(user);
                Authentication auth = new UsernamePasswordAuthenticationToken(securityUser, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                log.error("Cannot set user authentication: {}", e.getMessage());
            }

        }
        filterChain.doFilter(request, response);
    }
}
