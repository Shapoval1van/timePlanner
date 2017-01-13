package com.timePlanner.dao.mappers;


import com.timePlanner.dto.Project;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectMapper  implements RowMapper<Project>{
    @Override
    public Project mapRow(ResultSet resultSet, int i) throws SQLException {
        Project project  = new Project();
        project.setId(resultSet.getInt("projectId"));
        project.setName(resultSet.getString("projectName"));
        project.setDescription(resultSet.getString("projectDescription"));
        project.setStartDate(resultSet.getDate("projectSDate"));
        project.setFinishDate(resultSet.getDate("projectFDate"));
        project.setFinished(resultSet.getBoolean("projectIsFinished"));
        return project;
    }
}
