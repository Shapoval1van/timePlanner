package com.timePlanner.dao;


import com.timePlanner.dto.Priority;
import com.timePlanner.dto.Task;

import java.util.List;

public interface TaskDao {
    public Task getTaskById(int id);
    public void saveTask(Task task);
    public void updateTask(Task task);
    public void updateTaskPriority(int taskId, Priority priority);
    public void setTaskStarted(int taskId);
    public void setTaskFinished(int taskId);
    public Task getTaskWithDetailsById(int id);
    public List<Task> getAllTasks();
    public List<Task> getALlTasksWithDetails();
    public boolean updateResponsibleUsers(Task task);
}
