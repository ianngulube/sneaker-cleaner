package za.co.mafsoft.api.model;

import lombok.Data;
import za.co.mafsoft.api.model.enums.ItemTransport;
import za.co.mafsoft.api.model.enums.ItemType;

import java.time.LocalDateTime;

@Data
public abstract class Item {
    private String itemCode;
    private String itemShortName;
    private String itemDescription;
    private ItemType itemType;
    private Integer itemQuantity;
    private LocalDateTime dateCollected;
    private LocalDateTime dateDroppedOff;
    private LocalDateTime dateDelivered;
    private ItemTransport itemTransport;
}
