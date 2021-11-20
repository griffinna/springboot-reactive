package com.rio.hackingspringboot.reactive.repository;

import com.rio.hackingspringboot.reactive.entity.Cart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CartRepository extends ReactiveCrudRepository<Cart, String> {

}
