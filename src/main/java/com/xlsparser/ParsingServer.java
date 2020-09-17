package com.xlsparser;

import com.xlsparser.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
@EnableAsync
public class ParsingServer {

    public static void main(String[] args) {
        SpringApplication.run(ParsingServer.class, args);
    }


}
