package za.co.mafsoft.api.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import za.co.mafsoft.api.model.Cart;
import za.co.mafsoft.api.model.CartFiller;
import za.co.mafsoft.api.model.Catalog;

@Slf4j
@ApplicationScoped
public class CartService {
    @Inject
    Cart cart;

    public Cart viewCart() {
        return this.cart;
    }

    public void addToCart(final CartFiller cartFiller) {
        cart.setUser(cartFiller.getUser());
        cart.setItemTransport(cartFiller.getItemTransport());
        cart.getCatalogCount().put(cartFiller.getCatalogItem(), cartFiller.getQuantity());
    }

    public void removeFromCart(final Catalog catalogItem) {
        cart.getCatalogCount().remove(catalogItem);
    }
}
