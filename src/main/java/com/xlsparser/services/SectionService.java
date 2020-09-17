package com.xlsparser.services;


import com.xlsparser.models.GeoClass;
import com.xlsparser.models.Job;
import com.xlsparser.models.Section;
import com.xlsparser.models.crud.CreateSectionData;
import com.xlsparser.models.crud.DeleteSectionData;
import com.xlsparser.models.crud.ReadSectionData;
import com.xlsparser.models.crud.UpdateSectionData;
import com.xlsparser.repositories.GeoClassRepository;
import com.xlsparser.repositories.JobRepository;
import com.xlsparser.repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class SectionService {
    @Autowired
    SectionRepository sectionRepository;
    @Autowired
    GeoClassRepository geoClassRepository;
    @Autowired
    JobRepository jobRepository;

    @Transactional
    public boolean updateSection(UpdateSectionData updateSectionData) {
        Optional<Section> optionalSection = sectionRepository.findByJobIdAndName(updateSectionData.getJobId()
                , updateSectionData.getSectionName());
        if (optionalSection.isPresent()) {
            Section section = optionalSection.get();
            Section newSection = updateSectionData.getNewSection();
            section.setName(newSection.getName());
            geoClassRepository.deleteAllBySectionId(section.getId());
            Set<GeoClass> newGeoClasses = new HashSet<>();
            for (GeoClass geoClass : newSection.getGeologicalClasses()) {
                geoClass.setSection(section);
                geoClassRepository.save(geoClass);
                newGeoClasses.add(geoClass);
            }
            section.setGeologicalClasses(newGeoClasses);
            sectionRepository.save(section);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean createSection(CreateSectionData createSectionData) {
        Optional<Job> optionalJob = jobRepository.findById(createSectionData.getJobId());
        if (optionalJob.isPresent()) {
            Job job = optionalJob.get();
            Section newSection = createSectionData.getNewSection();
            newSection.setJob(job);
            job.getSections().add(newSection);
            sectionRepository.save(newSection);
            jobRepository.save(job);
            for (GeoClass geoClass : newSection.getGeologicalClasses()) {
                geoClass.setSection(newSection);
                geoClassRepository.save(geoClass);
            }
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteSection(DeleteSectionData deleteSectionData) {
        Optional<Section> optionalSection = sectionRepository.findByJobIdAndName(deleteSectionData.getJobId()
                , deleteSectionData.getSectionName());
        Optional<Job> optionalJob = jobRepository.findById(deleteSectionData.getJobId());
        if (optionalSection.isPresent() && optionalJob.isPresent()) {
            Section section = optionalSection.get();
            Job job = optionalJob.get();
            geoClassRepository.deleteAllBySectionId(section.getId());
            sectionRepository.deleteById(section.getId());
            job.getSections().remove(section);
            jobRepository.save(job);
            return true;
        }
        return false;
    }

    public Section getSection(ReadSectionData readSectionData) {
        Optional<Section> optionalSection = sectionRepository.findByJobIdAndName(readSectionData.getJobId()
                , readSectionData.getSectionName());
        return optionalSection.orElse(null);
    }

}
