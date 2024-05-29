package com.aditya_shrikhande.electronic_store.helper;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


public class DataToExcelHelper {

    public static ByteArrayInputStream createExcelFile(List<?> data, String[] headers, String sheetName, String filePath) {

        Workbook workbook =new HSSFWorkbook();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


        try {
            Sheet sheet = workbook.createSheet(sheetName);

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Create data rows
            int rowNum = 1;
            for (Object item : data) {
                Row dataRow = sheet.createRow(rowNum++);
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = dataRow.createCell(i);
                    String propertyName = headers[i];
                    Object propertyValue = getPropertyByName(item, propertyName);
                    cell.setCellValue(propertyValue.toString());
                }
            }

            // Write the workbook to a file
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    // Implement a method to get a property by name from the object
    private static Object getPropertyByName(Object item, String propertyName) {
        try {
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(item.getClass()).getPropertyDescriptors();

            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (propertyDescriptor.getName().equals(propertyName)) {
                    Method getterMethod = propertyDescriptor.getReadMethod();
                    if (getterMethod != null) {
                        return getterMethod.invoke(item);
                    }
                }
            }
            return null;
        } catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
            throw new RuntimeException(e);
        }
    }
}
