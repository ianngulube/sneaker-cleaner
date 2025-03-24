package za.co.mafsoft.api.mapper;

import org.mapstruct.Mapper;
import za.co.mafsoft.api.entity.CatalogEntity;
import za.co.mafsoft.api.model.Catalog;

@Mapper(componentModel = "cdi")
public interface CatalogMapper {
    CatalogEntity fromModelToEntity(Catalog catalog);

    Catalog fromEntityToModel(CatalogEntity catalogEntity);
}
