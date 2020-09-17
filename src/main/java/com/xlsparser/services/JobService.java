package com.xlsparser.services;


import com.xlsparser.models.*;
import com.xlsparser.repositories.FileRepository;
import com.xlsparser.repositories.GeoClassRepository;
import com.xlsparser.repositories.JobRepository;
import com.xlsparser.repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private GeoClassRepository geoClassRepository;

    public Job addJob(java.io.File dataFile) {
        File file = fileService.getFileByName(dataFile.getName());
        if (file != null) {
            Job job = new Job();
            job.setFile(file);
            job.setStatus(StringConstants.STATUS_PROCESSING);
            job.setSections(new HashSet<>());
            file.getJobs().add(job);
            fileRepository.save(file);
            jobRepository.save(job);
            workerService.doJob(job, dataFile);
            return job;
        }
        return null;
    }

    public String getJobResult(Integer jobId) {
        Optional<Job> optional = jobRepository.findById(jobId);
        if (optional.isPresent()) {
            Job job = optional.get();
            if (job.getStatus().equals(StringConstants.STATUS_DONE)) {
                return job.getSections().toString();
            } else {
                return StringConstants.STATUS_PROCESSING;
            }
        }
        return "";
    }

    public String getJobResultBySectionName(String sectionName) {
        Optional<List<Section>> optional = sectionRepository.findAllByName(sectionName);
        if (optional.isPresent()) {
            Set<Job> jobSet = new HashSet<>();
            List<Section> sections = optional.get();
            for (Section section : sections) {
                jobSet.add(section.getJob());
            }
            return jobSet.toString();
        }
        return "";
    }

    public String getJobResultByCode(String code) {
        Optional<List<GeoClass>> optional = geoClassRepository.findAllByCode(code);
        if (optional.isPresent()) {
            Set<Job> jobSet = new HashSet<>();
            List<GeoClass> geoClasses = optional.get();
            for (GeoClass geoClass : geoClasses) {
                jobSet.add(geoClass.getSection().getJob());
            }
            return jobSet.toString();
        }
        return "";
    }

    public String getJobResultByClassName(String className) {
        Optional<List<GeoClass>> optional = geoClassRepository.findAllByName(className);
        if (optional.isPresent()) {
            Set<Job> jobSet = new HashSet<>();
            List<GeoClass> geoClasses = optional.get();
            for (GeoClass geoClass : geoClasses) {
                jobSet.add(geoClass.getSection().getJob());
            }
            return jobSet.toString();
        }
        return "";
    }


}
