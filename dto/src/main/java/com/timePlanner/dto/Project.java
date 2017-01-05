package com.timePlanner.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

public @Data class Project {
    private int id;
    private String name;
    private String description;
    private Company company;
    private Customer customer;
    private boolean isFinished;
    private Date startDate;
    private Date finishDate;
    private ArrayList<Sprint> sprints;

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                "| name='" + name + '\'' +
                "| description='" + description + '\'' +
                "| isFinished=" + isFinished +
                "| startDate=" + startDate +
                "| finishDate=" + finishDate +
                '}';
    }
}
