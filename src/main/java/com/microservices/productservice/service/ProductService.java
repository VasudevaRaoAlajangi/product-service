package com.microservices.productservice.service;

import com.microservices.productservice.dto.ProductRequest;
import com.microservices.productservice.dto.ProductResponse;
import com.microservices.productservice.model.Product;
import com.microservices.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
        Product savedProduct = productRepository.save(product);
        log.info("Product {} is saved",savedProduct.getId());
        return maptoProductResponse(savedProduct);
    }

    public List<ProductResponse> getALLProducts(){
        List<Product> products = productRepository.findAll();
        return  products.stream()
                .map(this::maptoProductResponse)// .map(product -> maptoProductResponse(product))
                .toList();

    }

    private ProductResponse maptoProductResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice());
    }

}
