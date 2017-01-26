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
        String profiles[] = environment.getActiveProfiles();
        String activeProfile = profiles[0];
        if("dev".equals(activeProfile)) {
            configFileName = "db_config.properties";
        }else {
            configFileName = "db_heroku_config.properties";
        }
        try (InputStream fis = getClass().getClassLoader().getResourceAsStream(configFileName)) {
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

    @Profile("dev")
    @Bean(name = "dataSource")
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

    @Profile("prod")
    @Bean(name = "dataSource")
    public BasicDataSource dataSource(){
        Properties dbConfig = getDbConfig();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("dbc:postgresql://ec2-54-247-189-141.eu-west-1.compute.amazonaws.com:5432/d6h7pc1fg6ssvb?sslmode=require&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
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
