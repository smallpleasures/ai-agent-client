package com.yrainy.aiagentclient;

import cn.hutool.extra.spring.EnableSpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSpringUtil
public class AiAgentClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiAgentClientApplication.class, args);
    }

}
