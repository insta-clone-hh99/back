package com.sparta.instaclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class backApplication {

    public static void main(String[] args) {
        SpringApplication.run(backApplication.class, args);
    }

}