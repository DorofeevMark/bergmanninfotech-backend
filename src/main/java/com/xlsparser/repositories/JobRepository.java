package com.xlsparser.repositories;

import com.xlsparser.models.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JobRepository extends CrudRepository<Job, Integer> {
    Optional<Job> findById(Integer id);
}
