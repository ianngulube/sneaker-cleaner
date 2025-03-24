package za.co.mafsoft.api.service.interfaces;

import za.co.mafsoft.api.model.Item;

public interface Droppable<T> {
    T drop(Item item);
}
