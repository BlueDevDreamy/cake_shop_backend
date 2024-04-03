package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.product.ProductDto;
import com.andjela.diplomski.dto.product.ProductMapper;
import com.andjela.diplomski.entity.Product;
import com.andjela.diplomski.entity.codebook.Category;
import com.andjela.diplomski.entity.codebook.Flavor;
import com.andjela.diplomski.exception.DataNotValidException;
import com.andjela.diplomski.exception.ResourceNotFoundException;
import com.andjela.diplomski.repository.CategoryRepository;
import com.andjela.diplomski.repository.FlavorRepository;
import com.andjela.diplomski.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final FlavorRepository flavorRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Category topLevel = categoryRepository.findByName(productDto.getTopLevelCategory());
        if (topLevel == null) {
            Category topLevelCategory = Category.builder()
                    .name(productDto.getTopLevelCategory())
                    .level(1)
                    .uuid(UUID.randomUUID())
                    .createdAt(LocalDateTime.now())
                    .build();
            topLevel = categoryRepository.save(topLevelCategory);
        }
        Category secondLevel = categoryRepository.findByNameAndParent(productDto.getSecondLevelCategory(), topLevel.getName());
        if (secondLevel == null) {
            Category secondLevelCategory = Category.builder()
                    .name(productDto.getTopLevelCategory())
                    .parentCategory(topLevel)
                    .level(2)
                    .uuid(UUID.randomUUID())
                    .createdAt(LocalDateTime.now())
                    .build();
            secondLevel = categoryRepository.save(secondLevelCategory);
        }

        Set<Flavor> flavors = (Set<Flavor>) flavorRepository.findAll();
//        Set<FlavorDto> foundFlavors = new HashSet<>();
//        for(Flavor f : flavors){
//            FlavorDto flavorDto = FlavorMapper.MAPPER.mapToFlavorDto(f);
//            foundFlavors.add(flavorDto);
//        }

        Product product = Product.builder()
                .title(productDto.getTitle())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .discountedPrice(productDto.getDiscountedPrice())
                .discountedPercent(productDto.getDiscountedPercent())
                .flavors(flavors)
                .uuid(UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .build();
        productRepository.save(product);
        ProductDto savedProductDto = ProductMapper.MAPPER.mapToProductDto(product);
        return savedProductDto;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<ProductDto> foundProducts = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("List of products is empty");
        }
        for (Product p : products) {
            ProductDto productDto = ProductMapper.MAPPER.mapToProductDto(p);
            foundProducts.add(productDto);
        }
        return foundProducts;
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product foundProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Didn't find product with id:" + id));
        return ProductMapper.MAPPER.mapToProductDto(foundProduct);
    }

    @Override
    public List<ProductDto> getProductsByCategory(String category) {
        List<Product> products = productRepository.getProductsByCategory(category);
        return ProductMapper.MAPPER.mapToListProductDto(products);
    }

    @Override
    public Page<Product> getProductsPageable(String category, List<Double> weights, List<String> flavors, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
        if (!weights.isEmpty()) {
            products = products.stream().filter(p -> weights.stream().anyMatch(w -> w.equals(p.getWeight()))).collect(Collectors.toList());
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex, pageable.getPageSize());

        List<Product> pageContent = products.subList(startIndex, endIndex);
        Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());
        return filteredProducts;
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = ProductMapper.MAPPER.mapToProduct(productDto);
        Product updatedProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Didn't find product with id:" + id));
        Product savedproduct = new Product();
        boolean isChanged = false;

        if (productDto.getTitle() == null || productDto.getTitle().trim().isEmpty() || !productDto.getTitle().matches("^[A-Z][a-zA-Z0-9 ]*$")) {
            throw new DataNotValidException("Title must start with uppercase");
        } else {
            isChanged = true;
            updatedProduct.setTitle(product.getTitle());
        }
        if (productDto.getDescription() != null && !productDto.getDescription().isEmpty()) {
            isChanged = true;
            updatedProduct.setDescription(product.getDescription());
        }
        if (productDto.getPrice() < 0) {
            throw new DataNotValidException("Price must be greater than 0");
        } else {
            isChanged = true;
            updatedProduct.setPrice(product.getPrice());
        }
        if (productDto.getDiscountedPrice() < 0) {
            throw new DataNotValidException("Discounted price must be greater than 0");
        } else {
            isChanged = true;
            updatedProduct.setDiscountedPrice(product.getDiscountedPrice());
        }
        if (productDto.getDiscountedPercent() > 100) {
            throw new DataNotValidException("Discounted percent must be less than 100");
        } else {
            isChanged = true;
            updatedProduct.setDiscountedPercent(product.getDiscountedPercent());
        }
        if (productDto.getFlavors() != null && !productDto.getFlavors().isEmpty()) {
            isChanged = true;
            updatedProduct.setFlavors(product.getFlavors());
        }
        if (productDto.getWeight() > 50.0) {
            throw new DataNotValidException("Weight can not be greater than 50");
        } else {
            isChanged = true;
            updatedProduct.setWeight(product.getWeight());
        }
        if (productDto.getDecorationPrice() < 0) {
            throw new DataNotValidException("Decoration price must be greater than 0");
        } else {
            isChanged = true;
            updatedProduct.setDecorationPrice(product.getDecorationPrice());
        }
        if (productDto.getImageUrl() != null && !productDto.getImageUrl().isEmpty()) {
            isChanged = true;
            updatedProduct.setImageUrl(product.getImageUrl());
        }
        if (productDto.getRatings() != null && !productDto.getRatings().isEmpty()) {
            isChanged = true;
            updatedProduct.setRatings(product.getRatings());
        }
        if (productDto.getReviews() != null && !productDto.getReviews().isEmpty()) {
            isChanged = true;
            updatedProduct.setReviews(product.getReviews());
        }

        if (isChanged) {
            updatedProduct.setUpdatedAt(LocalDateTime.now());
            savedproduct = productRepository.save(updatedProduct);
        }

        return ProductMapper.MAPPER.mapToProductDto(savedproduct);
    }

    @Override
    public String deleteProduct(Long id) {
        Product foundProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Didn't find product with id:" + id));
        foundProduct.getFlavors().clear();
        productRepository.delete(foundProduct);
        return "Product deleted successfully";
    }

    @Override
    public List<ProductDto> searchProduct(String query) {
        List<Product> products = productRepository.searchProduct(query);
        List <ProductDto> productsDto = ProductMapper.MAPPER.mapToListProductDto(products);
        return productsDto;
    }
}
