package com.example.clothesshop.service;

import com.example.clothesshop.dto.ProductDto;
import com.example.clothesshop.entity.Manufacturer;
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
    List<ProductDto> getAllBySalesman(Long salesmanId) throws NotFoundException;
    Long countAllBySalesman(Long salesmanId) throws NotFoundException;
    Double getPriceBySalesman(Long salesmanId, Long productId) throws NotFoundException;
    Double countAllPriceBySalesman(Long salesmanId) throws NotFoundException;
    List<Manufacturer> getAllManufacturesBySalesman(Long salesmanId) throws NotFoundException;
    List<String> getAllProductsNameBySalesman(Long salesmanId) throws NotFoundException;
    List<Integer> getAllProductsPriceBySalesmanWithDiscount(Long salesmanId) throws NotFoundException;
}
