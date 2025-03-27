package za.co.mafsoft.api.model.request;

import lombok.Data;

@Data
public class UserVerify {
    private String emailOrMsisdn;
    private String verificationCode;
}
