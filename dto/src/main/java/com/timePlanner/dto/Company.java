package com.timePlanner.dto;

import lombok.Data;

import java.util.Date;
import java.util.Set;

public @Data class Company {
    private int id;
    private String name;
    private Date dateCreation;
    private String description;
    private Set<Project> projects;

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                "| name='" + name + '\'' +
                "| dateCreation=" + dateCreation +
                "| description='" + description + '\'' +
                '}';
    }
}
