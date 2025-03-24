package za.co.mafsoft.api.service.interfaces;

import za.co.mafsoft.api.model.Cart;

public interface Payable<T> {
    T pay(Cart cart);
}
