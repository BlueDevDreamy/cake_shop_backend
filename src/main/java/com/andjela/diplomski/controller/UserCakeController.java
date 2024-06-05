package com.andjela.diplomski.controller;

import com.andjela.diplomski.dto.cake.CakeDto;
import com.andjela.diplomski.entity.Cake;
import com.andjela.diplomski.service.CakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-cake")
public class UserCakeController {
    private final CakeService cakeService;

    //Istestirano u POSTMAN-u
    @GetMapping("/cakes")
    public ResponseEntity<Page<Cake>> getAllCakesPageable(@RequestParam String category,
                                                         @RequestParam int minWeight,
                                                         @RequestParam int maxWeight,
                                                         @RequestParam int minTier,
                                                         @RequestParam int maxTier,
                                                         @RequestParam String sort,
                                                         @RequestParam int pageNumber,
                                                         @RequestParam int pageSize) {


        Page<Cake> res = cakeService.getAllCakesPageable(category,
                minWeight,
                maxWeight,
                minTier,
                maxTier,
                sort,
                pageNumber,
                pageSize);

        System.out.println("complete cakes");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    //Istestirano u POSTMAN-u
    @GetMapping("/cakes/{cakeId}")
    public ResponseEntity<CakeDto> findCakeById(@PathVariable Long cakeId) {
        CakeDto cakeDto = cakeService.getCakeById(cakeId);
        return new ResponseEntity<>(cakeDto, HttpStatus.ACCEPTED);
    }

    //Istestirano u POSTMAN-u
    @GetMapping("/cake/category")
    public ResponseEntity<List<CakeDto>> findCakeByCategory(@RequestParam String q) {
        List<CakeDto> cakeDtoList = cakeService.findCakeByCategory(q);
        return new ResponseEntity<>(cakeDtoList, HttpStatus.OK);
    }

    //Istestirano u POSTMAN-u
    @GetMapping("/cake/search")
    public ResponseEntity<List<CakeDto>> searchCakeHandler(@RequestParam String q) {
        List<CakeDto> cakeDtoList = cakeService.searchCakes(q);
        System.out.println("Lista torti " + cakeDtoList);
        return new ResponseEntity<>(cakeDtoList, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<CakeDto>> findAllCakes() {
        List<CakeDto> cakeDtoList = cakeService.getAllCakes();
        return new ResponseEntity<>(cakeDtoList, HttpStatus.OK);
    }
}
