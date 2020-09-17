package com.xlsparser.models.crud;

import com.xlsparser.models.Section;

public class CreateSectionData {
    private Integer jobId;
    private Section newSection;

    public Section getNewSection() {
        return newSection;
    }

    public void setNewSection(Section newSection) {
        this.newSection = newSection;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }
}
