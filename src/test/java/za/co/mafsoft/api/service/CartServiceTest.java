package za.co.mafsoft.api.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import za.co.mafsoft.api.model.CartFiller;
import za.co.mafsoft.api.model.Catalog;
import za.co.mafsoft.api.model.User;
import za.co.mafsoft.api.model.enums.ItemTransport;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CartServiceTest {

    @Inject
    CartService cartService;

    User user;
    Catalog catalog1 = new Catalog();
    Catalog catalog2 = new Catalog();

    @BeforeEach
    void setUp() {
        user = new User();
        catalog1.setOfferingName("One");
        catalog1.setOfferingDescription("One description");
        catalog1.setPrice(BigDecimal.valueOf(100));
        catalog2.setOfferingName("Two");
        catalog2.setOfferingDescription("Two description");
        catalog2.setPrice(BigDecimal.valueOf(200));
    }

    @Test
    @Order(1)
    void add_to_cart() {
        CartFiller cartFiller = CartFiller.builder()
                .quantity(3).user(user).catalogItem(catalog1)
                .itemTransport(ItemTransport.COLLECTABLE_AND_DELIVERABLE)
                .build();
        cartService.addToCart(cartFiller);
        Map<Catalog, Integer> map = cartService.viewCart().getCatalogItemCount();
        Assertions.assertTrue(map.containsKey(catalog1));
        Assertions.assertFalse(map.containsKey(catalog2));
    }

    @Test
    @Order(2)
    void remove_from_cart() {
        cartService.removeFromCart(catalog1);
        Map<Catalog, Integer> map = cartService.viewCart().getCatalogItemCount();
        Assertions.assertFalse(map.containsKey(catalog1));
        Assertions.assertFalse(map.containsKey(catalog2));
    }
}