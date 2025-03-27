package za.co.mafsoft.api.model.response;

import lombok.Builder;
import lombok.Data;
import za.co.mafsoft.api.model.User;

@Data
@Builder
public class UserLoginResponse {
    public Integer responseCode;
    private String responseDescription;
    private String authToken;
    private User user;
}
