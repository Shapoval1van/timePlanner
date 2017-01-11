package com.timePlanner.dao.mappers;

import com.timePlanner.dto.Sprint;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SprintMapper implements RowMapper<Sprint>{


    @Override
    public Sprint mapRow(ResultSet resultSet, int i) throws SQLException {
        Sprint sprint = new Sprint();
        sprint.setId(resultSet.getInt("sprintId"));
        sprint.setName(resultSet.getString("sprintName"));
        sprint.setDescription(resultSet.getString("sprintDescription"));
        sprint.setStartDate(resultSet.getDate("sprintSDate"));
        sprint.setFinishDate(resultSet.getDate("sprintFDate"));
        sprint.setStarted(resultSet.getBoolean("sprintIsStarted"));
        sprint.setFinished(resultSet.getBoolean("sprintIsFinished"));
        return sprint;
    }
}
