package com.example.clothesshop.controller;

import com.example.clothesshop.dto.ManufacturerDto;
import com.example.clothesshop.dto.ProductDto;
import com.example.clothesshop.entity.Product;
import com.example.clothesshop.exception.NotFoundException;
import com.example.clothesshop.service.ProductService;
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
    @Tag(name = "ProductController", description = "API для сущности продукта (одежды)")
    @RestController
    @RequestMapping("/api/products")
    @RequiredArgsConstructor
    public class ProductController {
        private final ProductService productService;

        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Список всех продуктов успешно получен",
                        content = {
                                @Content(mediaType = "application/json",
                                        array = @ArraySchema(schema = @Schema(implementation = ProductDto.class))
                                )
                        }
                )
        }
        )
        @Operation(
                summary = "Получение всех продуктов",
                description = "Возвращает список продуктов в формате json."
        )
        @GetMapping()
        public ResponseEntity<List<ProductDto>> getAll() {
            List<ProductDto> products = productService.getAll();
            return new ResponseEntity<>(products, HttpStatus.OK);
        }

        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Продукт успешно получен по id",
                        content = {
                                @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ProductDto.class))
                        }
                )
        }
        )
        @Operation(
                summary = "Получение продукта по id",
                description = "Возвращает продукт в формате json."
        )
        @GetMapping("/{id}")
        public ResponseEntity<ProductDto> getById(@PathVariable Long id) {
            ProductDto product = productService.findById(id);
            if (product != null){
                return new ResponseEntity<>(product, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Продукт успешно создан",
                        content = {
                                @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ProductDto.class))
                        }
                )
        }
        )
        @Operation(
                summary = "Создание продукта",
                description = "Возвращает созданный продукт в формате json."
        )
        @PostMapping()
        public ResponseEntity<ProductDto> create(@RequestBody ProductDto model){
            ProductDto product = productService.createProduct(model);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        }

        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Продукт успешно удален"
                )
        }
        )
        @Operation(
                summary = "Удаление продукта",
                description = "Ничего не возвращает"
        )
        @DeleteMapping("/{id}")
        public  ResponseEntity<Void> delete(@PathVariable Long id) throws NotFoundException {
            productService.deleteProductById(id);
            return ResponseEntity.noContent().build();
        }

        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Список продуктов успешно получен",
                        content = {
                                @Content(mediaType = "application/json",
                                        array = @ArraySchema(schema = @Schema(implementation = ProductDto.class))
                                )
                        }
                )
        }
        )
        @Operation(
                summary = "Получение списка продуктов по id производителя",
                description = "Возвращает список продуктов в формате json."
        )
        @GetMapping("/manufacturer")
        public ResponseEntity<List<ProductDto>> getAllByManufacturerId(@RequestParam("manufacturerId") Long manufacturerId) {
            List<ProductDto> products = productService.getAllByManufacturerId(manufacturerId);
            return new ResponseEntity<>(products, HttpStatus.OK);
        }

        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Список продуктов по заданному цвету успешно получен",
                        content = {
                                @Content(mediaType = "application/json",
                                        array = @ArraySchema(schema = @Schema(implementation = ProductDto.class))
                                )
                        }
                )
        }
        )
        @Operation(
                summary = "Получение списка продуктов по заданному цвету",
                description = "Возвращает список продуктов в формате json."
        )
        @GetMapping("/filter")
        public ResponseEntity<List<ProductDto>> getByColor(@RequestParam String color){
            List<ProductDto> products = productService.getProductsByColor(color);
            return new ResponseEntity<>(products, HttpStatus.OK);
        }

        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Список продуктов по выбранным критериям успешно получен",
                        content = {
                                @Content(mediaType = "application/json",
                                        array = @ArraySchema(schema = @Schema(implementation = ProductDto.class))
                                )
                        }
                )
        }
        )
        @Operation(
                summary = "Получение списка продуктов по выбранным критериям",
                description = "Возвращает список продуктов в формате json."
        )
        @GetMapping("/filterBy")
        public ResponseEntity<List<ProductDto>> findByCriteria(
                @RequestParam("price") Double price,
                @RequestParam("manufacturers") List<String> manufacturers,
                @RequestParam("colors") List<String> colors
                )
        {
            List<ProductDto> products = productService.findProductsByCriteria(price,manufacturers,colors);
            return new ResponseEntity<>(products, HttpStatus.OK);
        }

}
