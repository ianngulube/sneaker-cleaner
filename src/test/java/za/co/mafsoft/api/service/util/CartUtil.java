package za.co.mafsoft.api.service.util;

import jakarta.enterprise.context.ApplicationScoped;
import za.co.mafsoft.api.model.Cart;
import za.co.mafsoft.api.model.CartFiller;
import za.co.mafsoft.api.model.Catalog;
import za.co.mafsoft.api.model.User;
import za.co.mafsoft.api.model.enums.ItemTransport;
import za.co.mafsoft.api.model.enums.ItemType;
import za.co.mafsoft.api.service.CartService;

import java.math.BigDecimal;

@ApplicationScoped
public class CartUtil {
    private CartUtil(CartService cartService) {
        this.cartService = cartService;
    }

    CartService cartService;


    public Cart getCart() {
        User user;
        Catalog catalog1;
        Catalog catalog2;
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

        CartFiller cartFiller1 = CartFiller.builder()
                .catalogItem(catalog1).user(user).quantity(4).itemTransport(ItemTransport.DROPPABLE_AND_DELIVERABLE)
                .build();
        CartFiller cartFiller2 = CartFiller.builder()
                .catalogItem(catalog2).user(user).quantity(7).itemTransport(ItemTransport.DROPPABLE_AND_DELIVERABLE)
                .build();
        cartService.addToCart(cartFiller1);
        cartService.addToCart(cartFiller2);
        return cartService.viewCart();
    }
}
