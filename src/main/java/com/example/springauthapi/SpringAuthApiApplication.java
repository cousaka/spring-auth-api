package com.example.springauthapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringAuthApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAuthApiApplication.class, args);
    }

}
