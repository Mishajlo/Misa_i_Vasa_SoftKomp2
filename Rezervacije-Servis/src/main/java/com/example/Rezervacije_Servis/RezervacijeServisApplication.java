package com.example.Rezervacije_Servis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RezervacijeServisApplication {

	public static void main(String[] args) {
		SpringApplication.run(RezervacijeServisApplication.class, args);
	}

}
