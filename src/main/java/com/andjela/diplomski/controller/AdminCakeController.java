package com.andjela.diplomski.controller;

import com.andjela.diplomski.dto.cake.CakeDto;
import com.andjela.diplomski.service.CakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/cakes")
public class AdminCakeController {

    private final CakeService cakeService;

    @PostMapping
    public ResponseEntity<CakeDto> createCake(@RequestBody CakeDto req) {
        CakeDto cakeDto = cakeService.createCake(req);
        return new ResponseEntity<>(cakeDto, HttpStatus.CREATED);
    }

    @PutMapping("create-multiple")
    public ResponseEntity<List<CakeDto>> createMultipleCakes(@RequestBody CakeDto[] req) {
        List<CakeDto> cakeDtoList = new ArrayList<>();
        for (CakeDto cakeDto : req) {
            cakeService.createCake(cakeDto);
            cakeDtoList.add(cakeDto);
        }
        return new ResponseEntity<>(cakeDtoList, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CakeDto>> getAllCakes() {
        List<CakeDto> cakeDtoList = cakeService.getCakes();
        return new ResponseEntity<>(cakeDtoList, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<CakeDto> updateCake(@PathVariable Long id, @RequestBody CakeDto req) {
        CakeDto cakeDto = cakeService.updateCake(id, req);
        return new ResponseEntity<>(cakeDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCake(@PathVariable Long id) {
        cakeService.deleteCake(id);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }

}
