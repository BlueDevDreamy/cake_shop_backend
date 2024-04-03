package com.andjela.diplomski.controller;

import com.andjela.diplomski.dto.order.OrderDto;
import com.andjela.diplomski.dto.product.ProductDto;
import com.andjela.diplomski.service.ProductService;
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
@RequestMapping("/api/admin/products")
public class AdminProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto req) {
        ProductDto productDto = productService.createProduct(req);
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    @PutMapping("create-multiple")
    public ResponseEntity<List<ProductDto>> createMultipleProducts(@RequestBody ProductDto[] req) {
        List<ProductDto> productDtoList = new ArrayList<>();
        for (ProductDto productDto : req) {
            productService.createProduct(productDto);
            productDtoList.add(productDto);
        }
        return new ResponseEntity<>(productDtoList, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtos = productService.getAllProducts();
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto req) {
        ProductDto productDto = productService.updateProduct(id, req);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }

}
