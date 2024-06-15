package com.gamechanger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GameChangerAppplication {

    public static void main(String[] args) {
        SpringApplication.run(GameChangerAppplication.class, args);
    }
}
