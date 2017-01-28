package com.timePlanner.controller;


import com.timePlanner.dto.Sprint;
import com.timePlanner.dto.Task;
import com.timePlanner.dto.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class ExelWriter {
    private String fileName;
    private String sheetName;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFCellStyle cellStyle;
    private int rowCount = 0;


    public ExelWriter(String fileName, String  sheetName){
        this.fileName = fileName;
        this.sheetName = sheetName;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
        this.cellStyle  = workbook.createCellStyle();
        XSSFDataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));
    }


    public void addData(Collection<?> collection){
        Iterator i = collection.iterator();
        while (i.hasNext()){
            Object object =  i.next();
            if(object instanceof Sprint){
                Sprint sprint = (Sprint)object;
                Row row = sheet.createRow(++rowCount);
                int columnCount = 0;
                row.createCell(++columnCount).setCellValue(sprint.getName());
                row.createCell(++columnCount).setCellValue(sprint.getDescription());
                Cell cell = row.createCell(++columnCount);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new Date(sprint.getPlanedFinishDate().getTime()));
                row.createCell(++columnCount).setCellValue(sprint.getDependedOn()==null? "none": sprint.getDependedOn().getName());
                row.createCell(++columnCount).setCellValue(sprint.getTasks()==null? 0: sprint.getTasks().size());
            }
            if(object instanceof Task){
                Task task = (Task)object;
                Row row = sheet.createRow(++rowCount);
                int columnCount = 0;
                row.createCell(++columnCount).setCellValue(task.getName());
                row.createCell(++columnCount).setCellValue(task.getDescription());
                row.createCell(++columnCount).setCellValue(task.getPriority().toString());
                Cell cell = row.createCell(++columnCount);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new Date(task.getPlanFinishDate().getTime()));
                StringBuilder stringBuilder = new StringBuilder();
                if(task.getTasks()!=null){
                    for(Task t : task.getTasks()){
                        stringBuilder.append(t.getName()+"; ");
                    }
                }
                row.createCell(++columnCount).setCellValue(stringBuilder.toString());
                stringBuilder = new StringBuilder();
                if(task.getUsers()!=null){
                    for(User u : task.getUsers()){
                        stringBuilder.append(u.getFullName()+"; ");
                    }
                }
                row.createCell(++columnCount).setCellValue(stringBuilder.toString());
            }
            if(object instanceof User){
                User user =  (User)object;
                Row row = sheet.createRow(++rowCount);
                int columnCount = 0;
                row.createCell(++columnCount).setCellValue(user.getFullName());
                row.createCell(++columnCount).setCellValue(user.getEmail());
                row.createCell(++columnCount).setCellValue(user.getSex());
            }
        }
    }

    public void addHeader(String ... title) {
        ++rowCount;
        Row row = sheet.createRow(++rowCount);
        int columnCount = 0;
        for(int i =0; i < title.length; i++){
            row.createCell(++columnCount).setCellValue(title[i]);
        }
    }

    public String write(){
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
            return fileName;
        }catch (IOException e){
            return null;
        }
    }
}
