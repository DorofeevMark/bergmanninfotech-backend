package com.xlsparser.models.crud;


public class ReadSectionData {
    private Integer jobId;
    private String sectionName;

    public Integer getJobId() {
        return jobId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
