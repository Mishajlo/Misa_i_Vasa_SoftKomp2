package com.example.api_mrtvi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiMrtviApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiMrtviApplication.class, args);
	}

}
