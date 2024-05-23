package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.cake.CakeCreateDto;
import com.andjela.diplomski.dto.cake.CakeDto;
import com.andjela.diplomski.dto.cake.CakeUpdateDto;
import com.andjela.diplomski.entity.Cake;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICakeService {
//    CakeDto createCake(CakeDto cakeDto);
    CakeDto createCake(CakeCreateDto cakeDto);
    CakeDto getCakeById(Long cakeId);
    List<CakeDto> getCakes();
    CakeDto updateCake(Long id, CakeUpdateDto cakeDto);
    void deleteCake(Long cakeId);

    List<CakeDto> findCakeByCategory(String category);
    List<CakeDto> searchCakes(String query);
    Page<Cake> getAllCakesPageable(String category, int minWeight, int maxWeight, int minTier, int maxTier, String sort, Integer pageNumber, Integer pageSize);
    List<CakeDto> getAllCakes();
}
