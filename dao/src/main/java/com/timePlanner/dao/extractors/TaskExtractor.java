package com.timePlanner.dao.extractors;


import com.timePlanner.dao.mappers.SprintMapper;
import com.timePlanner.dao.mappers.TaskMapper;
import com.timePlanner.dao.mappers.UserMapper;
import com.timePlanner.dto.Sprint;
import com.timePlanner.dto.Task;
import com.timePlanner.dto.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TaskExtractor implements ResultSetExtractor<List<Task>> {

    @Override
    public List<Task> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, Task> taskMap = new HashMap<>();
        TaskMapper taskMapper = new TaskMapper();
        SprintMapper sprintMapper = new SprintMapper();
        UserMapper userMapper = new UserMapper();
        Set<User> users = new HashSet();
        Set<Task> dependedTask = new HashSet();
        while(resultSet.next()){
            int taskId = resultSet.getInt("taskId");
            Task task = taskMap.get(taskId);
            if(task == null){
                dependedTask = new HashSet();
                users = new HashSet<>();
                task =  taskMapper.mapRow(resultSet, 0);
                Sprint sprint = sprintMapper.mapRow(resultSet, 0);
                task.setSprint(sprint);
                taskMap.put(taskId,task);
            }
            if(resultSet.getInt("userId") != 0){
                User user = userMapper.mapRow(resultSet, 0);
                users.add(user);
                task.setUsers(users);
            }
            if(resultSet.getInt("taskIdDepended")!=0){
                Task taskDepended =  new Task();
                taskDepended.setId(resultSet.getInt("taskIdDepended"));
                taskDepended.setName(resultSet.getString("taskNameDepended"));
                taskDepended.setEstimate(resultSet.getFloat("estimateDepended"));
                taskDepended.setStartDate(resultSet.getDate("taskSDateDepended"));
                taskDepended.setFinishDate(resultSet.getDate("taskFDateDepended"));
                taskDepended.setStarted(resultSet.getBoolean("taskIsStartedDepended"));
                taskDepended.setFinished(resultSet.getBoolean("taskIsFinishedDepended"));
                dependedTask.add(taskDepended);
                task.setTasks(dependedTask);
            }
        }
        return new ArrayList<Task>(taskMap.values());
    }
}
