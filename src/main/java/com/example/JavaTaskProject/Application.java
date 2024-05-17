package com.example.JavaTaskProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	// for decoding the token in this site
	// https://jwt.io/
	public static void main(String[] args) {
		/** in src/main/java/com/example/JavaTaskProject/config/JwtService.java
		 * i comment todo
		 * so in that part i declare the duration 30 min
		 */

		/** in src/main/java/com/example/JavaTaskProject/Auth/AuthenticationController.java register()
		 *user can get token when register new record
		* */

		SpringApplication.run(Application.class, args);
	}

}
