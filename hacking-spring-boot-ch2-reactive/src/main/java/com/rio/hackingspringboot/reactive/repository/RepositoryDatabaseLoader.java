package com.rio.hackingspringboot.reactive.repository;

import com.rio.hackingspringboot.reactive.entity.Item;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component  // 클래스가 Bean 으로 등록되게 함
public class RepositoryDatabaseLoader { // Blocking API 를 사용해서 데이터를 로딩하는 컴포넌트

//    @Bean   // Method 가 반환하는 객체를 Bean 으로 등록
    CommandLineRunner initialize(BlockingItemRepository repository) {
        // CommandLineRunner : application 시작 후 자동 실행되는 spring boot component (only have run method)
        return args -> {
            repository.save(new Item("Alf alarm clock", "",19.99));
            repository.save(new Item("Smurf TV tray", "",24.99));
        };
    }
}
