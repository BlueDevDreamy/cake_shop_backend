package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.cake.CakeDto;

import java.util.List;

public interface ICakeService {
    CakeDto createCake(CakeDto cakeDto);
    CakeDto getCakeById(Long cakeId);
    List<CakeDto> getCakes();
    CakeDto updateCake(Long id, CakeDto cakeDto);
    void deleteCake(Long cakeId);
}
