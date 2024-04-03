package com.andjela.diplomski.controller;

import com.andjela.diplomski.dto.product.ProductDto;
import com.andjela.diplomski.entity.Product;
import com.andjela.diplomski.service.ProductService;
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
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
        ProductDto productDto = productService.getProductById(id);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Page<Product>> getProductsByCategory(@RequestParam String category,
                                                                  @RequestParam List<Double> weights,
                                                                  @RequestParam List<String> flavors,
                                                                  @RequestParam Integer minPrice,
                                                                  @RequestParam Integer maxPrice,
                                                                  @RequestParam Integer minDiscount,
                                                                  @RequestParam String sort,
                                                                  @RequestParam Integer pageNumber,
                                                                  @RequestParam Integer pageSize){
        Page<Product> result = productService.getProductsPageable(category, weights, flavors, minPrice, maxPrice, minDiscount, sort, pageNumber, pageSize);
        System.out.println("complete products");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//ToDo
//    @GetMapping("/search")
//    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam String q){
//        List<Product> products = productService.
//    }
}
