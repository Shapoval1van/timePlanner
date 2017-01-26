package com.timePlanner.dao.impl;


import com.timePlanner.dao.TaskDao;
import com.timePlanner.dao.extractors.TaskExtractor;
import com.timePlanner.dao.mappers.TaskMapper;
import com.timePlanner.dto.Priority;
import com.timePlanner.dto.Task;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class TaskDaoImpl implements TaskDao, InitializingBean {
    private static final Logger LOGGER = LogManager.getLogger(TaskDaoImpl.class);

    private final String FIND_BY_ID = "SELECT" +
            " id \"taskId\", name \"taskName\", estimate, start_date \"taskSDate\", finish_date \"taskFDate\" ," +
            " is_started \"taskIsStarted\",description \"taskDescription\", is_finished \"taskIsFinished\"," +
            " plan_finish_date \"taskPFinish\", priority FROM task WHERE id = ?;";
    private final String FIND_ALL = "SELECT" +
            " id \"taskId\", name \"taskName\", estimate, start_date \"taskSDate\", finish_date \"taskFDate\" ," +
            " is_started \"taskIsStarted\",description \"taskDescription\", is_finished \"taskIsFinished\"," +
            " plan_finish_date \"taskPFinish\", priority FROM task ORDER BY id;";
    private final String SAVE_TASK = "INSERT INTO task VALUES (DEFAULT,?,?,?,?,?,?,?,?,?,?);";
    private final String FIND_ALL_WITH_DETAILS = "SELECT" +
            "  t.id \"taskId\", t.name \"taskName\",t.description \"taskDescription\", t.estimate,t.start_date \"taskSDate\",\n" +
            "      t.finish_date \"taskFDate\",t.is_started \"taskIsStarted\",t.is_finished \"taskIsFinished\"," +
            "      t.plan_finish_date \"taskPFinish\", t.priority,\n" +
            "  u.id \"userId\",  f_name,  l_name,  password, roleId,  email,  phone,  birth_date,  sex,\n" +
            "  s.id \"sprintId\",s.name \"sprintName\",s.description \"sprintDescription\",s.start_date \"sprintSDate\",\n" +
            "      s.finish_date \"sprintFDate\",s.is_started \"sprintIsStarted\",s.is_finished \"sprintIsFinished\",s.plan_finish_date \"sprintPFinish\",\n" +
            "  t_depended.id \"taskIdDepended\",t_depended.name \"taskNameDepended\",t_depended.estimate \"estimateDepended\",t_depended.start_date \"taskSDateDepended\",\n" +
            "      t_depended.finish_date \"taskFDateDepended\",t_depended.is_started  \"taskIsStartedDepended\",t_depended.is_finished \"taskIsFinishedDepended\"\n" +
            "   FROM task AS t\n" +
            "       JOIN sprint AS s ON s.id = t.sprint_id\n" +
            "       LEFT JOIN user_task AS ut ON t.id = ut.task_id\n" +
            "       LEFT JOIN users AS u ON ut.user_id = u.id\n" +
            "       LEFT JOIN task_dependency AS td ON t.id = td.task_id\n" +
            "       LEFT JOIN task AS t_depended ON td.depended_task_id = t_depended.id ORDER BY t.id";
    private final String FIND_BY_ID_WITH_DETAILS = "SELECT" +
            "  t.id \"taskId\", t.name \"taskName\",t.description \"taskDescription\", t.estimate,t.start_date \"taskSDate\",\n" +
            "      t.finish_date \"taskFDate\",t.is_started \"taskIsStarted\",t.is_finished \"taskIsFinished\"," +
            "      t.plan_finish_date \"taskPFinish\", t.priority,\n" +
            "  u.id \"userId\",  f_name,  l_name,  password, roleId,  email,  phone,  birth_date,  sex,\n" +
            "  s.id \"sprintId\",s.name \"sprintName\",s.description \"sprintDescription\",s.start_date \"sprintSDate\",\n" +
            "      s.finish_date \"sprintFDate\",s.is_started \"sprintIsStarted\",s.is_finished \"sprintIsFinished\", s.plan_finish_date \"sprintPFinish\",\n" +
            "  t_depended.id \"taskIdDepended\",t_depended.name \"taskNameDepended\",t_depended.estimate \"estimateDepended\",t_depended.start_date \"taskSDateDepended\",\n" +
            "      t_depended.finish_date \"taskFDateDepended\",t_depended.is_started  \"taskIsStartedDepended\",t_depended.is_finished \"taskIsFinishedDepended\"\n" +
            "FROM task AS t\n" +
            "  JOIN sprint AS s ON s.id = t.sprint_id\n" +
            "  LEFT JOIN user_task AS ut ON t.id = ut.task_id\n" +
            "  LEFT JOIN users AS u ON ut.user_id = u.id\n" +
            "  LEFT JOIN task_dependency AS td ON t.id = td.task_id\n" +
            "  LEFT JOIN task AS t_depended ON td.depended_task_id = t_depended.id " +
            "WHERE t.id = ?;";
    private final String UPDATE_TASK = "UPDATE task SET name=?, sprint_id=?, start_date=? , finish_date=?, " +
            "is_started=?, is_finished=?, plan_finish_date=?, description=?, priority=?,  estimate=? WHERE id = ?;";
    private final String UPDATE_TASK_PRIORITY = "UPDATE task SET priority=? WHERE id = ?;";
    private final String UPDATE_TASK_DEPENDENCY = "INSERT INTO task_dependency VALUES(?,?);";
    private final String UPDATE_RESPONSINBE_USERS = "INSERT INTO user_task VALUES(?,?);";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Task getTaskById(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, new Object[]{id}, new TaskMapper());
    }

    @Override
    public void saveTask(Task task) {
        final int SAVE_STATEMENT = 0;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rows = jdbcTemplate.update(new TaskPreparedStatementCreator(task, SAVE_STATEMENT, SAVE_TASK), keyHolder);
        if (rows > 0) {
            if (task.getTasks() != null && task.getTasks().size() != 0) {
                task.setId((Integer) keyHolder.getKeys().get("id"));
                task.getTasks().forEach(dependedTask -> jdbcTemplate.update(UPDATE_TASK_DEPENDENCY, task.getId(), dependedTask.getId()));
            }
        }
    }

    @Override
    public void updateTask(Task task) {
        final int UPDATE_STATEMENT = 1;
        jdbcTemplate.update(new TaskPreparedStatementCreator(task, UPDATE_STATEMENT, UPDATE_TASK));
    }

    @Override
    public void updateTaskPriority(int taskId, Priority priority) {
        jdbcTemplate.update(UPDATE_TASK_PRIORITY, priority.ordinal() + 1, taskId);
    }

    @Override
    public Task getTaskWithDetailsById(int id) {
        List<Task> tasks = jdbcTemplate.query(FIND_BY_ID_WITH_DETAILS, new Object[]{id}, new TaskExtractor());
        return tasks.size() == 0 ? null : tasks.get(0);
    }

    @Override
    public List<Task> getAllTasks() {
        return jdbcTemplate.query(FIND_ALL, new TaskMapper());
    }

    @Override
    public List<Task> getALlTasksWithDetails() {
        return jdbcTemplate.query(FIND_ALL_WITH_DETAILS, new TaskExtractor());
    }

    @Override
    public boolean updateResponsibleUsers(Task task) {
        if (task.getId()!=0 & (task.getUsers()!=null && task.getUsers().size()!=0)){
            task.getUsers().forEach(user -> jdbcTemplate.update(UPDATE_RESPONSINBE_USERS, user.getId(), task.getId()));
            return true;
        }
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (jdbcTemplate == null) {
            LOGGER.error("Must set jdbcTemplate");
            throw new BeanCreationException("Must set jdbcTemplate");
        }
    }


    private final static class TaskPreparedStatementCreator implements PreparedStatementCreator {
        private Task task;
        private int typeStatement;
        private String statement;

        TaskPreparedStatementCreator(Task task, int typeStatement, String statement) {
            this.task = task;
            this.typeStatement = typeStatement;
            this.statement = statement;
        }

        @Override
        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
            PreparedStatement preparedStatement = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            Integer sprintId = task.getSprint() != null ? task.getSprint().getId() : null;
            Long sDate = task.getStartDate() != null ? task.getStartDate().getTime() : null;
            Long fDate = task.getFinishDate() != null ? task.getFinishDate().getTime() : null;
            Long planFinishDate = task.getPlanFinishDate() != null ? task.getPlanFinishDate().getTime() : null;
            Integer priority = task.getPriority() != null ? task.getPriority().ordinal() + 1 : null;
            int i = 1;
            preparedStatement.setString(i++, task.getName());
            if (sprintId != null) {
                preparedStatement.setInt(i++, sprintId);
            } else {
                preparedStatement.setNull(i++, Types.INTEGER);
            }
            if (sDate != null) {
                preparedStatement.setDate(i++, new Date(sDate));
            } else {
                preparedStatement.setNull(i++, Types.DATE);
            }
            if (fDate != null) {
                preparedStatement.setDate(i++, new Date(fDate));
            } else {
                preparedStatement.setNull(i++, Types.DATE);
            }
            preparedStatement.setBoolean(i++, task.isStarted());
            preparedStatement.setBoolean(i++, task.isFinished());
            if (planFinishDate != null) {
                preparedStatement.setDate(i++, new Date(planFinishDate));
            } else {
                preparedStatement.setNull(i++, Types.DATE);
            }
            preparedStatement.setString(i++, task.getDescription());
            if (priority != null) {
                preparedStatement.setInt(i++, priority);
            } else {
                preparedStatement.setNull(i++, Types.INTEGER);
            }
            preparedStatement.setDouble(i++, task.getEstimate());
            if (typeStatement == 1) {
                preparedStatement.setInt(i++, task.getId());
            }
            return preparedStatement;
        }
    }
}
