package com.timePlanner.controller;


import com.timePlanner.dto.Project;
import com.timePlanner.dto.Sprint;
import com.timePlanner.dto.Task;
import com.timePlanner.dto.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
    private static final Logger LOGGER = LogManager.getLogger(ExelWriter.class);
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
                addSprint((Sprint)object);
            }
            if(object instanceof Task){
                addTask((Task)object);
            }
            if(object instanceof User){
                addUser((User)object);
            }
            if(object instanceof Project){
                addProject((Project)object);
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
            LOGGER.info(e);
            return null;
        }
    }

    private void addSprint(Sprint sprint){
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

    private void addTask(Task task){
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

    private void addUser(User user){
        Row row = sheet.createRow(++rowCount);
        int columnCount = 0;
        row.createCell(++columnCount).setCellValue(user.getFullName());
        row.createCell(++columnCount).setCellValue(user.getEmail());
        row.createCell(++columnCount).setCellValue(user.getRole().toString());
        row.createCell(++columnCount).setCellValue(user.getSex());
    }

    private void addProject(Project project){
        Row row = sheet.createRow(++rowCount);
        int columnCount = 0;
        row.createCell(++columnCount).setCellValue(project.getName());
        row.createCell(++columnCount).setCellValue(project.getDescription());
        row.createCell(++columnCount).setCellValue(project.getProjectManager()!=null?project.getProjectManager().getFullName():"none");
        Cell cell = row.createCell(++columnCount);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(new Date(project.getPlanFinishDate().getTime()));
        row.createCell(++columnCount).setCellValue(project.isFinished());
    }
}
