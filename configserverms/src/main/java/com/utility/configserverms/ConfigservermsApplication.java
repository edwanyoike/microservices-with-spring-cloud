package com.utility.configserverms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigservermsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigservermsApplication.class, args);
	}

}
