package com.timePlanner.service.impl;


import com.timePlanner.dao.TaskDao;
import com.timePlanner.dto.Project;
import com.timePlanner.dto.Sprint;
import com.timePlanner.dto.Task;
import com.timePlanner.service.EmptyResultException;
import com.timePlanner.service.ProjectService;
import com.timePlanner.service.SprintService;
import com.timePlanner.service.TaskService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger LOGGER = LogManager.getLogger(TaskServiceImpl.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SprintService sprintService;

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

    @Transactional(rollbackFor = Exception.class)
    public void updateTaskPriority(Task task) {
        taskDao.updateTaskPriority(task.getId(), task.getPriority());
    }

    public Set<Task> findTaskForProject(Project project) {
        Set<Task> tasks = Collections.synchronizedSet(new TreeSet<>(Comparator.comparing(Task::getId)));
        List<Sprint> sprints = sprintService.getSprintsForProjectWithDetails(project.getId()) ;// sorted quickly
        sprints.parallelStream().filter(s->s.getTasks()!= null).forEach(s -> tasks.addAll(s.getTasks()));
        return tasks;
    }

    @Override
    public Set<Task> findTaskForProject(int projectId) {
        Set<Task> tasks = Collections.synchronizedSet(new TreeSet<>(Comparator.comparing(Task::getId)));
        List<Sprint> sprints = sprintService.getSprintsForProjectWithDetails(projectId) ;// sorted quickly
        sprints.parallelStream().filter(s->s.getTasks()!= null).forEach(s -> tasks.addAll(s.getTasks()));
        return tasks;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateResponsibleUsers(Task task){
        return taskDao.updateResponsibleUsers(task);
    }
}
