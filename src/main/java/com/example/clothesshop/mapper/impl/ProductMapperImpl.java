package com.example.clothesshop.mapper.impl;

import com.example.clothesshop.dto.ProductDto;
import com.example.clothesshop.entity.Manufacturer;
import com.example.clothesshop.entity.Product;
import com.example.clothesshop.entity.Salesman;
import com.example.clothesshop.mapper.ProductMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapperImpl implements ProductMapper {
    @Override
    public ProductDto toDto(Product product) {
        if (product.getSalesman() != null){
            return ProductDto.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .manufacturerId(product.getManufacturer().getId())
                    .price(product.getPrice())
                    .size(product.getSize())
                    .color(product.getColor())
                    .quantity(product.getQuantity())
                    .salesmanId(product.getSalesman().getId())
                    .build();
        }
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .manufacturerId(product.getManufacturer().getId())
                .price(product.getPrice())
                .size(product.getSize())
                .color(product.getColor())
                .quantity(product.getQuantity())
                .build();
    }

    @Override
    public Product toEntity(ProductDto productDto) {
        return Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .manufacturer(new Manufacturer(productDto.getManufacturerId()))
                .price(productDto.getPrice())
                .size(productDto.getSize())
                .color(productDto.getColor())
                .quantity(productDto.getQuantity())
                .salesman(new Salesman(productDto.getSalesmanId()))
                .build();
    }

    @Override
    public List<ProductDto> toDtoList(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product: products){
            ProductDto productDto = ProductDto.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .manufacturerId(product.getManufacturer().getId())
                    .price(product.getPrice())
                    .size(product.getSize())
                    .color(product.getColor())
                    .quantity(product.getQuantity())
                    .salesmanId(product.getSalesman().getId())
                    .build();
            productDtos.add(productDto);
        }
        return productDtos;
    }

}
