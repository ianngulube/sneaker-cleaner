package za.co.mafsoft.api.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String msisdn;
    private String pin;
    private boolean verified;
    private String verificationCode;
}
