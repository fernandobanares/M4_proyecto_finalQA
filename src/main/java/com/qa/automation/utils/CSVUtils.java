
package com.qa.automation.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {
    
    public static Object[][] readCSV(String filePath) {
        List<Object[]> data = new ArrayList<>();
        
        try (FileReader reader = new FileReader(filePath);
             CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {
            
            for (CSVRecord record : parser) {
                data.add(new Object[]{
                    record.get("username"),
                    record.get("password"),
                    record.get("expected_result"),
                    record.get("description")
                });
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        
        return data.toArray(new Object[0][]);
    }
    
    public static Object[][] readRegisterCSV(String filePath) {
        List<Object[]> data = new ArrayList<>();
        
        try (FileReader reader = new FileReader(filePath);
             CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {
            
            for (CSVRecord record : parser) {
                data.add(new Object[]{
                    record.get("firstName"),
                    record.get("lastName"),
                    record.get("email"),
                    record.get("phone"),
                    record.get("gender"),
                    record.get("password"),
                    record.get("confirmPassword"),
                    record.get("expectedResult"),
                    record.get("description")
                });
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        
        return data.toArray(new Object[0][]);
    }
}

