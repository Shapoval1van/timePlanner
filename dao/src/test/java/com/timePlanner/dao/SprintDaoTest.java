package com.timePlanner.dao;


import com.timePlanner.dao.config.DaoTestConfig;
import com.timePlanner.dto.Sprint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoTestConfig.class , loader = AnnotationConfigContextLoader.class)
public class SprintDaoTest {


    @Autowired
    private SprintDao sprintDao;

    @Test
    @Transactional(readOnly = true)
    public void getSprintsForProjectTest() throws Exception {
        List<Sprint> sprints = sprintDao.getSprintsForProject(1);
        assertEquals(2, sprints.size());
        assertEquals("start", sprints.get(0).getName());
        assertEquals("next", sprints.get(1).getName());
        assertEquals("start", sprints.get(1).getDependedOn().getName());
        assertEquals(3,sprints.get(0).getTasks().size());
        assertEquals(1, sprintDao.getSprintsForProject(2).size());
    }

    @Test
    @Transactional(readOnly = true)
    public void getSprintByIdTest() throws Exception {
        Sprint sprint = sprintDao.getSprintById(1);
        assertEquals("start", sprint.getName());
        assertNull(sprint.getDescription());
        assertEquals(true, sprint.isStarted());
    }

    @Test
    @Transactional(readOnly = true)
    public void getAllSprintTest() throws Exception {
        List<Sprint> sprints = sprintDao.getAllSprint();
        assertEquals(3, sprints.size());
        assertEquals("start", sprints.get(0).getName());
    }

    @Test
    @Transactional(readOnly = true)
    public void getSprintWithDetailsTest() throws Exception {
        Sprint sprint = sprintDao.getSprintWithDetails(2);
        Sprint sprint1 = sprintDao.getSprintWithDetails(3);
        assertEquals("start",sprint.getDependedOn().getName());
        assertEquals("next", sprint.getName());
        assertEquals(1, sprint.getTasks().size());
    }


}
