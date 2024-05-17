package com.example.JavaTaskProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	// for decoding the json
	// https://jwt.io/
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
