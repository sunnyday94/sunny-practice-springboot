package com.vphoto.practice.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class HikariConfigBean extends HikariConfig {

    @Bean
    @ConditionalOnClass(HikariDataSource.class)
    @ConditionalOnProperty(name = "spring.datasource", havingValue = "com.zaxxer.hikari.HikariDataSource", matchIfMissing = true)
    public HikariDataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource(this);
        return dataSource;
    }

}
