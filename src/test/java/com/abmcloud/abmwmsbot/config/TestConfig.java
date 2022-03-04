package com.abmcloud.abmwmsbot.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;

//@Configuration
@PropertySource(value = "classpath:test.properties")
@EnableTransactionManagement
public class TestConfig {

    @Autowired
    private Environment environment;

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

//    @Bean
//    public EntityManager getEntityManager(){
//
//    }


}
