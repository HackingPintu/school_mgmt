package com.ssvm.school;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SchoolApplication.class, args);
    }

    @Override
    public void run(String @NonNull ... args) {
        System.out.println("School Application Started");
    }
}
