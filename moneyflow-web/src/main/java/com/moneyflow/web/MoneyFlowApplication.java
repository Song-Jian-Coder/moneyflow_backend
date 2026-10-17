package com.moneyflow.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.moneyflow.dao.repository")
@EntityScan(basePackages = "com.moneyflow.model.entity")
@ComponentScan(basePackages = "com.moneyflow")
public class MoneyFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyFlowApplication.class, args);
    }

}
