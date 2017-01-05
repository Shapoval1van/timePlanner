package com.timePlanner.dto;


import lombok.Data;

import java.util.Date;
import java.util.Set;

public @Data class Task {
    private int id;
    private String name;
    private String description;
    private float estimate;
    private Date startDate;
    private Date finishDate;
    private boolean isSterted;
    private boolean isFinished;
    private Set<User> users;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                "| name='" + name + '\'' +
                "| description='" + description + '\'' +
                "| estimate=" + estimate +
                "| startDate=" + startDate +
                "| finishDate=" + finishDate +
                "| isSterted=" + isSterted +
                "| isFinished=" + isFinished +
                '}';
    }
}
