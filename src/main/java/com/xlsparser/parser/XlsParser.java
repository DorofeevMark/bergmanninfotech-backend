package com.xlsparser.parser;

import com.xlsparser.models.GeoClass;
import com.xlsparser.models.Section;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class XlsParser {
    public static List<Section> parse(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
            HSSFSheet sheet = wb.getSheetAt(0);

            List<Section> sectionList = new ArrayList<>();

            boolean skipHeaders = false;

            for (Row row : sheet) {
                if (!skipHeaders) {
                    skipHeaders = true;
                    continue;
                }
                int position = 0;
                Section section = new Section();
                Stack<GeoClass> geoClassStack = new Stack<>();
                for (Cell cell : row) {
                    if (position == 0) {
                        section.setName(cell.getStringCellValue());
                        section.setGeologicalClasses(new HashSet<>());
                    } else if (position % 2 == 1) {
                        GeoClass geoClass = new GeoClass();
                        geoClass.setSection(section);
                        geoClass.setName(cell.getStringCellValue());
                        geoClassStack.push(geoClass);
                    } else {
                        GeoClass geoClass = geoClassStack.pop();
                        if (formulaEvaluator.evaluateInCell(cell).getCellType() == CellType.STRING) {
                            geoClass.setCode(cell.getStringCellValue());
                        } else {
                            double d = cell.getNumericCellValue();
                            int x = (int) d;
                            geoClass.setCode(String.valueOf(x));
                        }
                        section.getGeologicalClasses().add(geoClass);
                    }
                    position += 1;
                }
                if (section.getName() != null && !section.getName().equals("")) {
                    sectionList.add(section);
                }
            }

            return sectionList;

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
