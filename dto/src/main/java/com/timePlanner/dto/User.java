package com.timePlanner.dto;


import lombok.Data;

import java.util.Date;
import java.util.Set;

public @Data class User {
    private int id;
    protected String firstName;
    protected String lastName;
    protected String password;
    protected Role role;
    protected Company company;
    protected String email;
    protected String phone;
    protected Date birthDate;
    protected int sex;
    protected Set<Task> tasks;
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                "| firstName='" + firstName + '\'' +
                "| lastName='" + lastName + '\'' +
                "| password='" + password + '\'' +
                "| role=" + role +
                "| email='" + email + '\'' +
                "| phone='" + phone + '\'' +
                "| birthDate=" + birthDate +
                "| sex=" + sex +
                '}';
    }
}
