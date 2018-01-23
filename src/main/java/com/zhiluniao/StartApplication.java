package com.zhiluniao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 *
 * @author Administrator<br>
 *         2018年1月20日  上午11:43:20
 */
@SpringBootApplication
@EnableAutoConfiguration
@MapperScan("com.zhiluniao.model.po")
@ComponentScan//(basePackages = {"com.zhiluniao.controller"})
@EnableTransactionManagement
public class StartApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(StartApplication.class, args);
    }
}
