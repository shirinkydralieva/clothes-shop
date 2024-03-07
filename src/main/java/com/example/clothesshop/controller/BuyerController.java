package com.example.clothesshop.controller;

import com.example.clothesshop.dto.BuyerDto;
import com.example.clothesshop.exception.NotFoundException;
import com.example.clothesshop.service.BuyerService;
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

@Tag(name = "BuyerController", description = "API для сущности покупателя")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buyers")
public class BuyerController {
    private final BuyerService service;

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список всех покупателей успешно получен",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BuyerDto.class))
                            )
                    }
            )
    }
    )
    @Operation(
            summary = "Получение всех покупателей",
            description = "Возвращает список покупателей в формате json."
    )
    @GetMapping()
    public ResponseEntity<List<BuyerDto>> getAll() {
        List<BuyerDto> buyers = service.getAll();
        return new ResponseEntity<>(buyers, HttpStatus.OK);
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Покупатель успешно получен по id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BuyerDto.class))
                    }
            )
    }
    )
    @Operation(
            summary = "Получение покупателя по id",
            description = "Возвращает покупателя в формате json."
    )
    @GetMapping("/{id}")
    public ResponseEntity<BuyerDto> getById(@PathVariable Long id) {
        try {
            BuyerDto buyer = service.getById(id);
            return new ResponseEntity<>(buyer, HttpStatus.OK);
        } catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Покупатель успешно создан",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BuyerDto.class))
                    }
            )
    }
    )
    @Operation(
            summary = "Создание покупателя",
            description = "Возвращает созданного покупателя в формате json."
    )
    @PostMapping()
    public ResponseEntity<BuyerDto> create(@RequestBody BuyerDto model){
        BuyerDto buyer = service.create(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(buyer);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Покупатель успешно удален"
            )
    }
    )
    @Operation(
            summary = "Удаление покупателя",
            description = "Ничего не возвращает"
    )
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
