package com.timePlanner.service.impl;


import com.timePlanner.dao.ProjectDao;
import com.timePlanner.dto.Project;
import com.timePlanner.service.EmptyResultException;
import com.timePlanner.service.ProjectService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private static final Logger LOGGER = LogManager.getLogger(ProjectServiceImpl.class);
    @Autowired
    private ProjectDao projectDao;

    @Transactional(readOnly = true)
    public Project getProjectById(int id) throws EmptyResultException {
        try{
            return projectDao.getProjectById(id);
        }catch (EmptyResultDataAccessException up){
            LOGGER.info("Project with id " + id + "not found");
            throw new EmptyResultException(up);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveProject(Project project) {
        projectDao.saveProject(project);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateProject(Project project) {
        projectDao.updateProject(project);
    }

    @Transactional(readOnly = true)
    public List<Project> getAllProject() {
        return projectDao.getAllProject();
    }

    @Transactional(readOnly = true)
    public Project getProjectWithDetails(int id) {
        return projectDao.getProjectWithDetails(id);
    }

    @Transactional(readOnly = true)
    public List<Project> getProjectsForProjectManager(int userId) {
        return projectDao.getProjectsForProjectManager(userId);
    }

    @Override
    public void setProjectFinished(int projectId) {
        projectDao.setProjectFinished(projectId);
    }

    @Override
    public void setProjectStarted(int projectId) {
        projectDao.setProjectStarted(projectId);
    }
}
