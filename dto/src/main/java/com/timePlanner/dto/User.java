package com.timePlanner.dto;


import lombok.Data;

import java.util.Date;
import java.util.Set;

public @Data class User {
    private int id;
    private String firstName;
    private String lastName;
    private String password;
    private Role role;
    private Company company;
    private String email;
    private String phone;
    private Date birthDate;
    private int sex;
    private Set<Task> tasks;

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
