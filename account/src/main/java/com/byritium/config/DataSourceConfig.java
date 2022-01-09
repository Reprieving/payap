package com.byritium.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("88888888");
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/payap?characterEncoding=utf8&useSSL=false");
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setAutoCommit(true);
        hikariConfig.setMinimumIdle(1);
        hikariConfig.setIdleTimeout(60000);
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMaxLifetime(1800000);
        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setValidationTimeout(30000);
        return new HikariDataSource(hikariConfig);

    }

}
