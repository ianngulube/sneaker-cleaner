package za.co.mafsoft.api.model;

import lombok.Data;
import za.co.mafsoft.api.model.enums.ItemTransport;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Cart {
    private User user;
    private Map<Catalog, Integer> catalogCount = new ConcurrentHashMap<>();
    private BigDecimal calculatedTotalPrice;
    private ItemTransport itemTransport;
}
