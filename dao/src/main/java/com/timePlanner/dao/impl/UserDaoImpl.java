package com.timePlanner.dao.impl;


import com.timePlanner.dao.UserDao;
import com.timePlanner.dao.extractors.UserExtractor;
import com.timePlanner.dao.mappers.UserMapper;
import com.timePlanner.dto.User;
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
public class UserDaoImpl implements UserDao, InitializingBean {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    private final String FIND_BY_ID = "SELECT id \"userId\", f_name, l_name, password, email, phone,roleid, birth_date, sex FROM users WHERE id = ?;";
    private final String FIND_BY_EMAIL = "SELECT id \"userId\", f_name, l_name, password, email, phone,roleid, birth_date, sex FROM users WHERE email = ?;";
    private final String FIND_ALL = "SELECT id \"userId\", f_name, l_name, password, email, phone,roleid, birth_date, sex FROM users;";
    private final String FIND_USER_WITH_DETAILS_BY_ID ="SELECT " +
            "   u.id \"userId\", f_name, l_name, password, roleId, email,phone, birth_date, sex,\n" +
            "   t.id \"taskId\", t.name \"taskName\", t.description \"taskDescription\", " +
            "           estimate, start_date \"taskSDate\", finish_date \"taskFDate\"," +
            "            is_started \"taskIsStarted\", is_finished \"taskIsFinished\", t.plan_finish_date \"taskPFinish\", t.priority,\n" +
            "   c.id \"companyId\", c.name \"companyName\", c.date_creation, c.description \"companyDescription\"" +
            " FROM users as u\n" +
            "  FULL OUTER JOIN company AS c ON c.id = u.company_id\n" +
            "  LEFT JOIN user_task AS ut ON u.id = ut.user_id\n" +
            "  LEFT JOIN task AS t ON ut.task_id = t.id where u.id=?;";
    private final String FIND_ALL_WITH_DETAILS ="SELECT " +
            "   u.id \"userId\", f_name, l_name, password, roleId, email,phone, birth_date, sex,\n" +
            "   t.id \"taskId\", t.name \"taskName\", t.description \"taskDescription\", " +
            "           estimate, start_date \"taskSDate\", finish_date \"taskFDate\"," +
            "            is_started \"taskIsStarted\", is_finished \"taskIsFinished\", t.plan_finish_date \"taskPFinish\", t.priority,\n" +
            "   c.id \"companyId\", c.name \"companyName\", c.date_creation, c.description \"companyDescription\"" +
            " FROM users as u\n" +
            "   FULL OUTER JOIN company AS c ON c.id = u.company_id\n"+
            "   LEFT JOIN user_task AS ut ON u.id = ut.user_id\n"+
            "   LEFT JOIN task AS t ON ut.task_id = t.id;\n";
    private final String SAVE_USER = "INSERT INTO users VALUES (DEFAULT,?,?,?,?,?,?,?,?,?);";
    private final String UPDATE_USER = "UPDATE users SET f_name=?, l_name=?, password=?, roleid=?, company_id=?, email=?, phone=?, birth_date=?, sex=? WHERE id = ?;";
    private final String FIND_USERS_FOR_COMPANY = "SELECT id \"userId\", f_name, l_name, password, email, phone,roleid, birth_date, sex FROM users WHERE company_id = ?;";
    private final String FIND_EMPLOYEES_FOR_PROJECT = "SELECT\n" +
            "  u.id \"userId\", f_name, l_name, password, roleId, email, phone, birth_date, sex,\n" +
            "  t.id \"taskId\", t.name \"taskName\", t.description \"taskDescription\", estimate, t.start_date \"taskSDate\", t.finish_date \"taskFDate\",\n" +
            "    t.is_started \"taskIsStarted\", t.is_finished \"taskIsFinished\", t.plan_finish_date \"taskPFinish\", t.priority,\n" +
            "  c.id \"companyId\", c.name \"companyName\", c.date_creation, c.description \"companyDescription\"\n" +
            "FROM users AS u\n" +
            "  JOIN company AS C ON C.id = u.company_id\n" +
            "  JOIN user_task AS ut ON ut.user_id = u.id\n" +
            "  JOIN task AS t ON t.id = ut.task_id\n" +
            "  JOIN sprint AS S ON t.sprint_id = s.id\n" +
            "WHERE s.projectid = ?;";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(BasicDataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public User getUserById(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID,new Object[]{id}, new UserMapper());
    }

    @Override
    public User getUserByEmail(String email) {
        return jdbcTemplate.queryForObject(FIND_BY_EMAIL, new Object[]{email}, new UserMapper());
    }

    @Override
    public List<User> getAllUsers(){
        return jdbcTemplate.query(FIND_ALL, new UserMapper());
    }

    @Override
    public User getUserWithDetailsById(int id){
        return jdbcTemplate.query(FIND_USER_WITH_DETAILS_BY_ID, new Object[]{id}, new UserExtractor()).get(0);
    }


    @Override
    public List<User> getALlUsersWithDetails(){
        return jdbcTemplate.query(FIND_ALL_WITH_DETAILS, new UserExtractor());
    }

    @Override
    public List<User> getAllUsersForCompany(int companyId) {
        return jdbcTemplate.query(FIND_USERS_FOR_COMPANY, new Object[]{companyId},new UserMapper());
    }

    @Override
    public List<User> getEmployeesForProject(int projectId) {
        return jdbcTemplate.query(FIND_EMPLOYEES_FOR_PROJECT, new Object[]{projectId},new UserExtractor());
    }

    @Override
    public void saveUser(User user) {
        final int SAVE_STATEMENT = 0;
        jdbcTemplate.update(SAVE_USER, new UserPreparedStatementSetter(user,SAVE_STATEMENT));
    }

    @Override
    public void updateUser(User user){
        final int UPDATE_STATEMENT = 1;
        jdbcTemplate.update(UPDATE_USER, new UserPreparedStatementSetter(user,UPDATE_STATEMENT));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(jdbcTemplate == null){
            LOGGER.error("Must set jdbcTemplate");
            throw new BeanCreationException("Must set jdbcTemplate");
        }
    }

    private final static class UserPreparedStatementSetter implements PreparedStatementSetter{
        private User user;
        private int typeStatement;
        UserPreparedStatementSetter(User user, int typeStatement) {
            this.user = user;
            this.typeStatement = typeStatement;
        }

        @Override
        public void setValues(PreparedStatement preparedStatement) throws SQLException {
            Integer companyId = user.getCompany()!= null ? user.getCompany().getId(): null;
            Integer roleId = user.getRole()!= null ? user.getRole().ordinal()+1: null;
            Long date = user.getBirthDate()!=null ? user.getBirthDate().getTime() :null;
            int i = 1;
            preparedStatement.setString(i++, user.getFirstName());
            preparedStatement.setString(i++, user.getLastName());
            preparedStatement.setString(i++, user.getPassword());
            if(roleId == null){
                preparedStatement.setNull(i++, Types.INTEGER);
            }else {
                preparedStatement.setInt(i++, roleId);
            }
            if(companyId == null){
                preparedStatement.setNull(i++, Types.INTEGER);
            }else {
                preparedStatement.setInt(i++, companyId);
            }
            preparedStatement.setString(i++, user.getEmail());
            preparedStatement.setString(i++, user.getPhone() );
            if(date == null){
                preparedStatement.setNull(i++, Types.DATE);
            }else {
                preparedStatement.setDate(i++, new Date(date));
            }
            preparedStatement.setInt(i++, user.getSex());
            if(typeStatement == 1) {
                preparedStatement.setInt(i++, user.getId());
            }
        }
    }
}
