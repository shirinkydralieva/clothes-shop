package com.example.clothesshop.repository;

import com.example.clothesshop.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer,Long> {
    //@Query("select m from Manufacturer m where m.name like '?%' ")
    Manufacturer findManufacturerByNameStartsWith(String letter);
}
