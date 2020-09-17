package com.xlsparser.controllers;

import com.xlsparser.models.StringConstants;
import com.xlsparser.storage.StorageFileNotFoundException;
import com.xlsparser.storage.StorageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.Objects;
import java.util.stream.Collectors;


@RestController
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/importFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (Objects.requireNonNull(file.getOriginalFilename()).contains(".xls")) {
            storageService.store(file);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(StringConstants.STATUS_SUCCESS, "true");
            return jsonObject.toString();
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(StringConstants.STATUS_SUCCESS, "false");
            return jsonObject.toString();
        }
    }

    @GetMapping("/exportFile")
    public ResponseEntity<Resource> serveFile(@RequestParam("filename") String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/files")
    public String showFiles() {
        return storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build())
                .collect(Collectors.toList()).toString();
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}