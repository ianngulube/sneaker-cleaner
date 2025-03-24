package za.co.mafsoft.api.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import za.co.mafsoft.api.entity.ItemEntity;

@Slf4j
@ApplicationScoped
public class ItemRepository implements PanacheRepository<ItemEntity> {
}
