package com.timePlanner.controller.config;


import com.timePlanner.dao.config.DaoConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DaoConfig.class)
@ComponentScan(basePackages = "com.timePlanner.service")
public class RootConfig {
}
