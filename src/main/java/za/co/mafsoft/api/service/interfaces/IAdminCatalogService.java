package za.co.mafsoft.api.service.interfaces;

import za.co.mafsoft.api.model.Catalog;

public interface IAdminCatalogService {
    void createCatalog(final Catalog catalog);

    void updateCatalog(final Catalog catalog);

    void deleteCatalog(final Long catalogId);
}
