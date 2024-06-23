package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SistemaConferenciasApplication {

	public static void main(String[] args) {
		try {
			CustomTrustManager.configureTrustManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringApplication.run(SistemaConferenciasApplication.class, args);
	}

}
