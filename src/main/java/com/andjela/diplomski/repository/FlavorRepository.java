package com.andjela.diplomski.repository;

import com.andjela.diplomski.entity.codebook.Flavor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FlavorRepository extends JpaRepository<Flavor, Long> {
}
