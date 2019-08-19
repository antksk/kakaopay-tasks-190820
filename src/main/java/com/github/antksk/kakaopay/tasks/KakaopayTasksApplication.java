package com.github.antksk.kakaopay.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@EnableConfigurationProperties
@SpringBootApplication
public class KakaopayTasksApplication {
    public static void main(String[] args) {
        SpringApplication.run(KakaopayTasksApplication.class, args);
    }
}
