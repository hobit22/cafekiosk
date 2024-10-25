package sample.cafekiosk.spring.api.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.order.OrderStatus;
import sample.cafekiosk.spring.domain.orderProduct.OrderProductRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@SpringBootTest
class OrderStatisticsServiceTest {

    @Autowired
    OrderStatisticsService orderStatisticsService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MailSendHistoryRepository mailSendHistoryRepository;

    @MockBean
    MailSendClient mailSendClient;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @DisplayName("매출 통계 메일을 전송한다.")
    @Test
    void sendOrderStatisticsMail() {
        // given
        LocalDateTime now = LocalDateTime.of(2024, 10, 25, 0, 0);

        Product product1 = getProduct("001", HANDMADE, 1000);
        Product product2 = getProduct("002", HANDMADE, 2000);
        Product product3 = getProduct("003", HANDMADE, 3000);

        List<Product> products = List.of(product1, product2, product3);
        productRepository.saveAll(products);

        Order order1 = createPaymentCompletedOrder(products, LocalDateTime.of(2024, 10, 24, 23,59));
        Order order2 = createPaymentCompletedOrder(products, now);
        Order order3 = createPaymentCompletedOrder(products, LocalDateTime.of(2024, 10, 25, 23,59));
        Order order4 = createPaymentCompletedOrder(products, LocalDateTime.of(2024, 10, 26, 0,0));

        when(mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
                .thenReturn(true);
        // when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2024, 10, 25), "test@test.com");

        // then
        assertThat(result).isTrue();

        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1)
                .extracting("content")
                .contains("총 매출 합계는 12000원입니다.");
    }

    private Order createPaymentCompletedOrder(List<Product> products, LocalDateTime now) {
        Order order = Order.builder()
                .products(products)
                .orderStatus(OrderStatus.PAYMENT_COMPLETED)
                .registeredDateTime(now)
                .build();

        return orderRepository.save(order);
    }


    private static Product getProduct(String productNumber, ProductType type, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(ProductSellingStatus.SELLING)
                .price(price)
                .name("아메리카노")
                .build();
    }

}