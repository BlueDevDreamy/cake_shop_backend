package com.andjela.diplomski.dto.product;

import com.andjela.diplomski.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    ProductDto mapToProductDto(Product product);
    Product mapToProduct(ProductDto productDto);

    List<ProductDto> mapToListProductDto(List<Product> products);
}
