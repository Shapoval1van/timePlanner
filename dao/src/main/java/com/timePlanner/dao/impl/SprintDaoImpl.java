package com.timePlanner.dao.impl;

import com.timePlanner.dao.SprintDao;
import com.timePlanner.dao.extractors.SprintExtractor;
import com.timePlanner.dao.mappers.SprintMapper;
import com.timePlanner.dto.Sprint;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class SprintDaoImpl implements SprintDao , InitializingBean{
    private JdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = LogManager.getLogger(SprintDaoImpl.class);
    private final String FIND_BY_ID = "SELECT " +
            "       s.id \"sprintId\", s.name \"sprintName\", s.description \"sprintDescription\",s.start_date \"sprintSDate\", s.finish_date \"sprintFDate\",\n" +
            "            s.is_started \"sprintIsStarted\", s.is_finished \"sprintIsFinished\", s.plan_finish_date \"sprintPFinish\"\n" +
            "            FROM sprint AS s\n" +
            "            WHERE s.id = ?;";
    private final String FIND_ALL = "SELECT " +
            "       s.id \"sprintId\", s.name \"sprintName\", s.description \"sprintDescription\",s.start_date \"sprintSDate\", s.finish_date \"sprintFDate\",\n" +
            "            s.is_started \"sprintIsStarted\", s.is_finished \"sprintIsFinished\", s.plan_finish_date \"sprintPFinish\"\n" +
            "            FROM sprint AS s\n";
    private final String FIND_WITH_DETAILS = "SELECT" +
            "  s.id \"sprintId\", s.name \"sprintName\", s.description \"sprintDescription\",s.start_date \"sprintSDate\", s.finish_date \"sprintFDate\",\n" +
            "        s.is_started \"sprintIsStarted\", s.is_finished \"sprintIsFinished\", s.plan_finish_date \"sprintPFinish\",\n" +
            "  s_previous.id \"sprintIdPrev\",s_previous.name \"sprintNamePrev\", s_previous.description \"sprintDescriptionPrev\"," +
            "       s_previous.start_date \"sprintSDatePrev\", s_previous.finish_date \"sprintFDatePrev\",\n" +
            "       s_previous.is_started \"sprintIsStartedPrev\", s_previous.is_finished \"sprintIsFinishedPrev\",s.plan_finish_date \"sprintPFinishPrev\",\n" +
            "  t.id \"taskId\", t.name \"taskName\", t.description \"taskDescription\", estimate, t.start_date \"taskSDate\", t.finish_date \"taskFDate\",\n" +
            "       t.is_started \"taskIsStarted\", t.is_finished \"taskIsFinished\", t.plan_finish_date \"taskPFinish\", t.priority, \n" +
            "   p.id  \"projectId\", p.name  \"projectName\", p.description  \"projectDescription\", p.start_date  \"projectSDate\", \n" +
            "       p.finish_date  \"projectFDate\", p.plan_finish_date \"projectPFinish\", p.is_started  \"projectIsStarted\", p.is_finished  \"projectIsFinished\" " +
            " FROM sprint AS s\n" +
            "       LEFT JOIN sprint AS s_previous ON s.dependent_on = s_previous.id\n" +
            "       LEFT JOIN task AS t ON t.sprint_id = s.id\n" +
            "       JOIN project AS p ON s.projectid = p.id" +
            "  WHERE s.id = ? ;";
    private final String FIND_SPRINTS_WITH_DETAILS_FOR_PROJECT = "SELECT" +
            "  s.id \"sprintId\", s.name \"sprintName\", s.description \"sprintDescription\",s.start_date \"sprintSDate\", s.finish_date \"sprintFDate\",\n" +
            "        s.is_started \"sprintIsStarted\", s.is_finished \"sprintIsFinished\", s.plan_finish_date \"sprintPFinish\",\n" +
            "  s_previous.id \"sprintIdPrev\",s_previous.name \"sprintNamePrev\", s_previous.description \"sprintDescriptionPrev\"," +
            "       s_previous.start_date \"sprintSDatePrev\", s_previous.finish_date \"sprintFDatePrev\",\n" +
            "       s_previous.is_started \"sprintIsStartedPrev\", s_previous.is_finished \"sprintIsFinishedPrev\",s.plan_finish_date \"sprintPFinishPrev\",\n" +
            "  t.id \"taskId\", t.name \"taskName\", t.description \"taskDescription\", estimate, t.start_date \"taskSDate\", t.finish_date \"taskFDate\",\n" +
            "       t.is_started \"taskIsStarted\", t.is_finished \"taskIsFinished\", t.plan_finish_date \"taskPFinish\", t.priority, \n" +
            "   p.id  \"projectId\", p.name  \"projectName\", p.description  \"projectDescription\", p.start_date  \"projectSDate\", \n" +
            "       p.finish_date  \"projectFDate\", p.plan_finish_date \"projectPFinish\", p.is_started  \"projectIsStarted\", p.is_finished  \"projectIsFinished\" " +
            " FROM sprint AS s\n" +
            "       LEFT JOIN sprint AS s_previous ON s.dependent_on = s_previous.id\n" +
            "       LEFT JOIN task AS t ON t.sprint_id = s.id\n" +
            "       JOIN project AS p ON s.projectid = p.id" +
            "  WHERE p.id = ?  ORDER BY s.id;";
    private final  String SAVE_SPRINT = "INSERT INTO sprint VALUES(DEFAULT ,?,?,?,?,?,?,?,?,?);";
    private final String UPDATE_SPRINT = "UPDATE sprint SET name=?, projectid=?, description=?,start_date=?, finish_date=?, " +
            "plan_finish_date=?, dependent_on=?,is_started=?,is_finished=? WHERE id = ?;";
    private final String SET_SPRINT_STARTED = "UPDATE sprint SET start_date=?, is_started = TRUE WHERE id = ?";
    private final String SET_SPRINT_FINISHED = "UPDATE sprint SET finish_date=?, is_finished = TRUE WHERE id = ?";
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Sprint getSprintById(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID , new Object[]{id}, new SprintMapper());
    }

    @Override
    public void saveSprint(Sprint sprint) {
        final int SAVE_STATEMENT = 0;
        jdbcTemplate.update(SAVE_SPRINT, new SprintPreparedStatementSetter(sprint, SAVE_STATEMENT));
    }

    @Override
    public void updateSprint(Sprint sprint) {
        final int UPDATE_STATEMENT = 1;
        jdbcTemplate.update(UPDATE_SPRINT, new SprintPreparedStatementSetter(sprint, UPDATE_STATEMENT));
    }

    @Override
    public List<Sprint> getAllSprint() {
        return jdbcTemplate.query(FIND_ALL, new SprintMapper());
    }

    @Override
    public Sprint getSprintWithDetails(int id) {
        List<Sprint> sprints = jdbcTemplate.query(FIND_WITH_DETAILS, new Object[]{id}, new SprintExtractor());
        return sprints.size() == 0?null:sprints.get(0);
    }

    @Override
    public void setSprintStarted(int sprintId) {
        Date date = new Date(System.currentTimeMillis());
        jdbcTemplate.update(SET_SPRINT_STARTED, date, sprintId);
    }

    @Override
    public void setSprintFinished(int sprintId) {
        Date date = new Date(System.currentTimeMillis());
        jdbcTemplate.update(SET_SPRINT_FINISHED, date, sprintId);
    }

    @Override
    public List<Sprint> getSprintsForProjectWithDetails(int projectId) {
        return jdbcTemplate.query(FIND_SPRINTS_WITH_DETAILS_FOR_PROJECT, new Object[]{projectId}, new SprintExtractor());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(jdbcTemplate == null){
            LOGGER.error("Must set jdbcTemplate");
            throw new BeanCreationException("Must set jdbcTemplate");
        }
    }

    private final static class SprintPreparedStatementSetter implements PreparedStatementSetter {
        private Sprint sprint;
        private int typeStatement;
        SprintPreparedStatementSetter(Sprint sprint, int typeStatement) {
            this.sprint = sprint;
            this.typeStatement = typeStatement;
        }

        @Override
        public void setValues(PreparedStatement preparedStatement) throws SQLException {
            Integer projectId = sprint.getProject()!=null?sprint.getProject().getId():null;
            Integer dependedSprintId = sprint.getDependedOn()!=null?sprint.getDependedOn().getId():null;
            Long startDate = sprint.getStartDate() != null ? sprint.getStartDate().getTime() : null;
            Long finishDate = sprint.getFinishDate() != null ? sprint.getFinishDate().getTime() : null;
            Long planFinishDate = sprint.getPlanedFinishDate() != null ? sprint.getPlanedFinishDate().getTime() : null;
            int i = 1;
            preparedStatement.setString(i++, sprint.getName());
            if(projectId==null){
                preparedStatement.setNull(i++, Types.INTEGER);
            }else{
                preparedStatement.setInt(i++, projectId);
            }
            preparedStatement.setString(i++, sprint.getDescription());
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
            if(dependedSprintId==null){
                preparedStatement.setNull(i++, Types.INTEGER);
            }else {
                preparedStatement.setInt(i++,dependedSprintId);
            }
            preparedStatement.setBoolean(i++, sprint.isStarted());
            preparedStatement.setBoolean(i++, sprint.isFinished());
            if(typeStatement == 1) {
                preparedStatement.setInt(i++, sprint.getId());
            }
        }
    }
}
