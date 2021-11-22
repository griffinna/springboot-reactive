package com.rio.hackingspringboot.reactive.service;

import com.rio.hackingspringboot.reactive.entity.Cart;
import com.rio.hackingspringboot.reactive.entity.CartItem;
import com.rio.hackingspringboot.reactive.entity.Item;
import com.rio.hackingspringboot.reactive.repository.CartRepository;
import com.rio.hackingspringboot.reactive.repository.ItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service    // 자체상태를 가지고 있지 않은 서비스임을 의미
public class CartService {
    private ItemRepository itemRepository;
    private CartRepository cartRepository;

    // 생성자주입 (constructor injection)
    public CartService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    public Flux<Item> findItemAll() {
        return this.itemRepository.findAll().doOnNext(System.out::println);
    }

    public Mono<Cart> findCartById(String cartId) {
        return this.cartRepository.findById(cartId)
                .defaultIfEmpty(new Cart(cartId));
    }

    public Mono<Cart> addToCart(String cartId, String id) {
        return this.cartRepository.findById(cartId)
                .log("foundCart")
                .defaultIfEmpty(new Cart(cartId))
                .log("emptyCart")
                .flatMap(cart -> cart.getCartItems().stream()
                        .filter(cartItem -> cartItem.getItem()
                                .getId().equals(id))
                        .findAny()
                        .map(cartItem -> {
                            cartItem.increment();
                            return Mono.just(cart).log("newCartItem");
                        })
                        .orElseGet(() ->
                            this.itemRepository.findById(id)
                                    .log("fetchedItem")
                                    .map(CartItem::new)
                                    .log("cartItem")
                                    .doOnNext(cartItem ->
                                            cart.getCartItems().add(cartItem))
                                    .log("addedCartItem")
                                    .map(cartItem -> cart)))
                .log("cartWithAnotherItem")
                .flatMap(this.cartRepository::save)
                .log("savedCart");
    }
}
