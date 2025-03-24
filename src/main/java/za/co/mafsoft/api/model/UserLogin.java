package za.co.mafsoft.api.model;

import lombok.Data;

@Data
public class UserLogin {
    private String emailOrMsisdn;
    private String pin;
}
