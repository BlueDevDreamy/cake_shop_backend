package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.flavor.FlavorCreateDto;
import com.andjela.diplomski.dto.flavor.FlavorDto;
import com.andjela.diplomski.dto.flavor.FlavorUpdateDto;

import java.util.List;

public interface IFlavorService {
    FlavorDto createFlavor(FlavorCreateDto flavorCreateDto);
    FlavorDto getFlavorById(Long flavorId);
    List<FlavorDto> getFlavors();
    FlavorDto updateFlavor(Long id, FlavorUpdateDto flavorUpdateDto);
    void deleteFlavor(Long flavorId);
}
