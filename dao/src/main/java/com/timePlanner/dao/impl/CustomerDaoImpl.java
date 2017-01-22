package com.timePlanner.dao.impl;

import com.timePlanner.dao.CustomerDao;
import com.timePlanner.dao.extractors.CustomerExtractor;
import com.timePlanner.dto.Customer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class CustomerDaoImpl implements CustomerDao, InitializingBean {
    private static final Logger LOGGER = LogManager.getLogger(CustomerDaoImpl.class);

    private final String FIND_BY_COMPANY_ID = "SELECT " +
            "   c.id \"customerId\", c.company_name \"customerCompanyName\", c.description \"customerDescription\",\n" +
            "   u.id \"userId\", f_name, l_name, password, roleId, email, phone, birth_date, sex,\n" +
            "   p.id \"projectId\", p.name \"projectName\", p.description \"projectDescription\", p.start_date \"projectSDate\"," +
            "   p.finish_date \"projectFDate\", p.is_finished \"projectIsFinished\", p.plan_finish_date \"projectPFinish\", p.is_started  \"projectIsStarted\"\n" +
            "   from customer as c\n" +
            "       JOIN users AS u ON c.user_id = u.id\n" +
            "       JOIN project AS p ON p.id = c.project_id\n" +
            "   WHERE p.company_id = ?;";
    private final String FIND_ALL = "SELECT " +
            "   c.id \"customerId\", c.company_name \"customerCompanyName\", c.description \"customerDescription\",\n" +
            "   u.id \"userId\", f_name, l_name, password, roleId, email, phone, birth_date, sex,\n" +
            "   p.id \"projectId\", p.name \"projectName\", p.description \"projectDescription\", p.start_date \"projectSDate\"," +
            "   p.finish_date \"projectFDate\", p.is_finished \"projectIsFinished\", p.plan_finish_date \"projectPFinish\", p.is_started  \"projectIsStarted\"\n" +
            "   from customer as c\n" +
            "       JOIN users AS u ON c.user_id = u.id\n" +
            "       JOIN project AS p ON p.id = c.project_id;";
    private String SAVE_CUSTOMER = "INSERT INTO  customer VALUES(DEFAULT, ?,?,?,?)";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Customer> getCustomersWithDetailsByCompanyId(int companyId) {
        return jdbcTemplate.query(FIND_BY_COMPANY_ID, new Object[]{companyId}, new CustomerExtractor());
    }

    @Override
    public void saveCustomer(Customer customer) {
        final int SAVE_STATEMENT = 0;
        jdbcTemplate.update(SAVE_CUSTOMER, new CustomerPreparedStatementSetter(customer, SAVE_STATEMENT));
    }

    @Override
    public void updateCustomer(Customer customer) {

    }

    @Override
    public List<Customer> getAllCustomerWithDetails() {
        return jdbcTemplate.query(FIND_ALL, new CustomerExtractor());
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (jdbcTemplate == null) {
            LOGGER.error("Must set jdbcTemplate");
            throw new BeanCreationException("Must set jdbcTemplate");
        }
    }

    private final static class CustomerPreparedStatementSetter implements PreparedStatementSetter {
        private Customer customer;
        private int typeStatement;

        CustomerPreparedStatementSetter(Customer customer, int typeStatement) {
            this.customer = customer;
            this.typeStatement = typeStatement;
        }

        @Override
        public void setValues(PreparedStatement preparedStatement) throws SQLException {
            Integer projectId = customer.getProject() != null ? customer.getProject().getId() : null;
            Integer userId = customer.getUser() != null ? customer.getUser().getId() : null;
            int i = 1;
            preparedStatement.setString(i++, customer.getCompanyName());
            preparedStatement.setString(i++, customer.getDescription());
            if (userId == null) {
                preparedStatement.setNull(i++, Types.INTEGER);
            } else {
                preparedStatement.setInt(i++, userId);
            }
            if (projectId == null) {
                preparedStatement.setNull(i++, Types.INTEGER);
            } else {
                preparedStatement.setInt(i++, projectId);
            }
            if (typeStatement == 1) {
                preparedStatement.setInt(i++, customer.getId());
            }
        }
    }
}
