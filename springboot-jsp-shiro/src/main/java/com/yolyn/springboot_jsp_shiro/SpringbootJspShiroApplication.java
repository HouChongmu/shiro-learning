package com.yolyn.springboot_jsp_shiro;

import com.yolyn.springboot_jsp_shiro.config.ShiroConfig;
import com.yolyn.springboot_jsp_shiro.controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = {ShiroConfig.class, UserController.class})
@SpringBootApplication
public class SpringbootJspShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJspShiroApplication.class, args);
    }

}
