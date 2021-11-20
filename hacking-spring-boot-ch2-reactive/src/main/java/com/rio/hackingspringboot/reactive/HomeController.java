package com.rio.hackingspringboot.reactive;

import com.rio.hackingspringboot.reactive.entity.Cart;
import com.rio.hackingspringboot.reactive.entity.CartItem;
import com.rio.hackingspringboot.reactive.repository.CartRepository;
import com.rio.hackingspringboot.reactive.repository.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {

    private ItemRepository itemRepository;
    private CartRepository cartRepository;

    // 생성자주입 (constructor injection)
    public HomeController(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    @GetMapping
    Mono<Rendering> home() {             // Mono<Rendering> : view, attribute 포함 웹플럭스 컨테이너
        return Mono.just(Rendering.view("home.html")    // .view(랜더링에 사용할 템플릿 이름)
                .modelAttribute("items",                    // .modelAttribute() : 템플릿에서 사용될 데이터 지정
                        this.itemRepository.findAll())
                .modelAttribute("cart",
                        this.cartRepository.findById("My Cart")
                                .defaultIfEmpty(new Cart("My Cart")))
                .build());
    }

    @PostMapping("/add/{id}")
    Mono<String> addToCart(@PathVariable String id) {
        return this.cartRepository.findById("My Cart")
                .defaultIfEmpty(new Cart("My Cart"))
                .flatMap(cart -> cart.getCartItems().stream() // .flatMap() : '이것'의 스트림을 다른 크기로 된 '저것'의 스트림으로 바꾸는 함수형 도구
                    .filter(cartItem -> cartItem.getItem()
                        .getId().equals(id))
                    .findAny()
                    .map(cartItem -> {          // .map() : '이것'을 '저것'으로 바꾸는 함수형 도구
                        cartItem.increment();
                        return Mono.just(cart);
                    })
                .orElseGet(() -> {
                    return this.itemRepository.findById(id)
                            .map(item -> new CartItem(item))
                            .map(cartItem -> {
                                cart.getCartItems().add(cartItem);
                                return cart;
                            });
                }))
                .flatMap(cart -> this.cartRepository.save(cart))
                .thenReturn("redirect:/");      // webflux 가 HTTP 요청을 / 로 리다이렉트함
    }

}
