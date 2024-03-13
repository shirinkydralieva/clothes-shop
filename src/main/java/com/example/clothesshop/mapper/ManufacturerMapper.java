package com.example.clothesshop.mapper;

import com.example.clothesshop.dto.ManufacturerDto;
import com.example.clothesshop.entity.Manufacturer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ManufacturerMapper {
    ManufacturerDto toDto(Manufacturer manufacturer);
    Manufacturer toEntity(ManufacturerDto manufacturerDto);
    List<ManufacturerDto> toDtoList(List<Manufacturer> manufacturers);
    List<Manufacturer> toEntityList(List<ManufacturerDto> manufacturerDto);
}
