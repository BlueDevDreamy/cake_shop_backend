package com.andjela.diplomski.repository.auth;

import com.andjela.diplomski.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
