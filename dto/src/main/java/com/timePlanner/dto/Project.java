package com.timePlanner.dto;


import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

public @Data class Project {
    private int id;
    private String name;
    private String description;
    private Company company;
    private Set<Customer> customers;
    private User projectManager;
    private boolean isStarted;
    private boolean isFinished;
    private Date startDate;
    private Date finishDate;
    private Date planFinishDate;
    private List<Sprint> sprints;

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                "| name='" + name + '\'' +
                "| description='" + description + '\'' +
                "| isFinished=" + isFinished +
                "| startDate=" + startDate +
                "| finishDate=" + finishDate +
                "| planFinishDate" + planFinishDate +
                '}';
    }
}
