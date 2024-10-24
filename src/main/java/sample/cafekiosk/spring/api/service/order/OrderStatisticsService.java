package sample.cafekiosk.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.domain.order.OrderRepository;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class OrderStatisticsService {

    private final OrderRepository orderRepository;

    public void sendOrderStatisticsMail(LocalDate localDate, String email) {
        // 해당 일자의 결제완료된 주문들을 가져와서

        // 총 매출 합계를 계산하고

        // 메일 전송

    }
}
