package za.co.mafsoft.api.model.request;

import lombok.Data;
import za.co.mafsoft.api.model.enums.ItemTransport;

@Data
public class CartFillerRequest {
    private Long catalogId;
    private String emailOrMsisdn;
    private Integer quantity;
    private ItemTransport itemTransport;
}
