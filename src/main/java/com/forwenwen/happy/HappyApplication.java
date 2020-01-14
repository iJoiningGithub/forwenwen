package com.forwenwen.happy;

import com.forwenwen.happy.domain.File;
import com.forwenwen.happy.repository.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HappyApplication {
    private static final Logger log = LoggerFactory.getLogger(HappyApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(HappyApplication.class, args);
    }
    @Bean
    public CommandLineRunner demo(FileRepository repository) {
        return (args) -> {
            // save a few customers

        };
    }

}
