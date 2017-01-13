package com.timePlanner.service.impl;


import com.timePlanner.dao.ProjectDao;
import com.timePlanner.dto.Project;
import com.timePlanner.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectDao projectDao;

    @Transactional(readOnly = true)
    public Project getProjectById(int id) {
        return projectDao.getProjectById(id);
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
}