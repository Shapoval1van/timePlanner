package com.timePlanner.dto;

import lombok.Data;

import java.util.Date;
import java.util.Set;

public @Data class Sprint {
    private int id;
    private String name;
    private Project project;
    private String description;
    private Date startDate;
    private Date finishDate;
    private Sprint dependetOn;
    private boolean isSterted;
    private boolean isFinished;
    private Set<Task> tasks;

    @Override
    public String toString() {
        return "Sprint{" +
                "id=" + id +
                "| name='" + name + '\'' +
                "| description='" + description + '\'' +
                "| startDate=" + startDate +
                "| finishDate=" + finishDate +
                "| isSterted=" + isSterted +
                "| isFinished=" + isFinished +
                '}';
    }
}
