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
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
            "  WHERE p.id = ? ;";

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

    }

    @Override
    public void updateSprint(Sprint sprint) {

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
    public List<Sprint> getSprintsForProject(int projectId) {
        return jdbcTemplate.query(FIND_SPRINTS_WITH_DETAILS_FOR_PROJECT, new Object[]{projectId}, new SprintExtractor());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(jdbcTemplate == null){
            LOGGER.error("Must set jdbcTemplate");
            throw new BeanCreationException("Must set jdbcTemplate");
        }
    }
}
