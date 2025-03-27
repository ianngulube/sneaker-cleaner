package za.co.mafsoft.api.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateResponse {
    private int responseCode;
    private String responseDescription;
}
