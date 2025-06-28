package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.example.service.Impl.StudentServeiceImpl;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)

public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner(StudentServeiceImpl studentServeice) {
        return args -> studentServeice.createDefaultStudent();
    }
}
