package za.co.mafsoft.api.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import za.co.mafsoft.api.model.Cart;
import za.co.mafsoft.api.model.Catalog;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.zip.DataFormatException;

@Slf4j
@ApplicationScoped
public class CheckoutService {

    @Inject
    DeliveryFeeCalculatorService deliveryFeeCalculatorService;

    public void checkout(final Cart cart) throws DataFormatException {
        if (!validateCart(cart)) {
            throw new DataFormatException("Cart is missing important data");
        }
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (Map.Entry<Catalog, Integer> entry : cart.getCatalogItemCount().entrySet()) {
            Catalog catalog = entry.getKey();
            Integer integer = entry.getValue();
            totalPrice = totalPrice.add(catalog.getPrice().multiply(BigDecimal.valueOf(integer)));
        }
        cart.setCalculatedTotalPrice(totalPrice);
        deliveryFeeCalculatorService.calculateDeliveryFee(cart);
    }

    private boolean validateCart(final Cart cart) {
        if (Objects.isNull(cart)) {
            return false;
        }
        return !Objects.isNull(cart.getCatalogItemCount()) &&
                !Objects.isNull(cart.getUser()) &&
                !Objects.isNull(cart.getItemTransport());
    }
}
