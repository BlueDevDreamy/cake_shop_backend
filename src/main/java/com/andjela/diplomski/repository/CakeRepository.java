package com.andjela.diplomski.repository;

import com.andjela.diplomski.entity.Cake;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CakeRepository extends JpaRepository<Cake, Long> {
}
