package sample.cafekiosk.spring.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.domain.orderProduct.OrderProductRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static sample.cafekiosk.spring.domain.order.OrderStatus.INIT;
import static sample.cafekiosk.spring.domain.order.OrderStatus.PAYMENT_COMPLETED;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@ActiveProfiles("test")
@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    @DisplayName("특정 일자에 주문한 리스트를 조회한다.")
    @Test
    void findOrdersBy() {
        // given
        Product product1 = createProduct("001", HANDMADE, SELLING, 4000, "아메리카노");

        LocalDateTime registeredDateTime = LocalDateTime.of(2024, 10, 22, 14, 0);
        Order order1 = getOrder(List.of(product1), registeredDateTime, PAYMENT_COMPLETED);
        Order order2 = getOrder(List.of(product1), registeredDateTime, INIT);
        orderRepository.saveAll(List.of(order1,order2));

        LocalDateTime startDate = LocalDateTime.of(2024, 10, 22, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 10, 23, 0, 0);

        // when

        List<Order> orders = orderRepository.findOrdersBy(startDate, endDate, PAYMENT_COMPLETED);

        // then
        assertThat(orders).hasSize(1);
    }


    private Product createProduct(String productNumber, ProductType type, ProductSellingStatus productSellingStatus, int price, String name) {
        Product product = Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(productSellingStatus)
                .price(price)
                .name(name)
                .build();

        return productRepository.save(product);
    }

    private static Order getOrder(List<Product> products, LocalDateTime dateTime, OrderStatus orderStatus) {
        return Order.builder()
                .products(products)
                .registeredDateTime(dateTime)
                .orderStatus(orderStatus)
                .build();
    }
}