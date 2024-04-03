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
@RequestMapping("/api/user-products")
public class UserProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProductByCategory(@RequestParam String category,
                                                                      @RequestParam List<Double> weight,
                                                                      @RequestParam List<String> flavor,
                                                                      @RequestParam Integer minPrice,
                                                                      @RequestParam Integer maxPrice,
                                                                      @RequestParam Integer minDiscount,
                                                                      @RequestParam String sort,
                                                                      @RequestParam Integer pageNumber,
                                                                      @RequestParam Integer pageSize) {


        Page<Product> res = productService.getProductsPageable(category,
                                                                weight,
                                                                flavor,
                                                                minPrice,
                                                                maxPrice,
                                                                minDiscount,
                                                                sort,
                                                                pageNumber,
                                                                pageSize);

        System.out.println("complete products");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

    }
    @GetMapping("/products/id/{productId}")
    public ResponseEntity<ProductDto> findProductById(@PathVariable Long productId) {
        ProductDto productDto = productService.getProductById(productId);
        return new ResponseEntity<>(productDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<ProductDto>> searchProductHandler(@RequestParam String q) {
        List<ProductDto> productsDto = productService.searchProduct(q);
        return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }
}
