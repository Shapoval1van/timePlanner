package com.timePlanner.dto;

import lombok.Data;

public @Data class Customer {
    protected int id;
    protected String companyName;
    protected String description;
    protected Project project;
    protected User user;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                "| companyName='" + companyName + '\'' +
                "| description='" + description + '\'' +
                '}';
    }
}
