package com.timePlanner.controller.config;


import com.timePlanner.dao.config.DaoConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DaoConfig.class)
public class RootConfig {
}
