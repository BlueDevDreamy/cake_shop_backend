package com.andjela.diplomski.repository.auth;

import com.andjela.diplomski.entity.RefreshJwtToken;
import com.andjela.diplomski.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshJwtTokenRepository extends JpaRepository<RefreshJwtToken, Long> {
    Optional<RefreshJwtToken> findByToken(String token);
    Optional<RefreshJwtToken> findByUserId(Long userId);
    @Modifying
    int deleteByUser(User user);
}
