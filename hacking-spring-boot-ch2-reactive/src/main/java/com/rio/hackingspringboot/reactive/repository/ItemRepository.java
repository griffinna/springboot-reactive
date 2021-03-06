package com.rio.hackingspringboot.reactive.repository;

import com.rio.hackingspringboot.reactive.entity.Item;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/*
* ReactiveCrudRepository<A, B>
* - A : Repository 가 저장하고 조회하는 Type
* - B : 저장되는 데이터 식별자 Type
*
* - ItemRepository : 저장할 데이터 타입과 식별자 타입을 강제하는 역할
* - ReactiveCrudRepository 상속 Method
*   . save(), saveAll()
*   . findById(), findAll(), findAllById()
*   . existsById()
*   . count()
*   . deleteById(), delete(), deleteAll()
* - All Method Return Type are Mono and Flux.
*   Some Method can receive Publisher type to parameter.
* */
public interface ItemRepository extends ReactiveCrudRepository<Item, String>, ReactiveQueryByExampleExecutor {
    Flux<Item> findByNameContaining(String partialName);

//	@Query("{ 'name' : ?0, 'age' : ?1 }")
//	Flux<Item> findItemsForCustomerMonthlyReport(String name, int age);
//
//	@Query(sort = "{ 'age' : -1 }")
//	Flux<Item> findSortedStuffForWeeklyReport();
    // end::code-2[]

    // search by name
    Flux<Item> findByNameContainingIgnoreCase(String partialName);

    // search by description
    Flux<Item> findByDescriptionContainingIgnoreCase(String partialName);

    // search by name AND description
    Flux<Item> findByNameContainingAndDescriptionContainingAllIgnoreCase(String partialName, String partialDesc);

    // search by name OR description
    Flux<Item> findByNameContainingOrDescriptionContainingAllIgnoreCase(String partialName, String partialDesc);

}
