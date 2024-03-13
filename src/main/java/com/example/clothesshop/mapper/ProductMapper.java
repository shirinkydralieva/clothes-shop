package com.example.clothesshop.mapper;

import com.example.clothesshop.dto.ProductDto;
import com.example.clothesshop.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

public interface ProductMapper {
    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);
    List<ProductDto> toDtoList(List<Product> product);
}
