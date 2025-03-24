package za.co.mafsoft.api.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import za.co.mafsoft.api.model.Cart;
import za.co.mafsoft.api.model.Catalog;
import za.co.mafsoft.api.model.User;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CheckoutServiceTest {

    @Inject
    CheckoutService checkoutService;

    @Inject
    CartService cartService;

    @Inject
    CatalogService catalogService;

    User user;
    Catalog catalog1;
    Catalog catalog2;

    @BeforeEach
    void setUp() {

        catalog1 = new Catalog();
        catalog2 = new Catalog();

        user = new User();
        catalog1.setOfferingName("One");
        catalog1.setOfferingDescription("One description");
        catalog1.setPrice(BigDecimal.valueOf(100));
        catalog2.setOfferingName("Two");
        catalog2.setOfferingDescription("Two description");
        catalog2.setPrice(BigDecimal.valueOf(200));
    }

    @Test
    void checkout() {
        cartService.addToCart(catalog1, user, 4);
        cartService.addToCart(catalog2, user, 7);
        Cart cart = cartService.viewCart();
        checkoutService.checkout(cart);
        Assertions.assertEquals(cart.getCalculatedTotalPrice(),BigDecimal.valueOf(1800));
    }
}