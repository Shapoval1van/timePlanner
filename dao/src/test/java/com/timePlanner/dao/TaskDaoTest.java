package com.timePlanner.dao;

import com.timePlanner.dao.config.DaoTestConfig;
import com.timePlanner.dto.Priority;
import com.timePlanner.dto.Sprint;
import com.timePlanner.dto.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoTestConfig.class, loader = AnnotationConfigContextLoader.class)
public class TaskDaoTest {

    @Autowired
    private TaskDao taskDao;

    @Test
    @Transactional(readOnly = true)
    public void getTaskByIdTest() {
        Task task = taskDao.getTaskById(1);
        assertEquals("first Task", task.getName());
        assertNull(task.getSprint());
        assertEquals(new Double(2), new Double(task.getEstimate()));
    }

    @Test
    @Transactional
    @Rollback
    public void updateTaskPriorityTest() throws Exception {
        Task task = taskDao.getTaskById(1);
        assertEquals(Priority.LOW, task.getPriority());
        task.setPriority(Priority.CRITICAL);
        taskDao.updateTaskPriority(task.getId(), task.getPriority());
        task = taskDao.getTaskById(1);
        assertEquals(Priority.CRITICAL, task.getPriority());
    }

    @Test
    @Transactional(readOnly = true)
    public void getAllTaskTest() {
        List<Task> tasks = taskDao.getAllTasks();
        assertEquals(4, tasks.size());
        assertNull(tasks.get(0).getSprint());
        assertEquals(new Double(2), new Double(tasks.get(0).getEstimate()));
    }

    @Test
    @Transactional(readOnly = true)
    public void getAllTaskWishDetailsTest() {
        List<Task> tasks = taskDao.getALlTasksWithDetails();
        assertEquals(4, tasks.size());
        assertNotNull(tasks.get(0).getSprint());
        assertEquals(2, tasks.get(0).getUsers().size());
        assertEquals(1, tasks.get(1).getUsers().size());
        assertEquals(2, tasks.get(2).getTasks().size());
    }

    @Test
    @Transactional(readOnly = true)
    public void getByIdWishDetailsTest() {
        Task task = taskDao.getTaskWithDetailsById(1);
        assertEquals("first Task", task.getName());
        assertNotNull(task.getSprint());
        assertEquals(new Double(2), new Double(task.getEstimate()));
    }

    @Test
    @Transactional
    @Rollback()
    public void saveTaskTest() {
        Set<Task> taskSet = new HashSet<>();
        for (int i = 1; i <= 3; i++) {
            Task task = new Task();
            task.setId(i);
            taskSet.add(task);
        }
        Task task = new Task();
        task.setName("test");
        task.setDescription("desc");
        task.setPlanFinishDate(new Date(System.currentTimeMillis()));
        Sprint sprint = new Sprint();
        sprint.setId(1);
        task.setSprint(sprint);
        task.setTasks(taskSet);
        taskDao.saveTask(task);
        List<Task> tasks = taskDao.getALlTasksWithDetails();
        Task actualTask = tasks.get(tasks.size() - 1);
        assertEquals(task.getName(), actualTask.getName());
        assertEquals(task.getSprint().getId(), actualTask.getSprint().getId());
        assertEquals(task.getTasks().size(), actualTask.getTasks().size());
    }


    @Test
    @Transactional
    @Rollback()
    public void updateTaskTest() {
        Task task = new Task();
        task.setId(1);
        task.setName("test");
        task.setDescription("desc");
        task.setPlanFinishDate(new Date(System.currentTimeMillis()));
        Sprint sprint = new Sprint();
        sprint.setId(1);
        task.setSprint(sprint);
        taskDao.updateTask(task);
        Task actualTask = taskDao.getTaskWithDetailsById(1);
        assertEquals(task.getName(), actualTask.getName());
        assertEquals(task.getSprint().getId(), actualTask.getSprint().getId());
    }
}
