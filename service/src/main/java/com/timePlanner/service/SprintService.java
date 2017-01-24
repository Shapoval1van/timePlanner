package com.timePlanner.service;


import com.timePlanner.dto.Sprint;

import java.util.List;

public interface SprintService {
    public Sprint getSprintById(int id) throws EmptyResultException;
    public void saveSprint(Sprint sprint);
    public void updateSprint(Sprint sprint);
    public List<Sprint> getAllSprint();
    public Sprint getSprintWithDetails(int id);
    public List<Sprint> getSprintsForProjectWithDetails(int projectId);
}
