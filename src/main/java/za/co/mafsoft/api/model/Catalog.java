package za.co.mafsoft.api.model;

import lombok.Data;
import lombok.ToString;
import za.co.mafsoft.api.model.enums.ItemType;

import java.math.BigDecimal;

@Data
@ToString
public class Catalog {
    private Long id;
    private String offeringName;
    private String offeringDescription;
    private BigDecimal price;
    private ItemType itemType;
}
