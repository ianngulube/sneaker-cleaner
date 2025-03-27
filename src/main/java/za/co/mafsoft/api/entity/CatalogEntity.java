package za.co.mafsoft.api.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import za.co.mafsoft.api.model.enums.ItemType;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "catalog_table")
@EqualsAndHashCode(callSuper = true)
public class CatalogEntity extends PanacheEntity {
    private String offeringName;
    private String offeringDescription;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private ItemType itemType;
}
