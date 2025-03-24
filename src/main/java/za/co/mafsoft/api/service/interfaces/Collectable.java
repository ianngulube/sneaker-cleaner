package za.co.mafsoft.api.service.interfaces;

import za.co.mafsoft.api.model.Item;

public interface Collectable<T> {
    T collect(Item item);
}
