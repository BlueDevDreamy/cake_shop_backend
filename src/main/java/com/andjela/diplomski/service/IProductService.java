package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.product.ProductDto;
import com.andjela.diplomski.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {

    ProductDto createProduct(ProductDto productDto);

    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long id);

    List<ProductDto> getProductsByCategory(String category);

    Page<Product> getProductsPageable(String category,
                                      List<Double> weights,
                                      List<String> flavors,
                                      Integer minPrice,
                                      Integer maxPrice,
                                      Integer minDiscount,
                                      String sort,
                                      Integer pageNumber,
                                      Integer pageSize);

    ProductDto updateProduct(Long id, ProductDto productDto);

    String deleteProduct(Long id);

    List<ProductDto> searchProduct(String query);
}
