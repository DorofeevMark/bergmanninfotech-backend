package com.xlsparser.controllers;

import com.xlsparser.models.Section;
import com.xlsparser.models.Status;
import com.xlsparser.models.StringConstants;
import com.xlsparser.models.crud.CreateSectionData;
import com.xlsparser.models.crud.DeleteSectionData;
import com.xlsparser.models.crud.ReadSectionData;
import com.xlsparser.models.crud.UpdateSectionData;
import com.xlsparser.services.SectionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrudController {
    @Autowired
    SectionService sectionService;

    @PostMapping(path = "/createSection")
    public String createSection(@RequestBody CreateSectionData createSectionData) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(StringConstants.STATUS_SUCCESS, sectionService.createSection(createSectionData));
        return jsonObject.toString();
    }

    @PostMapping(path = "/readSection")
    public String readSection(@RequestBody ReadSectionData readSectionData) {
        JSONObject jsonObject = new JSONObject();
        Section section = sectionService.getSection(readSectionData);
        if (section != null) {
            return section.toString();
        } else {
            jsonObject.put(StringConstants.STATUS, Status.NOT_FOUND);
            return jsonObject.toString();
        }
    }

    @PostMapping(path = "/updateSection")
    public String updateSection(@RequestBody UpdateSectionData updateSectionData) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(StringConstants.STATUS_SUCCESS, sectionService.updateSection(updateSectionData));
        return jsonObject.toString();
    }

    @PostMapping(path = "/deleteSection")
    public String deleteSection(@RequestBody DeleteSectionData deleteSectionData) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(StringConstants.STATUS_SUCCESS, sectionService.deleteSection(deleteSectionData));
        return jsonObject.toString();
    }


}
