package com.timePlanner.dao.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.timePlanner.dao")
public class DaoConfig {
    private static final Logger LOGGER = LogManager.getLogger(DaoConfig.class);
    @Resource
    private Environment environment;

    private Properties getDbConfig(){
        Properties dbConfig = new Properties();
        String configFileName;
        List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());
        String activeProfile = activeProfiles.stream()
                .filter(x->x.equals("dev"))
                .findFirst()
                .orElse(null);
        if("local".equals(activeProfile)) {
            configFileName = "db_config.properties";
        }else {
            configFileName = "db_config_amazon.properties";
        }
        try (InputStream fis = getClass().getClassLoader().getResourceAsStream("db_config.properties")) {
            if (fis == null) {
                throw new FileNotFoundException();
            } else {
                dbConfig.load(fis);
                return dbConfig;
            }
        } catch (IOException up) {
            if (up instanceof FileNotFoundException) {
                LOGGER.error("File not found \n Program exit with error:", up);
            } else {
                LOGGER.error("Properties can't load \n Program exit with error: ", up);
            }
            System.exit(0);
            return dbConfig;
        }
    }


    @Bean(name = "dataSource")
    @Profile("dev")
    public BasicDataSource localDataSource(){
        Properties dbConfig = getDbConfig();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/timePlannerDev");
        dataSource.setUsername(dbConfig.getProperty("username"));
        dataSource.setPassword(dbConfig.getProperty("password"));
        dataSource.setInitialSize(20);
        dataSource.setMaxActive(100);
        return  dataSource;
    }

    @Bean(name = "dataSource")
    @Profile("prod")
    public BasicDataSource dataSource(){
        Properties dbConfig = getDbConfig();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://timeplanner.csaqgsfea9tq.us-west-2.rds.amazonaws.com:5432/timePlanner");
        dataSource.setUsername(dbConfig.getProperty("username"));
        dataSource.setPassword(dbConfig.getProperty("password"));
        dataSource.setInitialSize(20);
        dataSource.setMaxActive(100);
        return  dataSource;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }
}
