package com.timePlanner.service.impl;

import com.timePlanner.dao.SprintDao;
import com.timePlanner.dto.Sprint;
import com.timePlanner.service.EmptyResultException;
import com.timePlanner.service.SprintService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintServiceImpl implements SprintService {
    private static final Logger LOGGER = LogManager.getLogger(SprintServiceImpl.class);
    @Autowired
    private SprintDao sprintDao;

    public Sprint getSprintById(int id) throws EmptyResultException {
        try{
            return sprintDao.getSprintById(id);
        }catch (EmptyResultDataAccessException up){
            LOGGER.info("Sprint with id " + id + "not found");
             throw  new EmptyResultException(up);

        }
    }

    public void saveSprint(Sprint sprint) {

    }

    public void updateSprint(Sprint sprint) {

    }

    public List<Sprint> getAllSprint() {
        return sprintDao.getAllSprint();
    }

    public Sprint getSprintWithDetails(int id) {
        return sprintDao.getSprintWithDetails(id);
    }

    public List<Sprint> getSprintsForProject(int projectId) {
        return sprintDao.getSprintsForProject(projectId);
    }
}
