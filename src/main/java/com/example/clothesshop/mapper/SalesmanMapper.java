package com.example.clothesshop.mapper;

import com.example.clothesshop.dto.SalesmanDto;
import com.example.clothesshop.entity.Salesman;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SalesmanMapper {
    SalesmanDto toDto(Salesman salesman);
    Salesman toEntity(SalesmanDto salesmanDto);
    List<SalesmanDto> toDtoList(List<Salesman> salesman);
    List<Salesman> toEntityList(List<SalesmanDto> salesmanDto);
}
