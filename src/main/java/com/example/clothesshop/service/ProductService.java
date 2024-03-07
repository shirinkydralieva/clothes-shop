package com.example.clothesshop.service;

import com.example.clothesshop.dto.ProductDto;
import com.example.clothesshop.entity.Product;
import com.example.clothesshop.exception.NotFoundException;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto product);

    ProductDto findById(Long id);
    List<ProductDto> getAll();

    List<ProductDto> getAllByManufacturerId(Long manufacturerId);

    void deleteProductById(Long id) throws NotFoundException;

    List<ProductDto> findProductsByCriteria(
            Double price,
            List<String> manufacturers,
            List<String> color
    );

    void decreaseStockQuantity(Product product, Integer quantity);
    List<ProductDto> getProductsByColor(String color);
}
