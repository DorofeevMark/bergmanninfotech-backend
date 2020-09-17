package com.xlsparser.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xlsparser.models.GeoClass;
import com.xlsparser.models.Section;
import com.xlsparser.models.crud.CreateSectionData;
import com.xlsparser.models.crud.DeleteSectionData;
import com.xlsparser.models.crud.ReadSectionData;
import com.xlsparser.models.crud.UpdateSectionData;
import com.xlsparser.repositories.SectionRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.HashSet;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CrudControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private CrudController controller;
    @Autowired
    private SectionRepository sectionRepository;


    private final String TEST_FILENAME = "5.xls";

    private final Integer TEST_JOBID = 66;

    private final String TEST_NAME = "Section2";

    Section getTestSection() {
        Section section = new Section();
        section.setName("Tes");
        GeoClass geoClass1 = new GeoClass();
        geoClass1.setName("Test class 1");
        geoClass1.setCode("r1");
        GeoClass geoClass2 = new GeoClass();
        geoClass2.setName("Test class 2");
        geoClass2.setCode("r2");
        section.setGeologicalClasses(new HashSet<>());

        section.getGeologicalClasses().add(geoClass1);
        section.getGeologicalClasses().add(geoClass2);

        return section;
    }

    Integer createJob() throws Exception {
        MvcResult result =this.mvc.perform(get("/registerJob?fileName=" + TEST_FILENAME))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);
        return jsonObject.getInt("jobId");
    }


    @Test
    void injectedComponentsAreNotNull() {
        assertThat(controller).isNotNull();
        assertThat(sectionRepository).isNotNull();
    }

    @Test
    void createSection() throws Exception {
        Integer jobId = createJob();
        CreateSectionData createSectionData = new CreateSectionData();
        createSectionData.setJobId(jobId);
        createSectionData.setNewSection(getTestSection());

        this.mvc.perform(post("/createSection")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString((createSectionData))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
        assertThat(sectionRepository.findByJobIdAndName(jobId, createSectionData.getNewSection().getName())).isPresent();

    }

    @Test
    void readSection() throws Exception {
        Integer jobId = createJob();
        ReadSectionData readSectionData = new ReadSectionData();
        readSectionData.setJobId(jobId);
        readSectionData.setSectionName(TEST_NAME);

        this.mvc.perform(post("/readSection")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString((readSectionData))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

    }

    @Test
    void updateSection() throws Exception {
        Integer jobId = createJob();
        UpdateSectionData updateSectionData = new UpdateSectionData();
        updateSectionData.setNewSection(getTestSection());
        updateSectionData.setSectionName(TEST_NAME);
        updateSectionData.setJobId(jobId);

        this.mvc.perform(post("/updateSection")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString((updateSectionData))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteSection() throws Exception {
        Integer jobId = createJob();
        CreateSectionData createSectionData = new CreateSectionData();
        createSectionData.setJobId(jobId);
        createSectionData.setNewSection(getTestSection());

        this.mvc.perform(post("/createSection")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString((createSectionData))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
        assertThat(sectionRepository.findByJobIdAndName(jobId, createSectionData.getNewSection().getName())).isPresent();

        DeleteSectionData deleteSectionData = new DeleteSectionData();
        deleteSectionData.setSectionName(TEST_NAME);
        deleteSectionData.setJobId(TEST_JOBID);

        this.mvc.perform(post("/deleteSection")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString((deleteSectionData))))
                .andExpect(status().isOk());
    }


    public static String toJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
