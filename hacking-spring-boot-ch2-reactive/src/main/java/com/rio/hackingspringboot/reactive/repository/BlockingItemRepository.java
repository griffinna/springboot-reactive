package com.rio.hackingspringboot.reactive.repository;

import com.rio.hackingspringboot.reactive.entity.Item;
import org.springframework.data.repository.CrudRepository;

/*
* Blocking Interface
* Mono 나 Flux 를 반환하지 않는 메소드를 포함하는 인터페이스
* 리액터의 논블로킹, 비동기 실행 환경과는 전혀 연계되지 않음
* 결과를 받을 때까지 기다렸다가 응답을 반환하는 전통적인 블로킹 API
* */
public interface BlockingItemRepository extends CrudRepository<Item, String> {

}
