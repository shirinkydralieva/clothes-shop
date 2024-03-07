package com.example.clothesshop.controller;

import com.example.clothesshop.dto.TransactionDto;
import com.example.clothesshop.exception.NotFoundException;
import com.example.clothesshop.service.TransactionService;
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
@Tag(name = "TransactionController", description = "API для сущности транзакции(покупки)")
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService service;

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список всех транзакций успешно получен",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TransactionDto.class))
                            )
                    }
            )
    }
    )
    @Operation(
            summary = "Получение всех транзакций",
            description = "Возвращает список транзакций в формате json."
    )
    @GetMapping()
    public ResponseEntity<List<TransactionDto>> getAll() {
        List<TransactionDto> transactions = service.getAll();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Транзакция успешно получена по id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TransactionDto.class))
                    }
            )
    }
    )
    @Operation(
            summary = "Получение транзакции по id",
            description = "Возвращает транзакцию в формате json."
    )
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getById(@PathVariable Long id) {
        TransactionDto transaction = service.getById(id);
        if (transaction != null){
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Транзакция успешно создана",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TransactionDto.class))
                    }
            )
    }
    )
    @Operation(
            summary = "Создание транзакции",
            description = "Возвращает созданную транзакцию в формате json."
    )
    @PostMapping()
    public ResponseEntity<TransactionDto> create(@RequestBody TransactionDto model){
        try {
            TransactionDto transaction = service.sellProduct(model);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
        } catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список транзакций успешно получен",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TransactionDto.class))
                            )
                    }
            )
    }
    )
    @Operation(
            summary = "Получение списка транзакций по id покупателя",
            description = "Возвращает список транзакций в формате json."
    )
    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<TransactionDto>> getAllByBuyerId(@PathVariable Long buyerId){
        try {
            List<TransactionDto> transactions = service.getAllByBuyerId(buyerId);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список транзакций успешно получен",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TransactionDto.class))
                            )
                    }
            )
    }
    )
    @Operation(
            summary = "Получение списка транзакций по id продавца",
            description = "Возвращает список транзакций в формате json."
    )
    @GetMapping("/salesman/{salesmanId}")
    public ResponseEntity<List<TransactionDto>> getAllBySalesmanId(@PathVariable Long salesmanId){
        try {
            List<TransactionDto> transactions = service.getAllBySalesmanId(salesmanId);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Транзакция успешно удалена"
            )
    }
    )
    @Operation(
            summary = "Удаление транзакции по id",
            description = "Ничего не возвращает"
    )
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
