package za.co.mafsoft.api.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import za.co.mafsoft.api.entity.CatalogEntity;
import za.co.mafsoft.api.mapper.CatalogMapper;
import za.co.mafsoft.api.model.Catalog;
import za.co.mafsoft.api.repository.CatalogRepository;
import za.co.mafsoft.api.service.interfaces.IUserCatalogService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
public class CatalogService implements IUserCatalogService {

    @Inject
    CatalogRepository catalogRepository;
    @Inject
    CatalogMapper catalogMapper;

    @Override
    public Catalog getCatalog(Long catalogId) {
        CatalogEntity catalogEntity = catalogRepository.findById(catalogId);
        return catalogMapper.fromEntityToModel(catalogEntity);
    }

    @Override
    public List<Catalog> getAll() {
        return catalogRepository
                .findAll()
                .stream()
                .map(catalogEntity -> catalogMapper.fromEntityToModel(catalogEntity))
                .collect(Collectors.toList());
    }
}
