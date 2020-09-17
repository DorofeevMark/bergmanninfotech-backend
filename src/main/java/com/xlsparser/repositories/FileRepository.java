package com.xlsparser.repositories;

import com.xlsparser.models.File;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface FileRepository extends CrudRepository<File, Integer> {
    Optional<File> findByFileName(String fileName);
}


