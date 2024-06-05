package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.flavor.FlavorCreateDto;
import com.andjela.diplomski.dto.flavor.FlavorDto;
import com.andjela.diplomski.dto.flavor.FlavorMapper;
import com.andjela.diplomski.dto.flavor.FlavorUpdateDto;
import com.andjela.diplomski.entity.codebook.Flavor;
import com.andjela.diplomski.exception.ResourceNotFoundException;
import com.andjela.diplomski.repository.FlavorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlavorService implements IFlavorService {

    private final FlavorRepository flavorRepository;

    @Override
    public FlavorDto createFlavor(FlavorCreateDto flavorCreateDto) {
        Flavor flavor = Flavor.builder()
                .name(flavorCreateDto.getName())
                .description(flavorCreateDto.getDescription())
                .ingredients(flavorCreateDto.getIngredients())
                .allergens(flavorCreateDto.getAllergens())
                .createdAt(LocalDateTime.now())
                .build();

        flavorRepository.save(flavor);
        FlavorDto flavorDto = FlavorMapper.MAPPER.mapToFlavorDto(flavor);
        return flavorDto;
    }

    @Override
    public FlavorDto getFlavorById(Long flavorId) {
        Flavor flavor = flavorRepository.findById(flavorId).orElseThrow(() -> new ResourceNotFoundException("Didn't find flavor with id:" + flavorId));
        FlavorDto flavorDto = FlavorMapper.MAPPER.mapToFlavorDto(flavor);
        return flavorDto;
    }

    @Override
    public List<FlavorDto> getFlavors() {
        List<FlavorDto> foundFlavors = new ArrayList<>();
        List<Flavor> flavors = flavorRepository.findAll();

        for (Flavor flavor : flavors) {
            FlavorDto flavorDto = FlavorMapper.MAPPER.mapToFlavorDto(flavor);
            foundFlavors.add(flavorDto);
        }
        return foundFlavors;
    }

    @Override
    public FlavorDto updateFlavor(Long id, FlavorUpdateDto flavorUpdateDto) {
        Flavor foundFlavor = flavorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Didn't find flavor with id:" + id));

        foundFlavor.setName(flavorUpdateDto.getName());
        foundFlavor.setDescription(flavorUpdateDto.getDescription());
        foundFlavor.setIngredients(flavorUpdateDto.getIngredients());
        foundFlavor.setAllergens(flavorUpdateDto.getAllergens());
        foundFlavor.setUpdatedAt(LocalDateTime.now());

        flavorRepository.save(foundFlavor);

        return FlavorMapper.MAPPER.mapToFlavorDto(foundFlavor);
    }

    @Override
    public void deleteFlavor(Long flavorId) {
        if(!flavorRepository.existsById(flavorId)){
            throw new ResourceNotFoundException("Didn't find flavor with id:" + flavorId);
        }
        flavorRepository.deleteById(flavorId);
    }
}
