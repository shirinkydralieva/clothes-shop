package com.example.clothesshop.dto;

import com.example.clothesshop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesmanDto {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String placeOfSell;
    private Integer quantityOfLeft;
}
