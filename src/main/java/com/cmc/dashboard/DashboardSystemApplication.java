package com.cmc.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableAutoConfiguration
@ComponentScan
public class DashboardSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashboardSystemApplication.class, args);
	}

}
