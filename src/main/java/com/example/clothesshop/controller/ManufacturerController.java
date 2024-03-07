package com.example.clothesshop.controller;

import com.example.clothesshop.dto.BuyerDto;
import com.example.clothesshop.dto.ManufacturerDto;
import com.example.clothesshop.entity.Manufacturer;
import com.example.clothesshop.service.ManufacturerService;
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
@Tag(name = "ManufacturerController", description = "API для сущности производителя одежды")
@RestController
@RequestMapping("/api/manufacturers")
@RequiredArgsConstructor
public class ManufacturerController {

    private final ManufacturerService service;

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список всех производителей успешно получен",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ManufacturerDto.class))
                            )
                    }
            )
    }
    )
    @Operation(
            summary = "Получение всех производителей",
            description = "Возвращает список производителей в формате json."
    )
    @GetMapping()
    public ResponseEntity<List<ManufacturerDto>> getAll() {
        List<ManufacturerDto> manufacturer = service.getAllManufacturers();
        return new ResponseEntity<>(manufacturer, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Производитель успешно получен по id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ManufacturerDto.class))
                    }
            )
    }
    )
    @Operation(
            summary = "Получение производителя по id",
            description = "Возвращает производителя в формате json."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerDto> getById(@PathVariable Long id) {
        ManufacturerDto manufacturer = service.findById(id);
        if (manufacturer != null){
            return new ResponseEntity<>(manufacturer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Производитель успешно создан",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ManufacturerDto.class))
                    }
            )
    }
    )
    @Operation(
            summary = "Создание производителя",
            description = "Возвращает созданного производителя в формате json."
    )
    @PostMapping()
    public ResponseEntity<ManufacturerDto> create(@RequestBody ManufacturerDto model){
        ManufacturerDto manufacturer = service.createManufacturer(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(manufacturer);
    }
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Производитель успешно удален"
            )
    }
    )
    @Operation(
            summary = "Удаление производителя",
            description = "Ничего не возвращает"
    )
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
