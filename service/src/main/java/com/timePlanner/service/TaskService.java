package com.timePlanner.service;

import com.timePlanner.dto.Task;

import java.util.List;

public interface TaskService {
    public Task getTaskById(int id) throws  EmptyResultException;
    public void saveTask(Task task);
    public void updateTask(Task task);
    public Task getTaskWithDetailsById(int id);
    public List<Task> getAllTasks();
    public List<Task> getALlTasksWithDetails();
}
