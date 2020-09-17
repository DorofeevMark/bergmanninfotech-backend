package com.xlsparser.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class FileUploadControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private FileUploadController controller;


    private final String TEST_NAME = "5.xml";


    @Test
    void injectedComponentsAreNotNull() {
        assertThat(controller).isNotNull();
    }

    @Test
    void showFiles() throws Exception {
        this.mvc.perform(get("/files"))
                .andExpect(status().isOk());
    }

    @Test
    void exportFile() throws Exception {
        this.mvc.perform(get("/exportFile?filename=" + TEST_NAME))
                .andExpect(status().isOk());
    }

}
