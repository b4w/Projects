package com.photoasgift.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
// данный класс является конфигурацией, которую необходимо выполнить перед тем как необходимо разворачивать spring
@EnableWebMvc // включает режим webMVC
@ComponentScan("com.photoasgift.server") // где мы должны искать все наши бины
public class WebConfig extends WebMvcConfigurerAdapter {

}
