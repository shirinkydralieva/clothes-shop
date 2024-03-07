package com.example.clothesshop.repository;

import com.example.clothesshop.dto.ProductDto;
import com.example.clothesshop.entity.Manufacturer;
import com.example.clothesshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> getProductsByManufacturerId(Long manufacturerId);

    @Query("select p from Product p where p.price = (select max(p.price) from Product p)")
    Product getMostExpensiveProduct();

    @Query("select p from Product p where p.price = (select min(p.price) from Product p)")
    Product getCheapestProduct();

    @Query("select p from Product p where p.color = :color")
    List<Product> getProductsByColor( @Param("color") String color);

    @Query("""
            select p from Product p  
            where p.price > :price 
            and p.manufacturer.name in :manufacturers 
            and p.color in :colors
            """)
    List<Product> findProductsByPriceGreaterThanAndManufacturersInAndColorsIn(
            @Param("price") Double price,
            @Param("manufacturers") List<String> manufacturers,
            @Param("colors") List<String> color
    );
}
