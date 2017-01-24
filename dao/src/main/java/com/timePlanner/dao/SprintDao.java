package com.timePlanner.dao;


import com.timePlanner.dto.Sprint;

import java.util.List;

public interface SprintDao {
    public Sprint getSprintById(int id);
    public void saveSprint(Sprint sprint);
    public void updateSprint(Sprint sprint);
    public List<Sprint> getAllSprint();
    public Sprint getSprintWithDetails(int id);
    public List<Sprint> getSprintsForProjectWithDetails(int projectId);
}
