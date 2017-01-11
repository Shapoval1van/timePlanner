package com.timePlanner.dao.mappers;


import com.timePlanner.dto.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper  implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet resultSet, int i) throws SQLException {
        Task task = new Task();
        task.setId(resultSet.getInt("taskId"));
        task.setName(resultSet.getString("taskName"));
        task.setDescription(resultSet.getString("taskDescription"));
        task.setEstimate(resultSet.getFloat("estimate"));
        task.setStartDate(resultSet.getDate("taskSDate"));
        task.setFinishDate(resultSet.getDate("taskFDate"));
        task.setStarted(resultSet.getBoolean("taskIsStarted"));
        task.setFinished(resultSet.getBoolean("taskIsFinished"));
        return task;
    }
}

