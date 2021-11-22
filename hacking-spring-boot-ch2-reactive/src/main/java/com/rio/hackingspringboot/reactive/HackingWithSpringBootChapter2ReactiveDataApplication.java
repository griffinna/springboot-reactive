package com.rio.hackingspringboot.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thymeleaf.TemplateEngine;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class HackingWithSpringBootChapter2ReactiveDataApplication {

	public static void main(String[] args) {
		// 블록하운드 등록
		// SpringApplication.run 보다 선행되어야 바이트코드를 조작 가능
		BlockHound.builder()
				// TemplateEngine.process() 를 블록하운드 허용 리스트에 추가
				.allowBlockingCallsInside(
						TemplateEngine.class.getCanonicalName(), "process")
				.install();

		SpringApplication.run(HackingWithSpringBootChapter2ReactiveDataApplication.class, args);
	}

}
