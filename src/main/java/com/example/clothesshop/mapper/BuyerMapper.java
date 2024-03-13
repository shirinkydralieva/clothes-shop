package com.example.clothesshop.mapper;
import com.example.clothesshop.dto.BuyerDto;
import com.example.clothesshop.entity.Buyer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BuyerMapper {
    BuyerDto toDto(Buyer buyer);
    Buyer toEntity(BuyerDto buyerDto);
    List<BuyerDto> toDtoList(List<Buyer> buyers);
    List<Buyer> toEntityList(List<BuyerDto> buyerDto);
}
