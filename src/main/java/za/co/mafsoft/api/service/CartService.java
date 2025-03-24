package za.co.mafsoft.api.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import za.co.mafsoft.api.model.Cart;
import za.co.mafsoft.api.model.Catalog;
import za.co.mafsoft.api.model.User;

@Slf4j
@ApplicationScoped
public class CartService {
    @Inject
    Cart cart;

    public Cart viewCart() {
        return this.cart;
    }

    public void addToCart(final Catalog catalogItem, final User user, final Integer quantity) {
        cart.setUser(user);
        cart.getCatalogCount().put(catalogItem, quantity);
    }

    public void removeFromCart(final Catalog catalogItem) {
        cart.getCatalogCount().remove(catalogItem);
    }
}
