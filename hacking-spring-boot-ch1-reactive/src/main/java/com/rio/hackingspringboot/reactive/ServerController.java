package com.rio.hackingspringboot.reactive;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController // 결과 데이터를 직렬화하고 HTTP 응답을 본문에 직접 써서 반환하는 REST 컨트롤러
public class ServerController {

    private final KitchenService kitchen;

    // Application 이 실행될때 인스턴스를 찾아서 자동으로 생성자 주입
    public ServerController(KitchenService kitchen) {
        this.kitchen = kitchen;
    }

    // GET /server 요청을 라우팅하는 웹 MVC Annotation
    // 반환 미디어타입 : text/event-stream
    // 클라이언트는 서버가 반환하는 스트림을 쉽게 소비(consume)
    @GetMapping(value = "/server", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Dish> serveDishes() {
        return this.kitchen.getDishes();
    }

    @GetMapping(value = "/served-dished", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Dish> deliverDishes() {
        return this.kitchen.getDishes()
                .map(dish -> Dish.deliver(dish));
    }

}
