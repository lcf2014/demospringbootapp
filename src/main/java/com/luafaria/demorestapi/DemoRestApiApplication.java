package com.luafaria.demorestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class DemoRestApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoRestApiApplication.class, args);
	}

}
