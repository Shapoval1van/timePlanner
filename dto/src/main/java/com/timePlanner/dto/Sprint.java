package com.timePlanner.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Sprint dependedOn;
    private Date planedFinishDate;
    @JsonProperty
    private boolean isStarted;
    @JsonProperty
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
                "| isStarted=" + isStarted +
                "| isFinished=" + isFinished +
                '}';
    }

    public Status getSprintStatus(){
        if(!this.isStarted && !this.isFinished){
            return Status.CREATED;
        }else if(this.isStarted && !this.isFinished){
            return Status.STARTED;
        }else if(this.isStarted && this.isFinished){
            return Status.FINISHED;
        }
        return null;
    }
}
