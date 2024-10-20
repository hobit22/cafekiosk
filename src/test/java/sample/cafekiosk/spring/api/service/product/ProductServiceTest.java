package sample.cafekiosk.spring.api.service.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 상품의 상품번호에서 1 증가한 값이다.")
    @Test
    void createProduct() {
        // given
        Product product1 = getProduct("001", HANDMADE, SELLING, 4000, "아메리카노");

        productRepository.save(product1);

        ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
                .name("새로운 상품")
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .price(5000)
                .build();

        // when
        ProductResponse response = productService.createProduct(request);

        // then
        assertThat(response).extracting("productNumber", "type", "sellingStatus", "price", "name")
                .containsExactly("002", HANDMADE, SELLING, 5000, "새로운 상품");

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(2)
                .extracting("productNumber", "type", "sellingStatus", "price", "name")
                .containsExactlyInAnyOrder(
                        tuple("001", HANDMADE, SELLING, 4000, "아메리카노"),
                        tuple("002", HANDMADE, SELLING, 5000, "새로운 상품")
                );

    }

    @DisplayName("상품이 하나도 없는 경우 신규 상품을 등록하면 상품번호는 001이다.")
    @Test
    void createProductWhenProductIsEmpty() {
        // given
        ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
                .name("새로운 상품")
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .price(5000)
                .build();

        // when
        ProductResponse response = productService.createProduct(request);

        // then
        assertThat(response).extracting("productNumber", "type", "sellingStatus", "price", "name")
                .containsExactly("001", HANDMADE, SELLING, 5000, "새로운 상품");

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1)
                .extracting("productNumber", "type", "sellingStatus", "price", "name")
                .containsExactlyInAnyOrder(
                        tuple("001", HANDMADE, SELLING, 5000, "새로운 상품")
                );

    }


    private static Product getProduct(String productNumber, ProductType type, ProductSellingStatus productSellingStatus, int price, String name) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(productSellingStatus)
                .price(price)
                .name(name)
                .build();
    }
}