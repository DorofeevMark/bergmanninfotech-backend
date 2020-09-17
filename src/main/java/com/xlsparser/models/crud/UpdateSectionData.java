package com.xlsparser.models.crud;

import com.xlsparser.models.Section;

public class UpdateSectionData {
    private Integer jobId;
    private String sectionName;
    private Section newSection;

    public Integer getJobId() {
        return jobId;
    }

    public Section getNewSection() {
        return newSection;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public void setNewSection(Section newSection) {
        this.newSection = newSection;
    }

    public String getSectionName() {
        return sectionName;
    }
}