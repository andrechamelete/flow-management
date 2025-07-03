package com.chamelete.flowManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FlowManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowManagementApplication.class, args);
	}

}
