package com.xlsparser.services;

import com.xlsparser.models.GeoClass;
import com.xlsparser.models.Job;
import com.xlsparser.models.Section;
import com.xlsparser.models.StringConstants;
import com.xlsparser.parser.XlsParser;
import com.xlsparser.repositories.GeoClassRepository;
import com.xlsparser.repositories.JobRepository;
import com.xlsparser.repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {

    @Autowired
    SectionRepository sectionRepository;
    @Autowired
    GeoClassRepository geoClassRepository;
    @Autowired
    JobRepository jobRepository;


    @Async
    public synchronized void doJob(Job job, java.io.File dataFile) {
        List<Section> sectionList = XlsParser.parse(dataFile);
        for (Section section : sectionList) {
            section.setJob(job);
            job.getSections().add(section);
            sectionRepository.save(section);
            for (GeoClass geoClass : section.getGeologicalClasses()) {
                geoClassRepository.save(geoClass);
            }
        }
        job.setStatus(StringConstants.STATUS_DONE);
        jobRepository.save(job);
    }
}
