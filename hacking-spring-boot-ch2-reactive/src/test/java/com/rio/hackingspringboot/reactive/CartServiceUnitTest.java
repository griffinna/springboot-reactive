package com.rio.hackingspringboot.reactive;

import com.rio.hackingspringboot.reactive.entity.Cart;
import com.rio.hackingspringboot.reactive.entity.CartItem;
import com.rio.hackingspringboot.reactive.entity.Item;
import com.rio.hackingspringboot.reactive.repository.CartRepository;
import com.rio.hackingspringboot.reactive.repository.ItemRepository;
import com.rio.hackingspringboot.reactive.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

// @ExtendWith : 테스트핸들러를 지정할 수 있는 Junit5 API
// SpringExtension.class : 스프링에 특화된 테스트 기능 사용가능
@ExtendWith(SpringExtension.class)
public class CartServiceUnitTest {
    
    // CUT (Class Under Test) : 테스트 대상이 되는 클래스
    // Mock : 메소드 호출 여부, 순서, 횟수 등 행위 검증 (behavior verification) 을 위한 가짜 객체
    // Stub : 값 기반의 상태 검증 (state verification) 을 위해 미리 정해진 값을 반환하도록 하는 가짜 객체
    // https://martinfowler.com/articles/mocksArentStubs.html

    CartService cartService;  // CUT

    @MockBean       // Mockito 를 사용해서 가짜 객체를 만들고 application context bean 에 추가
    private ItemRepository itemRepository;

    /*
    * @MockBean 은 아래 로직과 동일
    *
    *   @BeforeEach
        void setUp() {
            itemRepository = mock(ItemRepository.class);
            cartRepository = mock(CartRepository.class);
        }
    * */
    @MockBean
    private CartRepository cartRepository;

    @BeforeEach
    void setUp() {
        // 테스트 데이터 정의
        Item sampleItem = new Item("item1", "TV tray", "Alf TV tray", 19.99);
        CartItem sampleCartItem = new CartItem(sampleItem);
        Cart sampleCart = new Cart("My Cart", Collections.singletonList(sampleCartItem));

        // 협력자와의 상호작용 정의
        when(cartRepository.findById(anyString())).thenReturn(Mono.empty());
        when(itemRepository.findById(anyString())).thenReturn(Mono.just(sampleItem));
        when(cartRepository.save(any(Cart.class))).thenReturn(Mono.just(sampleCart));

        // Mock 객체를 생성자 주입하여 CUT 생성
        cartService = new CartService(itemRepository, cartRepository);
    }

    @Test
    void addItemToEmptyCartShouldProduceOneCartItem() {
        cartService.addToCart("MyCart", "item1")
                .as(StepVerifier::create)
                .expectNextMatches(cart -> {
                    assertThat(cart.getCartItems()).extracting(CartItem::getQuantity)
                            .containsExactlyInAnyOrder(1);

                    assertThat(cart.getCartItems()).extracting(CartItem::getItem)
                            .containsExactly(new Item("item1", "TV tray", "Alf TV tray", 19.99));

                    return true;
                })
                .verifyComplete();

    }
}
