package com.andjela.diplomski.repository;

import com.andjela.diplomski.entity.Cake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CakeRepository extends JpaRepository<Cake, Long> {


    @Query("SELECT c From Cake c Where c.category.name=:category")
    List<Cake> findByCategory(@Param("category") String category);

    @Query(value = "SELECT c FROM Cake c " +
            "WHERE c.title LIKE %:query% " +
            "OR c.category.name LIKE %:query%")
    List<Cake> searchCakes(@Param("query") String query);


    @Query("SELECT c FROM Cake c " +
            "WHERE (c.category.name = :category OR :category = '') " +
            "AND (c.minWeight BETWEEN :minWeight AND :maxWeight) " +
            "AND (c.minTier BETWEEN :minTier AND :maxTier) " +
            "ORDER BY " +
            "CASE WHEN :sort = 'price_low' THEN c.pricePerKilo END ASC, " +
            "CASE WHEN :sort = 'price_high' THEN c.pricePerKilo END DESC")
    List<Cake> filterCakes(
            @Param("category") String category,
            @Param("minWeight") int minWeight,
            @Param("maxWeight") int maxWeight,
            @Param("minTier") int minTier,
            @Param("maxTier") int maxTier,
            @Param("sort") String sort
    );
}
