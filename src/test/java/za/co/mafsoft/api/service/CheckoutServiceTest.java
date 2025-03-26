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
import za.co.mafsoft.api.model.CartFiller;
import za.co.mafsoft.api.model.Catalog;
import za.co.mafsoft.api.model.User;
import za.co.mafsoft.api.model.enums.ItemTransport;
import za.co.mafsoft.api.model.enums.ItemType;

import java.math.BigDecimal;
import java.util.zip.DataFormatException;

@Slf4j
@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CheckoutServiceTest {

    @Inject
    CheckoutService checkoutService;

    @Inject
    CartService cartService;

    User user;
    Catalog catalog1;
    Catalog catalog2;

    @BeforeEach
    void setUp() {

        catalog1 = new Catalog();
        catalog2 = new Catalog();

        user = new User();
        user.setVerificationCode("Qasxzs");
        user.setPin("1807");
        user.setMsisdn("27619250520");
        user.setEmail("ngulubeian94@gmail.com");
        user.setFirstName("Ian");
        user.setLastName("Ngulube");
        user.setVerified(true);

        catalog1.setOfferingName("One");
        catalog1.setOfferingDescription("One description");
        catalog1.setPrice(BigDecimal.valueOf(100));
        catalog1.setItemType(ItemType.SNEAKER);
        catalog2.setOfferingName("Two");
        catalog2.setOfferingDescription("Two description");
        catalog2.setItemType(ItemType.SNEAKER);
        catalog2.setPrice(BigDecimal.valueOf(200));
    }

    @Test
    void checkout() throws DataFormatException {
        CartFiller cartFiller1 = CartFiller.builder()
                .catalogItem(catalog1).user(user).quantity(4).itemTransport(ItemTransport.DROPPABLE_AND_DELIVERABLE)
                .build();
        CartFiller cartFiller2 = CartFiller.builder()
                .catalogItem(catalog2).user(user).quantity(7).itemTransport(ItemTransport.DROPPABLE_AND_DELIVERABLE)
                .build();
        cartService.addToCart(cartFiller1);
        cartService.addToCart(cartFiller2);
        Cart cart = cartService.viewCart();
        checkoutService.checkout(cart);
        Assertions.assertEquals(cart.getCalculatedTotalPrice(), BigDecimal.valueOf(1800));
        Assertions.assertEquals(BigDecimal.valueOf(20), cart.getDeliveryFee());
    }
}