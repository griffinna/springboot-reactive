package com.rio.hackingspringboot.reactive;

import com.rio.hackingspringboot.reactive.entity.Cart;
import com.rio.hackingspringboot.reactive.repository.CartRepository;
import com.rio.hackingspringboot.reactive.repository.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

}
