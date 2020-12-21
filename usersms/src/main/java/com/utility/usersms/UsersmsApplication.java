package com.utility.usersms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class UsersmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersmsApplication.class, args);
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoderProducer(){
		return new BCryptPasswordEncoder();
	}


}
