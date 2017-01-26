package com.timePlanner.service;

import com.timePlanner.dto.Project;

import java.util.List;

public interface ProjectService {
    public Project getProjectById(int id) throws EmptyResultException;
    public void saveProject(Project project);
    public void updateProject(Project project);
    public List<Project> getAllProject();
    public Project getProjectWithDetails(int id);
    public List<Project> getProjectsForProjectManager(int userId);
    public Project getProjectForTask(int taskId);
    public void setProjectFinished(int projectId);
    public void setProjectStarted(int projectId);
}
