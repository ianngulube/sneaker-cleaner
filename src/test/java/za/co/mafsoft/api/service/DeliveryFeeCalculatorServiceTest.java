package za.co.mafsoft.api.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import za.co.mafsoft.api.model.Cart;
import za.co.mafsoft.api.service.util.CartUtil;

import java.math.BigDecimal;
import java.util.zip.DataFormatException;

@Slf4j
@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeliveryFeeCalculatorServiceTest {

    @Inject
    DeliveryFeeCalculatorService deliveryFeeCalculatorService;
    @Inject
    CheckoutService checkoutService;
    @Inject
    CartUtil cartUtil;

    Cart cart;

    @BeforeEach
    void setUp() throws DataFormatException {
        cart = cartUtil.getCart();
        checkoutService.checkout(cart);
    }

    @Test
    void calculate_delivery_fee() throws DataFormatException {
        deliveryFeeCalculatorService.calculateDeliveryFee(cart);
        Assertions.assertEquals(BigDecimal.valueOf(20), cart.getDeliveryFee());
    }
}