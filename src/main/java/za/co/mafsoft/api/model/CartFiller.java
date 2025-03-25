package za.co.mafsoft.api.model;

import lombok.Builder;
import lombok.Data;
import za.co.mafsoft.api.model.enums.ItemTransport;

@Data
@Builder
public class CartFiller {
    private Catalog catalogItem;
    private User user;
    private Integer quantity;
    private ItemTransport itemTransport;
}
