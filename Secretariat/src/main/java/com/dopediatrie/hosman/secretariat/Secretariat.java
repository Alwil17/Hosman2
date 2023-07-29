package com.dopediatrie.hosman.secretariat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class Secretariat extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Secretariat.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Secretariat.class);
    }

    /*@Override
    public void run(String... args) throws Exception {
        //SpringApplication.run(Secretariat.class, args);
        System.out.println("Application Started !!");
        Thread.currentThread().join();
    }*/
}
