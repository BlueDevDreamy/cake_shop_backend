package com.andjela.diplomski.repository.auth;

import com.andjela.diplomski.entity.RegistrationToken;
import com.andjela.diplomski.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {
    Optional<RegistrationToken> findByUser(User user);
    Optional<RegistrationToken> findByToken(String token);
}
