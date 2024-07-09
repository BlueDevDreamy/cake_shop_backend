package com.andjela.diplomski.controller;

import com.andjela.diplomski.dto.cake.CakeCreateDto;
import com.andjela.diplomski.dto.cake.CakeDto;
import com.andjela.diplomski.dto.cake.CakeUpdateDto;
import com.andjela.diplomski.dto.category.CategoryDto;
import com.andjela.diplomski.service.CakeService;
import com.andjela.diplomski.service.CategoryService;
import jakarta.validation.Valid;
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
    private final CategoryService categoryService;

    //Istestirano u POSTMAN-u
    @PostMapping
    public ResponseEntity<CakeDto> createCake(@Valid @RequestBody CakeCreateDto req) {
        CakeDto cakeDto = cakeService.createCake(req);
        return new ResponseEntity<>(cakeDto, HttpStatus.CREATED);
    }

    //Sa novim CreateDto ne verujm da moze multuiple kreiranje
    //Istestirano u POSTMAN-u
//    @PostMapping("create-multiple")
//    public ResponseEntity<List<CakeDto>> createMultipleCakes(@RequestBody CakeCreateDto[] req) {
//        List<CakeDto> cakeDtoList = new ArrayList<>();
//        for (CakeCreateDto cakeCreateDto : req) {
//            cakeService.createCake(cakeCreateDto);
//            cakeDtoList.add(cakeCreateDto);
//        }
//        return new ResponseEntity<>(cakeDtoList, HttpStatus.CREATED);
//    }

    //Istestirano u POSTMAN-u
    @GetMapping
    public ResponseEntity<List<CakeDto>> getAllCakes() {
        List<CakeDto> cakeDtoList = cakeService.getCakes();
        return new ResponseEntity<>(cakeDtoList, HttpStatus.OK);
    }

    //Istestirano u POSTMAN-u
    @PutMapping("{id}")
    public ResponseEntity<CakeDto> updateCake(@PathVariable Long id, @Valid @RequestBody CakeUpdateDto req) {
        CakeDto cakeDto = cakeService.updateCake(id, req);
        return new ResponseEntity<>(cakeDto, HttpStatus.OK);
    }

    //Istestirano u POSTMAN-u
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCake(@PathVariable Long id) {
        cakeService.deleteCake(id);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryDtoList, HttpStatus.OK);
    }

}
