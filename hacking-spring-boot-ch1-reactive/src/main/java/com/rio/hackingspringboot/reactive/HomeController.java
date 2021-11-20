package com.rio.hackingspringboot.reactive;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

/*
* JSON 이나 XML 같은 데이터가 아니라 템플릿을 사용한 웹 페이지를 반환하는 Spring Web controller
* */
@Controller
public class HomeController {

    @GetMapping                     // default : /
    Mono<String> home() {           // Mono : 리액티브 컨테이너
        return Mono.just("home");   // 템플릿 이름 반환
    }
}
