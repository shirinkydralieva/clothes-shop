package com.example.clothesshop.service.impl;

import com.example.clothesshop.dto.ProductDto;
import com.example.clothesshop.entity.Manufacturer;
import com.example.clothesshop.entity.Product;
import com.example.clothesshop.entity.Salesman;
import com.example.clothesshop.exception.NotFoundException;
import com.example.clothesshop.repository.ProductRepository;
import com.example.clothesshop.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductDto createProduct(ProductDto model) {
        Product product = Product.builder()
                .name(model.getName())
                .price(model.getPrice())
                .color(model.getColor())
                .size(model.getSize())
                .description(model.getDescription())
                .manufacturer(Manufacturer.builder().id(model.getManufacturerId()).build())
                .salesman(new Salesman(model.getSalesmanId()))
                .quantity(model.getQuantity())
                .build();
        try {
            productRepository.save(product);
        } catch (Exception e){
            log.error(e.getStackTrace().toString());
        }
        return model;
    }

    @Override
    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Product not found with id"+ id));
        ProductDto model = ProductDto.builder()
                .id(product.getId())
                .color(product.getColor())
                .description(product.getDescription())
                .price(product.getPrice())
                .manufacturerId(product.getManufacturer().getId())
                .size(product.getSize())
                .name(product.getName())
                .salesmanId(product.getSalesman().getId())
                .quantity(product.getQuantity())
                .build();
        return model;
    }

    @Override
    public List<ProductDto> getAll() {
        List<Product> products = productRepository.findAll();
        return getProductDtos(products);
    }

    @Override
    public List<ProductDto> getAllByManufacturerId(Long manufacturerId) {
        List<Product> products = productRepository.getProductsByManufacturerId(manufacturerId);
        return getProductDtos(products);
    }

    private List<ProductDto> getProductDtos(List<Product> products) {
        List<ProductDto> models = new ArrayList<>();
        for (Product product: products){
            ProductDto model = ProductDto.builder()
                    .id(product.getId())
                    .color(product.getColor())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .manufacturerId(product.getManufacturer().getId())
                    .size(product.getSize())
                    .name(product.getName())
                    .quantity(product.getQuantity())
                    .build();
            if (product.getSalesman() != null){
                model.setSalesmanId(product.getSalesman().getId());
            }
            models.add(model);
        }
        return models;
    }

    @Override
    public void deleteProductById(Long id) throws NotFoundException {
        Product product = productRepository.findById(id).orElseThrow(()-> new NotFoundException("Product not found"));
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDto> findProductsByCriteria(Double price, List<String> manufacturers, List<String> color) {
        List<Product> products = productRepository.findProductsByPriceGreaterThanAndManufacturersInAndColorsIn(price,manufacturers,color);
        return getProductDtos(products);
    }

    @Override
    public void decreaseStockQuantity(Product product, Integer quantity) {
       product.setQuantity(product.getQuantity() - quantity);
       productRepository.save(product);
    }


    @Override
    public List<ProductDto> getProductsByColor(String color) {
        List<Product> products = productRepository.getProductsByColor(color);
        return getProductDtos(products);
    }

}
