package com.microservices.productservice;

import com.microservices.productservice.dto.ProductRequest;
import com.microservices.productservice.model.Product;
import com.microservices.productservice.repository.ProductRepository;
import com.microservices.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldCreateProduct(){
        //given
        ProductRequest productRequest = new ProductRequest("iphone 17", "iphone 17", BigDecimal.valueOf(1000));


        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> {
                    Product p= invocation.getArgument(0);
                    p.setId("123");
                    return p;
                });
        //when
        productService.createProduct(productRequest);

        //then
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);

        verify(productRepository).save(productCaptor.capture());

        Product savedProduct = productCaptor.getValue();

        assertEquals("123", savedProduct.getId());
        assertEquals("iphone 17", savedProduct.getName());
        assertEquals("iphone 17", savedProduct.getDescription());
        assertEquals(BigDecimal.valueOf(1000), savedProduct.getPrice());


    }


}
