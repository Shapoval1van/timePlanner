package com.timePlanner.dao;


import com.timePlanner.dto.Task;

import java.util.List;

public interface TaskDao {
    public Task getTaskById(int id);
    public void saveTask(Task task);
    public void updateTask(Task task);
    public Task getTaskWithDetailsById(int id);
    public List<Task> getAllTasks();
    public List<Task> getALlTasksWithDetails();
}
