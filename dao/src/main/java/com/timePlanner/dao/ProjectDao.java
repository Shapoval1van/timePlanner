package com.timePlanner.dao;

import com.timePlanner.dto.Project;

import java.util.List;

public interface ProjectDao {
    public Project getProjectById(int id);
    public List<Project> getProjectsForProjectManager(int userId);
    public void setProjectStarted(int projectId);
    public void setProjectFinished(int projectId);
    public void saveProject(Project project);
    public void updateProject(Project project);
    public List<Project> getAllProject();
    public Project getProjectWithDetails(int id);
}
