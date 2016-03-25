package com.photoasgift.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

@Configuration
@EnableJpaRepositories(value = "com.photoasgift.server.repository") // говорим, что будем использовать JPA
@EnableTransactionManagement
@PropertySource(value = "classpath:db.properties")
@ComponentScan(value = "com.photoasgift.server")
public class DatabaseConfig {

    @Resource
    private Environment env; // будем получать доступ к property файлам

}
