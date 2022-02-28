package com.hanghae.naegahama;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NaegahamaApplication {

    public static void main(String[] args) {
        SpringApplication.run(NaegahamaApplication.class, args);
    }

}
