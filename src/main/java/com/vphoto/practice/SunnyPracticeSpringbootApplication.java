package com.vphoto.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {"com.vphoto.practice"})
@EnableAspectJAutoProxy

public class SunnyPracticeSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SunnyPracticeSpringbootApplication.class, args);
    }
}
