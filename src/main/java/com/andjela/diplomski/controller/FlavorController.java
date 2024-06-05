package com.andjela.diplomski.controller;

import com.andjela.diplomski.dto.flavor.FlavorCreateDto;
import com.andjela.diplomski.dto.flavor.FlavorDto;
import com.andjela.diplomski.dto.flavor.FlavorUpdateDto;
import com.andjela.diplomski.service.FlavorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/flavors")
public class FlavorController {
    private final FlavorService flavorService;

    @PostMapping
    public ResponseEntity<FlavorDto> createFlavor(@RequestBody FlavorCreateDto flavorCreateDto) {
        FlavorDto flavorDto = flavorService.createFlavor(flavorCreateDto);
        return new ResponseEntity<>(flavorDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FlavorDto>> getAllFlavors() {
        List<FlavorDto> flavorDtoList = flavorService.getFlavors();
        return new ResponseEntity<>(flavorDtoList, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<FlavorDto> updateFlavor(@PathVariable Long id, @RequestBody FlavorUpdateDto flavorUpdateDto) {
        FlavorDto flavorDto = flavorService.updateFlavor(id, flavorUpdateDto);
        return new ResponseEntity<>(flavorDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<FlavorDto> deleteFlavor(@PathVariable Long id) {
        flavorService.deleteFlavor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
