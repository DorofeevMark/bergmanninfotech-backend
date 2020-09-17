package com.xlsparser.repositories;

import com.xlsparser.models.Section;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends CrudRepository<Section, Integer> {
    Optional<List<Section>> findAllByName(String name);
    Optional<Section> findByJobIdAndName(Integer jobId, String name);
    void deleteById(Integer id);
}
