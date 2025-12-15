package com.example.do_phu_song4g.demo.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.do_phu_song4g.demo.infrastructure.repository")
@EnableTransactionManagement
public class DatabaseConfig {
    // Additional database configuration if needed
}

