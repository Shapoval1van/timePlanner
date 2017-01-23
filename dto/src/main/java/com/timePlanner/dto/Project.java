package com.timePlanner.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty
    private boolean isStarted;
    @JsonProperty
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

    public  ProjectStatus getProjectStatus(){
        if(!this.isStarted && !this.isFinished){
            return ProjectStatus.CREATED;
        }else if(this.isStarted && !this.isFinished){
            return ProjectStatus.STARTED;
        }else if(this.isStarted && this.isFinished){
            return ProjectStatus.FINISHED;
        }
        return null;
    }
}
