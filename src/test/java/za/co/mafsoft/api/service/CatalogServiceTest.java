package za.co.mafsoft.api.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import za.co.mafsoft.api.model.Catalog;
import za.co.mafsoft.api.model.enums.ItemType;
import za.co.mafsoft.api.service.interfaces.IAdminCatalogService;
import za.co.mafsoft.api.service.interfaces.IUserCatalogService;

import java.math.BigDecimal;
import java.util.List;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CatalogServiceTest {

    @Inject
    IAdminCatalogService adminCatalogService;
    @Inject
    IUserCatalogService userCatalogService;

    @BeforeAll
    void setUp() {
        Catalog catalog = new Catalog();
        catalog.setPrice(BigDecimal.valueOf(180));
        catalog.setOfferingName("Lite");
        catalog.setOfferingDescription("Wash outside only");
        catalog.setItemType(ItemType.SNEAKER);
        adminCatalogService.createCatalog(catalog);

        Catalog catalog2 = new Catalog();
        catalog2.setPrice(BigDecimal.valueOf(280));
        catalog2.setOfferingName("Full");
        catalog2.setOfferingDescription("Wash inside and outside");
        catalog.setItemType(ItemType.SNEAKER);
        adminCatalogService.createCatalog(catalog2);
    }

    @AfterAll
    void tearDown() {
    }

    @Test
    @Order(1)
    void get_all_catalogs() {
        List<Catalog> catalogs = userCatalogService.getAll();
        Assertions.assertEquals(2, catalogs.size());
    }

    @Test
    @Order(2)
    void get_one_catalog() {
        Catalog catalog = userCatalogService.getCatalog(1L);
        Catalog catalog1 = userCatalogService.getCatalog(2L);
        Assertions.assertNotNull(catalog);
        Assertions.assertNotNull(catalog1);
        Assertions.assertEquals("Wash outside only", catalog.getOfferingDescription());
        Assertions.assertEquals("Wash inside and outside", catalog1.getOfferingDescription());
    }

    @Test
    @Order(3)
    @TestTransaction
    void admin_update_catalog() {
        Catalog catalog1 = userCatalogService.getCatalog(2L);
        catalog1.setOfferingDescription("Description of the offering has been updated");
        adminCatalogService.updateCatalog(catalog1);

        List<Catalog> catalogs = userCatalogService.getAll();
        Assertions.assertEquals(2, catalogs.size());

        Catalog catalogUpdated = userCatalogService.getCatalog(2L);
        Assertions.assertEquals("Description of the offering has been updated", catalogUpdated.getOfferingDescription());
    }

    @Test
    @Order(4)
    @TestTransaction
    void admin_delete_catalog(){
        Catalog catalog1 = userCatalogService.getCatalog(2L);
        adminCatalogService.deleteCatalog(catalog1.getId());
        List<Catalog> catalogs = userCatalogService.getAll();
        Assertions.assertEquals(1, catalogs.size());
    }
}