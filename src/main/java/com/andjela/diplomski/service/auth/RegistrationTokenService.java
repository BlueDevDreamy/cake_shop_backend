package com.andjela.diplomski.service.auth;

import com.andjela.diplomski.entity.RegistrationToken;
import com.andjela.diplomski.entity.User;
import com.andjela.diplomski.repository.auth.RegistrationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class RegistrationTokenService {
    private final RegistrationTokenRepository registrationTokenRepository;

    public RegistrationToken createRegistrationTokenForUser(final User user) {
        final String token = UUID.randomUUID().toString();
        RegistrationToken registrationToken = RegistrationToken.builder()
                .token(token)
                .user(user)
                .expiryDate(ZonedDateTime.now().plusMinutes(RegistrationToken.EXPIRATION_MINUTES))
                .createdAt(LocalDateTime.now())
                .build();
        registrationTokenRepository.save(registrationToken);
        return registrationToken;
    }
}
