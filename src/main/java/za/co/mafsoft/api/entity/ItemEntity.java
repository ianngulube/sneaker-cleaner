package za.co.mafsoft.api.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import za.co.mafsoft.api.model.Address;
import za.co.mafsoft.api.model.enums.ItemTransport;
import za.co.mafsoft.api.model.enums.ItemType;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "item_table")
@EqualsAndHashCode(callSuper = true)
public class ItemEntity extends PanacheEntity {
    private Long userId;
    private String itemCode;
    private String itemShortName;
    private String itemDescription;
    private ItemType itemType;
    private Integer itemQuantity;
    private LocalDateTime dateCollected;
    private LocalDateTime dateDroppedOff;
    private LocalDateTime dateDelivered;
    private ItemTransport itemTransport;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Address address;
}
