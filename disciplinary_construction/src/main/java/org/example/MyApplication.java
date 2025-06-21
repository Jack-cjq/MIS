package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Hello world!
 *
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)

public class MyApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(MyApplication.class, args);
    }
}
