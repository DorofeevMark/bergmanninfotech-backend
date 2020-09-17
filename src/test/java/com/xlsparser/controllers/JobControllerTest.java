package com.xlsparser.controllers;

import com.xlsparser.repositories.JobRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class JobControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JobController controller;
    @Autowired
    private JobRepository jobRepository;


    private final String TEST_FILENAME = "5.xls";

    private final String TEST_SECTION_NAME = "Section2";

    private final String TEST_CLASS_NAME = "Test class2";

    private final String TEST_CODE = "r2";


    Integer createJob() throws Exception {
        MvcResult result = this.mvc.perform(get("/registerJob?fileName=" + TEST_FILENAME))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);
        return jsonObject.getInt("jobId");
    }


    @Test
    void injectedComponentsAreNotNull() {
        assertThat(controller).isNotNull();
        assertThat(jobRepository).isNotNull();
    }

    @Test
    void registerJob() throws Exception {
        MvcResult result = this.mvc.perform(get("/registerJob?fileName=" + TEST_FILENAME))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(content);
        assertThat(jsonObject.getString("status").equals("processing"));
        assertThat(jobRepository.findById(jsonObject.getInt("jobId"))).isPresent();
    }

    @Test
    void getJobResult() throws Exception {
        Integer jobId = createJob();
        this.mvc.perform(get("/getJobResult?jobId=" + jobId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void getJobResultBySectionName() throws Exception {
        Integer jobId = createJob();
        this.mvc.perform(get("/getJobResultBySectionName?sectionName=" + TEST_SECTION_NAME))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void getJobResultByClassName() throws Exception {
        Integer jobId = createJob();
        this.mvc.perform(get("/getJobResultBySectionName?sectionName=" + TEST_SECTION_NAME))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
        this.mvc.perform(get("/getJobResultByClassName?className=" + TEST_CLASS_NAME))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void getJobResultByClassCode() throws Exception {
        Integer jobId = createJob();
        this.mvc.perform(get("/getJobResultBySectionName?sectionName=" + TEST_SECTION_NAME))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
        this.mvc.perform(get("/getJobResultByClassCode?code=" + TEST_CODE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
}
