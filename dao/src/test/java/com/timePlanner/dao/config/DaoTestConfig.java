package com.timePlanner.dao.config;


import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.timePlanner.dao")
public class DaoTestConfig {
    private static final Logger LOGGER = LogManager.getLogger(DaoConfig.class);

    private Properties getDbConfig(){
        Properties dbConfig = new Properties();
        try (InputStream fis = getClass().getClassLoader().getResourceAsStream("db_test_config.properties")) {
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
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        Properties dbConfig = getDbConfig();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/timePlanner");
        dataSource.setUsername(dbConfig.getProperty("username"));
        dataSource.setPassword(dbConfig.getProperty("password"));
        dataSource.setInitialSize(20);
        dataSource.setMaxActive(100);
        return dataSource;
    }


}
