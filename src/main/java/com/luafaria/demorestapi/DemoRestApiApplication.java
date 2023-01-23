package com.luafaria.demorestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class DemoRestApiApplication {

    // If you have a mac m1, uncomment this line and the integration will pass
    // Do not use this on prod
    //static {System.setProperty("os.arch", "i686_64");}

    public static void main(String[] args) {
        SpringApplication.run(DemoRestApiApplication.class, args);
    }

}
