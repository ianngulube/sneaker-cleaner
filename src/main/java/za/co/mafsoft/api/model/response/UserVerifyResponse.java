package za.co.mafsoft.api.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserVerifyResponse {
    public Integer responseCode;
    private String responseDescription;
}
