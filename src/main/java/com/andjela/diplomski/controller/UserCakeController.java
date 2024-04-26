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

    //ToDo getCakesPageable
//    @GetMapping("/cakes")
//    public ResponseEntity<Page<Cake>> getProductByCategory(@RequestParam String category,
//                                                           @RequestParam List<String> flavor,
//                                                           @RequestParam int minPrice,
//                                                           @RequestParam int maxPrice,
//                                                           @RequestParam String sort,
//                                                           @RequestParam int pageNumber,
//                                                           @RequestParam int pageSize) {
//
//
//        Page<Cake> res = cakeService.getCakesPageable(category,
//                                                                flavor,
//                                                                minPrice,
//                                                                maxPrice,
//                                                                sort,
//                                                                pageNumber,
//                                                                pageSize);
//
//        System.out.println("complete products");
//        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
//    }

    @GetMapping("/cakes/id/{cakeId}")
    public ResponseEntity<CakeDto> findCakeById(@PathVariable Long cakeId) {
        CakeDto cakeDto = cakeService.getCakeById(cakeId);
        return new ResponseEntity<>(cakeDto, HttpStatus.ACCEPTED);
    }
    //ToDo searchCake
//    @GetMapping("/cake/search")
//    public ResponseEntity<List<CakeDto>> searchCakeHandler(@RequestParam String q) {
//        List<CakeDto> productsDto = cakeService.searchCake(q);
//        return new ResponseEntity<>(productsDto, HttpStatus.OK);
//    }
}
