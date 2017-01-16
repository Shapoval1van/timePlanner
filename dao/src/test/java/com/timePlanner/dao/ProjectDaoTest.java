package com.timePlanner.dao;


import com.timePlanner.dao.config.DaoTestConfig;
import com.timePlanner.dto.Project;
import com.timePlanner.dto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoTestConfig.class , loader = AnnotationConfigContextLoader.class)
public class ProjectDaoTest {

    @Autowired
    private ProjectDao projectDao;

    @Test
    @Transactional(readOnly = true)
    public void getProjectByIdTest(){
        Project project = projectDao.getProjectById(1);
        assertEquals("destroy", project.getName());
        assertEquals("destroy peaceful planet", project.getDescription());
    }

    @Test
    @Transactional(readOnly = true)
    public void getAllProjectTest(){
        assertEquals(3, projectDao.getAllProject().size());
    }

    @Test
    @Transactional(readOnly = true)
    public void getProjectWithDetailsTest(){
        Project  project = projectDao.getProjectWithDetails(1);
        User user  = project.getProjectManager();
        assertEquals("destroy", project.getName());
        assertEquals(1, project.getCustomers().size());
        assertEquals(2, project.getSprints().size());
        assertEquals("Golum",user.getFirstName());
    }

    @Test
    @Transactional(readOnly = true)
    public void getProjectForProjectManager(){
        List<Project> projects = projectDao.getProjectsForProjectManager(3);
        Project project  = projects.get(0);
        assertEquals("destroy", project.getName());
    }
}
