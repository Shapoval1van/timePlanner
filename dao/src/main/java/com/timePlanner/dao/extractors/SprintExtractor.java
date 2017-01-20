package com.timePlanner.dao.extractors;

import com.timePlanner.dao.mappers.ProjectMapper;
import com.timePlanner.dao.mappers.SprintMapper;
import com.timePlanner.dao.mappers.TaskMapper;
import com.timePlanner.dto.Sprint;
import com.timePlanner.dto.Task;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SprintExtractor implements ResultSetExtractor<List<Sprint>> {
    @Override
    public List<Sprint> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, Sprint> sprintMap = new HashMap<>();
        SprintMapper sprintMapper = new SprintMapper();
        ProjectMapper projectMapper = new ProjectMapper();
        TaskMapper taskMapper = new TaskMapper();
        while (resultSet.next()) {
            int sprintId = resultSet.getInt("sprintId");
            Sprint sprint = sprintMap.get(sprintId);
            if (sprint == null) {
                sprint = sprintMapper.mapRow(resultSet, 0);
                sprint.setProject(projectMapper.mapRow(resultSet,0));
                if(resultSet.getInt("sprintIdPrev")!=0){
                    Sprint sprintPrevious = new Sprint();
                    sprintPrevious.setId(resultSet.getInt("sprintIdPrev"));
                    sprintPrevious.setName(resultSet.getString("sprintNamePrev"));
                    sprintPrevious.setDescription(resultSet.getString("sprintDescriptionPrev"));
                    sprintPrevious.setStartDate(resultSet.getDate("sprintSDatePrev"));
                    sprintPrevious.setFinishDate(resultSet.getDate("sprintFDatePrev"));
                    sprintPrevious.setPlanedFinishDate(resultSet.getDate("sprintPFinishPrev"));
                    sprintPrevious.setStarted(resultSet.getBoolean("sprintIsStartedPrev"));
                    sprintPrevious.setFinished(resultSet.getBoolean("sprintIsFinishedPrev"));
                    sprint.setDependedOn(sprintPrevious);
                }
                sprintMap.put(sprintId, sprint);
            }
            if (resultSet.getInt("taskId") != 0) {
                Task task = taskMapper.mapRow(resultSet, 0);
                Set<Task> taskTemp = sprint.getTasks();
                if(taskTemp!=null){
                    taskTemp.add(task);
                }else {
                    taskTemp = new HashSet<>();
                    taskTemp.add(task);
                }
                sprint.setTasks(taskTemp);
            }
        }
        return new ArrayList<>(sprintMap.values());
    }
}