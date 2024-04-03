package com.andjela.diplomski.repository.auth;

import com.andjela.diplomski.entity.PasswordResetToken;
import com.andjela.diplomski.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUser(User user);
}
