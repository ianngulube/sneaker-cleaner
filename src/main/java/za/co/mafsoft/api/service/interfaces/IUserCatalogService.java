package za.co.mafsoft.api.service.interfaces;

import za.co.mafsoft.api.model.Catalog;

import java.util.List;

public interface IUserCatalogService {
    Catalog getCatalog(final Long catalogId);

    List<Catalog> getAll();
}
