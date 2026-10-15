package com.moneyflow.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.moneyflow.dao.mapper")
@ComponentScan(basePackages = "com.moneyflow")
public class MoneyFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyFlowApplication.class, args);
    }

}
