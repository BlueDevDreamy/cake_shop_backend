package com.andjela.diplomski.repository;

import com.andjela.diplomski.entity.codebook.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.name=:name")
    Category findByNameAndParent(@Param("name") String name);
}
