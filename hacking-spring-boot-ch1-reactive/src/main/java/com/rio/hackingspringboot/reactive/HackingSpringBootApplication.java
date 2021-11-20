package com.rio.hackingspringboot.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
* @SpringBootApplication
* auto configuration + component scan
* */
@SpringBootApplication
public class HackingSpringBootApplication {

	public static void main(String[] args) {	// run application
		// spring boot hook (이 클래스를 application 시작점으로 등록)
		SpringApplication.run(HackingSpringBootApplication.class, args);
	}

}
