package com.rio.hackingspringboot.reactive;

import com.rio.hackingspringboot.reactive.entity.Cart;
import com.rio.hackingspringboot.reactive.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {

    private CartService cartService;

    public HomeController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    Mono<Rendering> home() {             // Mono<Rendering> : view, attribute 포함 웹플럭스 컨테이너
        return Mono.just(Rendering.view("home.html")    // .view(랜더링에 사용할 템플릿 이름)
                .modelAttribute("items",                    // .modelAttribute() : 템플릿에서 사용될 데이터 지정
                        cartService.findItemAll())
                .modelAttribute("cart",
                        cartService.findCartById("My Cart"))
                .build());
    }

    @PostMapping("/add/{id}")
    Mono<String> addToCart(@PathVariable String id) {
        return this.cartService.addToCart("My Cart", id)
                .thenReturn("redirect:/");
    }

}
