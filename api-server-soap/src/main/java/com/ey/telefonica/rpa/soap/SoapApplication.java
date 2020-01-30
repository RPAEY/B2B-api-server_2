package com.ey.telefonica.rpa.soap;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories(basePackages = "com.ey")
@ComponentScan(basePackages = "com.ey")
@SpringBootApplication
public class SoapApplication {

    private static final Logger logger = LoggerFactory.getLogger(SoapApplication.class);



    public static void main(String[] args) {
        logger.debug("Start application");
        ReflectionToStringBuilder.setDefaultStyle(new RecursiveToStringStyle());
        SpringApplication.run(SoapApplication.class, args);
    }

}
