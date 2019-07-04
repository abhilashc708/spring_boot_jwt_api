package com.testapi.springbootrespapijwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
public class JwtApiApp {
	
	public static void main(String[] args) {
		SpringApplication.run(JwtApiApp.class, args);
		
	}

}
