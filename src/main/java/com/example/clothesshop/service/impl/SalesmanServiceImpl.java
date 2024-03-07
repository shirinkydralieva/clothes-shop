package com.example.clothesshop.service.impl;

import com.example.clothesshop.dto.ProductDto;
import com.example.clothesshop.dto.SalesmanDto;
import com.example.clothesshop.entity.Product;
import com.example.clothesshop.entity.Salesman;
import com.example.clothesshop.repository.SalesmanRepository;
import com.example.clothesshop.service.SalesmanService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SalesmanServiceImpl implements SalesmanService {
    private final SalesmanRepository salesmanRepository;

    @Override
    public SalesmanDto createSalesman(SalesmanDto model) {
        Salesman salesman = Salesman.builder()
                .name(model.getName())
                .email(model.getEmail())
                .phoneNumber(model.getPhoneNumber())
                .placeOfSell(model.getPlaceOfSell())
                .quantityOfLeft(model.getQuantityOfLeft())
                .build();
        try {
            salesmanRepository.save(salesman);
        } catch (Exception e){
            log.error(e.getStackTrace().toString());
        }
        return model;
    }

    @Override
    public SalesmanDto getById(long id) {
        Salesman salesman = salesmanRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Salesman not found with id"+ id));
        SalesmanDto model = SalesmanDto.builder()
                .email(salesman.getEmail())
                .id(salesman.getId())
                .name(salesman.getName())
                .phoneNumber(salesman.getPhoneNumber())
                .placeOfSell(salesman.getPlaceOfSell())
                .quantityOfLeft(salesman.getQuantityOfLeft())
                .build();
        return model;
    }

    @Override
    public List<SalesmanDto> getAll() {
        List<Salesman> salesmen = salesmanRepository.findAll();
        List<SalesmanDto> models = new ArrayList<>();
        for (Salesman salesman: salesmen){
            SalesmanDto model = SalesmanDto.builder()
                    .email(salesman.getEmail())
                    .id(salesman.getId())
                    .name(salesman.getName())
                    .phoneNumber(salesman.getPhoneNumber())
                    .placeOfSell(salesman.getPlaceOfSell())
                    .quantityOfLeft(salesman.getQuantityOfLeft())
                    .build();
            models.add(model);
        }
        return models;
    }

    @Override
    public void deleteSalesmanById(Long id) {
        salesmanRepository.deleteById(id);
    }

    @Override
    public void deleteById(Long id) {
        salesmanRepository.deleteById(id);
    }
}
