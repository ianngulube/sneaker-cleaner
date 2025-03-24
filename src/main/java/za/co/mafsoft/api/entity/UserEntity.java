package za.co.mafsoft.api.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "user_table")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends PanacheEntity {
    private String firstName;
    public String lastName;
    public String email;
    public String msisdn;
    private String pin;
    private boolean verified;
    private String verificationCode;
}
