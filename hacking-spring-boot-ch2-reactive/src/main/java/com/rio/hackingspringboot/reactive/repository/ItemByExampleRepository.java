package com.rio.hackingspringboot.reactive.repository;

import com.rio.hackingspringboot.reactive.entity.Item;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.stereotype.Repository;

public interface ItemByExampleRepository extends ReactiveQueryByExampleExecutor<Item> {

}
