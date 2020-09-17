package com.xlsparser.repositories;

import com.xlsparser.models.GeoClass;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface GeoClassRepository extends CrudRepository<GeoClass, Integer> {
    Optional<List<GeoClass>> findAllByCode(String code);
    Optional<List<GeoClass>> findAllByName(String name);
    void deleteAllBySectionId(Integer sectionId);
}

