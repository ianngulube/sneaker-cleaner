package za.co.mafsoft.api.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "catalog_audit_table")
@EqualsAndHashCode(callSuper = true)
public class CatalogAuditEntity extends PanacheEntity {
}
