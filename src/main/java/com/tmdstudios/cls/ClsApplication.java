package com.tmdstudios.cls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClsApplication.class, args);
	}

}
