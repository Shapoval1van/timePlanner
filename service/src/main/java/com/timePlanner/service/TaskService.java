package com.timePlanner.service;

import com.timePlanner.dto.Project;
import com.timePlanner.dto.Task;

import java.util.List;
import java.util.Set;

public interface TaskService {
    public Task getTaskById(int id) throws  EmptyResultException;
    public void saveTask(Task task);
    public void updateTask(Task task);
    public Task getTaskWithDetailsById(int id);
    public List<Task> getAllTasks();
    public List<Task> getALlTasksWithDetails();
    public void updateTaskPriority(Task task);
    public Set<Task> findTaskForProject(Project project);
    public Set<Task> findTaskForProject(int projectId);
    public boolean  updateResponsibleUsers(Task task);
}
