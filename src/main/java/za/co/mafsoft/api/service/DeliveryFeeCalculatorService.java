package za.co.mafsoft.api.service;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import za.co.mafsoft.api.model.Cart;
import za.co.mafsoft.api.model.enums.ItemTransport;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.zip.DataFormatException;

@Slf4j
@ApplicationScoped
public class DeliveryFeeCalculatorService {
    public void calculateDeliveryFee(final Cart cart) throws DataFormatException {
        if (!validateCart(cart)) {
            throw new DataFormatException("Cart is missing important data");
        }
        BigDecimal deliveryFee = BigDecimal.valueOf(0);
        ItemTransport cartItemTransport = cart.getItemTransport();
        deliveryFee = switch (cartItemTransport) {
            case COLLECTABLE, DROPPABLE_AND_DELIVERABLE -> deliveryFee.add(BigDecimal.valueOf(10));
            case COLLECTABLE_AND_DELIVERABLE -> deliveryFee.add(BigDecimal.valueOf(20));
            default -> deliveryFee.add(BigDecimal.valueOf(0));
        };
        deliveryFee = deliveryFee.multiply(BigDecimal.valueOf(cart.getCatalogItemCount().size()));
        cart.setDeliveryFee(deliveryFee);
    }

    private boolean validateCart(final Cart cart) {
        if (Objects.isNull(cart)) {
            return false;
        }
        return !Objects.isNull(cart.getCalculatedTotalPrice());
    }
}
