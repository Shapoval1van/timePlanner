package com.timePlanner.dto;

import lombok.Data;

public @Data class Customer {
    private int id;
    private String companyName;
    private String description;
    private Company company;
    private User user;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                "| companyName='" + companyName + '\'' +
                "| description='" + description + '\'' +
                '}';
    }
}
