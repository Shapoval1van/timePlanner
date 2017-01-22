package com.timePlanner.dao.impl;

import com.timePlanner.dao.ProjectDao;
import com.timePlanner.dao.extractors.ProjectExtractor;
import com.timePlanner.dao.mappers.ProjectMapper;
import com.timePlanner.dto.Project;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class ProjectDaoImpl implements ProjectDao, InitializingBean {
    private static final Logger LOGGER = LogManager.getLogger(ProjectDaoImpl.class);

    private final String FIND_BY_ID = "SELECT " +
            "        id  \"projectId\", name  \"projectName\", description  \"projectDescription\", start_date  \"projectSDate\",\n" +
            "            finish_date  \"projectFDate\",plan_finish_date \"projectPFinish\", is_started  \"projectIsStarted\", is_finished  \"projectIsFinished\"\n" +
            "            FROM project\n" +
            "            WHERE id = ?;";
    private final String FIND_ALL = "SELECT" +
            "        id  \"projectId\", name  \"projectName\", description  \"projectDescription\", start_date  \"projectSDate\",\n" +
            "             finish_date  \"projectFDate\",plan_finish_date \"projectPFinish\", is_started  \"projectIsStarted\", is_finished  \"projectIsFinished\",\n" +
            "             plan_finish_date  \"projectPFinish\" " +
            "             FROM project\n;";
    private final String FIND_WITH_DETAILS = "SELECT " +
            "       p.id  \"projectId\", p.name  \"projectName\", p.description  \"projectDescription\", p.start_date  \"projectSDate\",\n" +
            "             p.finish_date  \"projectFDate\", p.plan_finish_date \"projectPFinish\", p.is_started  \"projectIsStarted\", p.is_finished  \"projectIsFinished\",\n" +
            "       cu.id \"customerId\", cu.company_name \"customerCompanyName\",cu.description \"customerDescription\",\n" +
            "       c.id  \"companyId\", c.name  \"companyName\", c.date_creation, c.description  \"companyDescription\",\n" +
            "       s.id \"sprintId\", s.name \"sprintName\", s.description \"sprintDescription\",s.start_date \"sprintSDate\", s.finish_date \"sprintFDate\",\n" +
            "            s.is_started \"sprintIsStarted\", s.is_finished \"sprintIsFinished\", s.plan_finish_date \"sprintPFinish\",\n" +
            "       u.id \"userId\", f_name, l_name, password, roleId, email,phone, birth_date, sex\n" +
            "            FROM project AS p\n" +
            "              FULL OUTER JOIN company AS c ON c.id = p.company_id\n" +
            "              FUll OUTER JOIN users AS u ON u.id = p.project_manager_id" +
            "              LEFT JOIN customer AS cu ON cu.project_id = p.id\n" +
            "              LEFT JOIN sprint AS s  ON s.projectid = p.id\n" +
            "            WHERE p.id = ?;";
    private final String FIND_PROJECTS_FOR_PM = "SELECT" +
            "       p.id  \"projectId\", p.name  \"projectName\", p.description  \"projectDescription\", p.start_date  \"projectSDate\",\n" +
            "           p.finish_date  \"projectFDate\", p.plan_finish_date \"projectPFinish\", p.is_started  \"projectIsStarted\", p.is_finished  \"projectIsFinished\" \n" +
            "       FROM project AS p" +
            "       WHERE p.project_manager_id = ?";
    private final String SAVE_USER = "INSERT INTO project VALUES (DEFAULT,?,?,?,?,?,?,?,?,?);";
    private final String UPDATE_USER = "UPDATE project SET name=?, description=?, company_id=?, start_date=?, finish_date=?, plan_finish_date=?, project_manager_id=?, is_started=?, is_finished=? WHERE id = ?;";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(BasicDataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Project getProjectById(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, new Object[]{id}, new ProjectMapper());
    }

    @Override
    public List<Project> getProjectsForProjectManager(int userId) {
        return jdbcTemplate.query(FIND_PROJECTS_FOR_PM, new Object[]{userId}, new ProjectMapper());
    }

    @Override
    public void saveProject(Project project) {
        final int SAVE_STATEMENT = 0;
        jdbcTemplate.update(SAVE_USER, new ProjectDaoImpl.ProjectPreparedStatementSetter(project, SAVE_STATEMENT));
    }

    @Override
    public void updateProject(Project project) {
        final int UPDATE_STATEMENT = 1;
        jdbcTemplate.update(UPDATE_USER, new ProjectDaoImpl.ProjectPreparedStatementSetter(project, UPDATE_STATEMENT));
    }

    @Override
    public List<Project> getAllProject() {
        return jdbcTemplate.query(FIND_ALL, new ProjectMapper());
    }

    @Override
    public Project getProjectWithDetails(int id) {
        List<Project> projects = jdbcTemplate.query(FIND_WITH_DETAILS, new Object[]{id}, new ProjectExtractor());
        return projects.size() == 0?null:projects.get(0);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (jdbcTemplate == null) {
            LOGGER.error("Must set jdbcTemplate");
            throw new BeanCreationException("Must set jdbcTemplate");
        }
    }

    private final static class ProjectPreparedStatementSetter implements PreparedStatementSetter {
        private Project project;
        private int typeStatement;

        ProjectPreparedStatementSetter(Project project, int typeStatement) {
            this.project = project;
            this.typeStatement = typeStatement;
        }

        @Override
        public void setValues(PreparedStatement preparedStatement) throws SQLException {
            Integer companyId = project.getCompany() != null ? project.getCompany().getId() : null;
            Integer userId = project.getProjectManager() != null ? project.getProjectManager().getId() : null;
            Long startDate = project.getStartDate() != null ? project.getStartDate().getTime() : null;
            Long finishDate = project.getFinishDate() != null ? project.getFinishDate().getTime() : null;
            Long planFinishDate = project.getPlanFinishDate() != null ? project.getPlanFinishDate().getTime() : null;
            int i = 1;
            preparedStatement.setString(i++, project.getName());
            preparedStatement.setString(i++, project.getDescription());
            if (companyId == null) {
                preparedStatement.setNull(i++, Types.INTEGER);
            } else {
                preparedStatement.setInt(i++, companyId);
            }
            if (startDate == null) {
                preparedStatement.setNull(i++, Types.DATE);
            } else {
                preparedStatement.setDate(i++, new Date(startDate));
            }
            if (finishDate == null) {
                preparedStatement.setNull(i++, Types.DATE);
            } else {
                preparedStatement.setDate(i++, new Date(finishDate));
            }
            if (planFinishDate == null) {
                preparedStatement.setNull(i++, Types.DATE);
            } else {
                preparedStatement.setDate(i++, new Date(planFinishDate));
            }
            if (userId == null) {
                preparedStatement.setNull(i++, Types.INTEGER);
            } else {
                preparedStatement.setInt(i++, userId);
            }
            preparedStatement.setBoolean(i++, project.isStarted());
            preparedStatement.setBoolean(i++, project.isFinished());
            if (typeStatement == 1) {
                preparedStatement.setInt(i++, project.getId());
            }
        }
    }
}
