package za.co.mafsoft.api.service.interfaces;

import za.co.mafsoft.api.model.Item;

public interface Deliverable<T> {
    T deliver(Item item);
}
