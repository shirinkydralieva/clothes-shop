package com.example.clothesshop.service.impl;

import com.example.clothesshop.dto.BuyerDto;
import com.example.clothesshop.entity.Buyer;
import com.example.clothesshop.entity.Product;
import com.example.clothesshop.exception.NotFoundException;
import com.example.clothesshop.repository.BuyerRepository;
import com.example.clothesshop.repository.ProductRepository;
import com.example.clothesshop.service.BuyerService;
import com.example.clothesshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BuyerServiceImpl implements BuyerService {
    private final BuyerRepository buyerRepository;
    private final ProductService productService;
    @Override
    public BuyerDto create(BuyerDto model) {
        Buyer buyer = Buyer.builder()
                .fullName(model.getFullName())
                .build();
        try {
            buyerRepository.save(buyer);
        } catch (Exception e){
            log.error(e.getStackTrace().toString());
        }
        return model;
    }

    @Override
    public List<BuyerDto> getAll() {
        List<Buyer> buyers = buyerRepository.findAll();
        List<BuyerDto> buyerDtos = new ArrayList<>();
        for (Buyer buyer: buyers){
            BuyerDto buyerDto = BuyerDto.builder()
                    .id(buyer.getId())
                    .fullName(buyer.getFullName())
                    .build();
            buyerDtos.add(buyerDto);
        }
        return buyerDtos;
    }

    @Override
    public BuyerDto getById(Long id) throws NotFoundException {
        Buyer buyer = buyerRepository.findById(id).orElseThrow(() -> new NotFoundException("Buyer not found with:" + id));
        BuyerDto buyerDto = BuyerDto.builder()
                .id(buyer.getId())
                .fullName(buyer.getFullName())
                .build();
        return buyerDto;
    }

    @Override
    public BuyerDto update(Long id, BuyerDto model) throws NotFoundException {
        Buyer buyer = buyerRepository.findById(id).orElseThrow(() -> new NotFoundException("Buyer not found with:" + id));
        if (model.getFullName() != null){
            buyer.setFullName(model.getFullName());
            buyerRepository.save(buyer);
        }
        return model;
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Buyer buyer = buyerRepository.findById(id).orElseThrow(() -> new NotFoundException("Buyer not found with id: " + id));
        buyerRepository.delete(buyer);
    }

}
