package com.rio.hackingspringboot.reactive.service;

import com.rio.hackingspringboot.reactive.entity.Item;
import com.rio.hackingspringboot.reactive.repository.ItemRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class InventoryService {

    private ItemRepository repository;

    public InventoryService(ItemRepository repository) {
        this.repository = repository;
    }

    Flux<Item> search(String partialName, String partialDescription, boolean useAnd) {
        if (partialName != null) {
            if (partialDescription != null) {
                if (useAnd) {
                    return repository.findByNameContainingAndDescriptionContainingAllIgnoreCase(partialName, partialDescription);
                } else {
                    return repository.findByNameContainingOrDescriptionContainingAllIgnoreCase(partialName, partialDescription);
                }
            } else {
                return repository.findByNameContaining(partialName);
            }
        } else {
            if (partialDescription != null) {
                return repository.findByDescriptionContainingIgnoreCase(partialDescription);
            } else {
                return repository.findAll();
            }
        }
    }

    public Flux<Item> searchByExample(String name, String description, boolean useAnd) {
        Item item = new Item(name, description, 0.0);   // 새 Item 객체 생성

        ExampleMatcher matcher = (useAnd                        // 3항 연산자로 분기해서 ExampleMatcher 생성
                ? ExampleMatcher.matchingAll()
                : ExampleMatcher.matchingAny())
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // 부분일치 검색 수행
                    .withIgnoreCase()                           // 대소문자 구분하지 않음
                    .withIgnorePaths("price");                  // price 필드 무시 (기본적으로 null 필드는 무시)

        Example<Item> probe = Example.of(item, matcher);        // Item 객체와 matcher 를 Example.of 로 감싸서 Example 생성

        return repository.findAll(probe);                // 쿼리 실행
    }


}
