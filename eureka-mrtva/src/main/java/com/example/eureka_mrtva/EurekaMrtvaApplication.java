package com.example.eureka_mrtva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaMrtvaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaMrtvaApplication.class, args);
	}

}
