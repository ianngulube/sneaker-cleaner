package za.co.mafsoft.api.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import za.co.mafsoft.api.entity.CatalogEntity;
import za.co.mafsoft.api.mapper.CatalogMapper;
import za.co.mafsoft.api.model.Catalog;
import za.co.mafsoft.api.repository.CatalogRepository;
import za.co.mafsoft.api.service.interfaces.IAdminCatalogService;

@Slf4j
@ApplicationScoped
public class AdminCatalogService implements IAdminCatalogService {

    @Inject
    CatalogRepository catalogRepository;
    @Inject
    CatalogMapper catalogMapper;

    @Override
    @Transactional
    public void createCatalog(Catalog catalog) {
        CatalogEntity catalogEntity = catalogMapper.fromModelToEntity(catalog);
        catalogRepository.persist(catalogEntity);
    }

    @Override
    @Transactional
    public void updateCatalog(Catalog catalog) {
        CatalogEntity catalogEntity = catalogRepository.findById(catalog.getId());
        catalogEntity.setPrice(catalog.getPrice());
        catalogEntity.setOfferingName(catalog.getOfferingName());
        catalogEntity.setOfferingDescription(catalog.getOfferingDescription());
        catalogRepository.persist(catalogEntity);
    }

    @Override
    public void deleteCatalog(Long catalogId) {
        catalogRepository.deleteById(catalogId);
    }
}

