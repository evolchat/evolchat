package com.glossy.evolchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.glossy.evolchat.repository")
public class EvolchatApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvolchatApplication.class, args);
	}

}
