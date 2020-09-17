package com.xlsparser.services;

import com.xlsparser.models.File;
import com.xlsparser.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;


@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    public boolean checkFile(String fileName) {
        return fileRepository.findByFileName(fileName).isPresent();
    }

    public File getFileByName(String fileName) {
        Optional<File> optionalFile = fileRepository.findByFileName(fileName);
        return optionalFile.orElse(null);
    }

    public void addFile(String fileName) {
        File file = new File();
        file.setFileName(fileName);
        file.setJobs(new HashSet<>());
        fileRepository.save(file);
    }
}
