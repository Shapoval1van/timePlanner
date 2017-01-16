package com.timePlanner.service.impl;


import com.timePlanner.dao.TaskDao;
import com.timePlanner.dto.Task;
import com.timePlanner.service.EmptyResultException;
import com.timePlanner.service.TaskService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger LOGGER = LogManager.getLogger(TaskServiceImpl.class);
    @Autowired
    private TaskDao taskDao;

    @Transactional(readOnly = true)
    public Task getTaskById(int id) throws EmptyResultException {
        try {
            return taskDao.getTaskById(id);
        }catch(EmptyResultDataAccessException up){
            LOGGER.info("Task with id " + id + "not found");
            throw new EmptyResultException(up);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveTask(Task task) {
        taskDao.saveTask(task);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateTask(Task task) {
        taskDao.updateTask(task);
    }

    @Transactional(readOnly = true)
    public Task getTaskWithDetailsById(int id) {
        return taskDao.getTaskWithDetailsById(id);
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return taskDao.getAllTasks();
    }

    @Transactional(readOnly = true)
    public List<Task> getALlTasksWithDetails() {
        return taskDao.getALlTasksWithDetails();
    }
}
