package com.rio.hackingspringboot.reactive;

import com.rio.hackingspringboot.reactive.entity.Cart;
import com.rio.hackingspringboot.reactive.service.CartService;
import com.rio.hackingspringboot.reactive.service.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {

    private CartService cartService;
    private InventoryService inventoryService;

    public HomeController(CartService cartService, InventoryService inventoryService) {
        this.cartService = cartService;
        this.inventoryService = inventoryService;
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

    @GetMapping("/search")
    Mono<Rendering> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam boolean useAnd
    ) {
        return Mono.just(Rendering.view("home.html")
                .modelAttribute("items",
                        inventoryService.searchByExample(name, description, useAnd))
                .modelAttribute("cart",
                        cartService.findCartById("My Cart"))
            .build());      // lazy 방식이라 구독을 해야 실제 검색이 수행
    }

}
