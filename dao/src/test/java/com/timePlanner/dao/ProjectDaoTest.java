package com.timePlanner.dao;


import com.timePlanner.dao.config.DaoTestConfig;
import com.timePlanner.dto.Company;
import com.timePlanner.dto.Project;
import com.timePlanner.dto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Test
    @Transactional
    @Rollback
    public void updateProjectTest(){
        Project project = new Project();
        Company company = new Company();
        company.setId(1);
        project.setId(1);
        project.setName("test1");
        project.setDescription("test2");
        project.setPlanFinishDate(new Date(213123));
        project.setStarted(true);
        project.setCompany(company);
        projectDao.updateProject(project);
        Project projectActual = projectDao.getProjectById(1);
        assertEquals(project.getName(), projectActual.getName());
        assertEquals(project.getFinishDate(), projectActual.getFinishDate());
    }

    @Test
    @Transactional
    @Rollback
    public void saveProjectTest(){
        Project project = new Project();
        Company company = new Company();
        company.setId(1);
        project.setId(1);
        project.setName("test1");
        project.setDescription("test2");
        project.setPlanFinishDate(new Date(213123));
        project.setStarted(true);
        project.setCompany(company);
        projectDao.saveProject(project);
        List<Project> projectsActual = projectDao.getAllProject();
        Project projectActual = projectsActual.get(projectsActual.size()-1);
        assertEquals(project.getName(), projectActual.getName());
        assertEquals(project.getFinishDate(), projectActual.getFinishDate());
    }
}
