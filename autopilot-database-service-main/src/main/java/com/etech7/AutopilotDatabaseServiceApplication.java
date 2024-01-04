package com.etech7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
//(exclude = { SecurityAutoConfiguration.class })
public class AutopilotDatabaseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutopilotDatabaseServiceApplication.class, args);
	}

}
