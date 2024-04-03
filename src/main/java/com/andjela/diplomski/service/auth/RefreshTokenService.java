package com.andjela.diplomski.service.auth;

import com.andjela.diplomski.dto.auth.TokenRefreshResponseDto;
import com.andjela.diplomski.entity.RefreshJwtToken;
import com.andjela.diplomski.entity.User;
import com.andjela.diplomski.exception.ResourceNotFoundException;
import com.andjela.diplomski.exception.TokenRefreshException;
import com.andjela.diplomski.repository.auth.RefreshJwtTokenRepository;
import com.andjela.diplomski.repository.UserRepository;
import com.andjela.diplomski.security.SecurityUser;
import com.andjela.diplomski.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class RefreshTokenService {
    @Value("${app.jwt.expiration-refresh-minutes}")
    private Long refreshTokenDurationMinutes;

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Autowired
    private RefreshJwtTokenRepository refreshJwtTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshJwtToken> findByRefreshToken(String token) {
        return refreshJwtTokenRepository.findByToken(token);
    }

    public RefreshJwtToken createRefreshToken(Authentication authentication) {
        SecurityUser principal = (SecurityUser) authentication.getPrincipal();
        Long userId = principal.getId();
        RefreshJwtToken refreshJwtToken = refreshJwtTokenRepository.findByUserId(userId).orElse(null);
        if(refreshJwtToken == null) {
            refreshJwtToken = new RefreshJwtToken();
        }
        refreshJwtToken.setUser(userRepository.findById(userId).get());
        refreshJwtToken.setExpiryDate(ZonedDateTime.now().plusMinutes(refreshTokenDurationMinutes));
        String token = JwtUtils.generateJwtToken(authentication, jwtSecret, refreshTokenDurationMinutes);
        refreshJwtToken.setToken(token);
        refreshJwtToken.setTokenHashCode(token.hashCode());
        if(refreshJwtToken.getCreatedAt() == null) {
            refreshJwtToken.setCreatedAt(LocalDateTime.now());
        } else {
            refreshJwtToken.setUpdatedAt(LocalDateTime.now());
        }

        refreshJwtToken = refreshJwtTokenRepository.save(refreshJwtToken);
        return refreshJwtToken;
    }

    public RefreshJwtToken verifyExpiration(RefreshJwtToken refreshJwtToken) {
        if (refreshJwtToken.getExpiryDate().compareTo(ZonedDateTime.now()) < 0) {
            refreshJwtTokenRepository.delete(refreshJwtToken);
            throw new TokenRefreshException(refreshJwtToken.getToken(), "Refresh token je istekao.");
        }

        return refreshJwtToken;
    }

    @Transactional
    public int deleteRefreshTokenByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Didn't find user with id " + userId));
        return refreshJwtTokenRepository.deleteByUser(user);
    }

    public TokenRefreshResponseDto refreshToken(String requestRefreshToken) {
        return findByRefreshToken(requestRefreshToken)
                .map(rf -> verifyExpiration(rf))
                .map(RefreshJwtToken::getUser)
                .map(user -> {
                    String token = JwtUtils.generateJwtToken(user, jwtSecret, refreshTokenDurationMinutes);
                    return TokenRefreshResponseDto.builder().accessToken(token).refreshToken(requestRefreshToken).build();
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Nije pronadjen refresh token!"));
    }

}
