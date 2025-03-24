package za.co.mafsoft.api.service;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import za.co.mafsoft.api.model.Cart;
import za.co.mafsoft.api.model.Catalog;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@ApplicationScoped
public class CheckoutService {

    public void checkout(final Cart cart) {
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (Map.Entry<Catalog, Integer> entry : cart.getCatalogCount().entrySet()) {
            Catalog catalog = entry.getKey();
            Integer integer = entry.getValue();
            totalPrice = totalPrice.add(catalog.getPrice().multiply(BigDecimal.valueOf(integer)));
        }
        cart.setCalculatedTotalPrice(totalPrice);
    }
}
