package com.example.clothesshop.service.impl;

import com.example.clothesshop.dto.ProductDto;
import com.example.clothesshop.entity.Manufacturer;
import com.example.clothesshop.entity.Product;
import com.example.clothesshop.entity.Salesman;
import com.example.clothesshop.exception.NotFoundException;
import com.example.clothesshop.mapper.ProductMapper;
import com.example.clothesshop.repository.ProductRepository;
import com.example.clothesshop.repository.SalesmanRepository;
import com.example.clothesshop.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final SalesmanRepository salesmanRepository;
    private final ProductMapper productMapper;
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productRepository.save(productMapper.toEntity(productDto));
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Product not found with id"+ id));
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getAll() {
        return productRepository.findAll().stream().map(productMapper::toDto).toList();
    }

    @Override
    public List<ProductDto> getAllByManufacturerId(Long manufacturerId) {
        return productMapper.toDtoList(productRepository.getProductsByManufacturerId(manufacturerId));
    }
    @Override
    public void deleteProductById(Long id) throws NotFoundException {
        Product product = productRepository.findById(id).orElseThrow(()-> new NotFoundException("Product not found"));
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDto> findProductsByCriteria(Double price, List<String> manufacturers, List<String> color) {
        return productRepository.findProductsByPriceGreaterThanAndManufacturersInAndColorsIn(price,manufacturers,color).stream().map(productMapper::toDto).toList();
    }

    @Override
    public void decreaseStockQuantity(Product product, Integer quantity) {
       product.setQuantity(product.getQuantity() - quantity);
       productRepository.save(product);
    }

    @Override
    public List<ProductDto> getProductsByColor(String color) {
        return productRepository.getProductsByColor(color).stream().map(productMapper::toDto).toList();
    }
//4
    @Override
    public List<ProductDto> getAllBySalesman(Long salesmanId) throws NotFoundException {
        Salesman salesman = salesmanRepository.findById(salesmanId).orElseThrow(()-> new NotFoundException("Salesman not found with id:" + salesmanId));
        List<Product> products = productRepository.findAll().stream().filter(product -> product.getSalesman() == salesman).toList();
        return productMapper.toDtoList(products);
    }
//5
    public Long countAllBySalesman(Long salesmanId) throws NotFoundException {
        Salesman salesman = salesmanRepository.findById(salesmanId).orElseThrow(()-> new NotFoundException("Salesman not found with id:" + salesmanId));
        List<Product> products = productRepository.findAll().stream().filter(product -> product.getSalesman() == salesman).toList();
        return (long) products.size();
    }
//6
    public Double getPriceBySalesman(Long productId, Long salesmanId) throws NotFoundException {
        Salesman salesman = salesmanRepository.findById(salesmanId).orElseThrow(()-> new NotFoundException("Salesman not found with id:" + salesmanId));
        Product product = productRepository.findProductByIdAndSalesmanId(productId, salesmanId).orElseThrow(()-> new NotFoundException("Product not found"));
        return product.getPrice();
    }
//7
    public Double countAllPriceBySalesman(Long salesmanId) throws NotFoundException {
        Salesman salesman = salesmanRepository.findById(salesmanId).orElseThrow(()-> new NotFoundException("Salesman not found with id:" + salesmanId));
        List<Product> products = productRepository.findAll().stream().filter(product -> product.getSalesman() == salesman).toList();
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    //8
    public List<Manufacturer> getAllManufacturesBySalesman(Long salesmanId) throws NotFoundException {
        Salesman salesman = salesmanRepository.findById(salesmanId).orElseThrow(()-> new NotFoundException("Salesman not found with id:" + salesmanId));
        List<Product> products = productRepository.findAll().stream().filter(product -> product.getSalesman() == salesman).toList();
        return products.stream().map(Product::getManufacturer).toList();
    }
    //9
    public List<String> getAllProductsNameBySalesman(Long salesmanId) throws NotFoundException {
        Salesman salesman = salesmanRepository.findById(salesmanId).orElseThrow(()-> new NotFoundException("Salesman not found with id:" + salesmanId));
        List<Product> products = productRepository.findAll().stream().filter(product -> product.getSalesman() == salesman).toList();
        return products.stream().map(Product::getName).map(product -> product + "s").toList();
    }

    //10
    public List<Integer> getAllProductsPriceBySalesmanWithDiscount(Long salesmanId) throws NotFoundException {
        Salesman salesman = salesmanRepository.findById(salesmanId).orElseThrow(()-> new NotFoundException("Salesman not found with id:" + salesmanId));
        List<Product> products = productRepository.findAll().stream().filter(product -> product.getSalesman() == salesman).toList();
        return products.stream().map(product -> (int) product.getPrice()).map(price -> (int) (price * 0.5)).toList();
    }
}
