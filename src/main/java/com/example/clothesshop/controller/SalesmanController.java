package com.example.clothesshop.controller;

import com.example.clothesshop.dto.ManufacturerDto;
import com.example.clothesshop.dto.ProductDto;
import com.example.clothesshop.dto.SalesmanDto;
import com.example.clothesshop.entity.Manufacturer;
import com.example.clothesshop.entity.Salesman;
import com.example.clothesshop.repository.SalesmanRepository;
import com.example.clothesshop.service.ManufacturerService;
import com.example.clothesshop.service.SalesmanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "SalesmanController", description = "API для сущности продавца одежды")
@RestController
@RequestMapping("/api/salesmen")
@RequiredArgsConstructor
public class SalesmanController {
    private final SalesmanService service;

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список всех продавцов успешно получен",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SalesmanDto.class))
                            )
                    }
            )
    }
    )
    @Operation(
            summary = "Получение всех продавцов",
            description = "Возвращает список продавцов в формате json."
    )
    @GetMapping()
    public ResponseEntity<List<SalesmanDto>> getAll() {
        List<SalesmanDto> salesman = service.getAll();
        return new ResponseEntity<>(salesman, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Продавец успешно получен по id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SalesmanDto.class))
                    }
            )
    }
    )
    @Operation(
            summary = "Получение продавца по id",
            description = "Возвращает продавца в формате json."
    )
    @GetMapping("/{id}")
    public ResponseEntity<SalesmanDto> getById(@PathVariable Long id) {
        SalesmanDto salesman = service.getById(id);
        if (salesman != null){
            return new ResponseEntity<>(salesman, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Продавец успешно создан",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SalesmanDto.class))
                    }
            )
    }
    )
    @Operation(
            summary = "Создание продавца",
            description = "Возвращает созданного продавца в формате json."
    )
    @PostMapping()
    public ResponseEntity<SalesmanDto> create(@RequestBody SalesmanDto model){
        SalesmanDto salesman = service.create(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(salesman);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Продавец успешно удален"
            )
    }
    )
    @Operation(
            summary = "Удаление продавца",
            description = "Ничего не возвращает"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
