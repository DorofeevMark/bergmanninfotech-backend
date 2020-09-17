package com.xlsparser.controllers;

import com.xlsparser.models.Job;
import com.xlsparser.models.Status;
import com.xlsparser.models.StringConstants;
import com.xlsparser.services.JobService;
import com.xlsparser.storage.FileSystemStorageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class JobController {
    @Autowired
    FileSystemStorageService systemStorageService;
    @Autowired
    JobService jobService;

    @GetMapping(path = "/registerJob")
    public String registerJob(@RequestParam("fileName") String fileName) {
        JSONObject jsonObject = new JSONObject();
        Job result = jobService.addJob(systemStorageService.load(fileName).toFile());
        if (result != null) {
            jsonObject.put(StringConstants.JOB_ID, result.getId());
            jsonObject.put(StringConstants.STATUS, Status.PROCESSING);
        } else {
            jsonObject.put(StringConstants.STATUS, Status.ERROR);
        }
        return jsonObject.toString();
    }

    @GetMapping(path = "/getJobResult")
    public String getJobResult(@RequestParam("jobId") Integer jobId) {
        String result = jobService.getJobResult(jobId);
        JSONObject jsonObject;
        if (!result.equals("") && !result.equals(StringConstants.STATUS_PROCESSING)) {
            return result;
        } else if (result.equals(StringConstants.STATUS_PROCESSING)) {
            jsonObject = new JSONObject();
            jsonObject.put(StringConstants.STATUS, Status.PROCESSING);
        } else {
            jsonObject = new JSONObject();
            jsonObject.put(StringConstants.STATUS, Status.INVALID_ID);
        }
        return jsonObject.toString();
    }

    @GetMapping(path = "/getJobResultBySectionName")
    public String getJobResultBySectionName(@RequestParam("sectionName") String sectionName) {
        String result = jobService.getJobResultBySectionName(sectionName);
        JSONObject jsonObject;
        if (!result.equals("")) {
            return result;
        } else {
            jsonObject = new JSONObject();
            jsonObject.put(StringConstants.STATUS, Status.NOT_FOUND);
        }
        return jsonObject.toString();
    }

    @GetMapping(path = "/getJobResultByClassName")
    public String getJobResultByClassName(@RequestParam("className") String className) {
        String result = jobService.getJobResultByClassName(className);
        JSONObject jsonObject;
        if (!result.equals("")) {
            return result;
        } else {
            jsonObject = new JSONObject();
            jsonObject.put(StringConstants.STATUS, Status.NOT_FOUND);
        }
        return jsonObject.toString();
    }

    @GetMapping(path = "/getJobResultByClassCode")
    public String getJobResultByClassCode(@RequestParam("code") String code) {
        String result = jobService.getJobResultByCode(code);
        JSONObject jsonObject;
        if (!result.equals("")) {
            return result;
        } else {
            jsonObject = new JSONObject();
            jsonObject.put(StringConstants.STATUS, Status.NOT_FOUND);
        }
        return jsonObject.toString();
    }


}
