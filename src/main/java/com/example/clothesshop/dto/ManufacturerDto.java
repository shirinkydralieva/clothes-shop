package com.example.clothesshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManufacturerDto {
    private Long id;
    private String name;
    private String address;
    private String contactInfo;
}
