package za.co.mafsoft.api.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import za.co.mafsoft.api.model.Address;
import za.co.mafsoft.api.model.Cart;
import za.co.mafsoft.api.model.GeoLocation;
import za.co.mafsoft.api.service.util.CartUtil;

import java.util.zip.DataFormatException;

@Slf4j
@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LocationServiceTest {

    @Inject
    LocationService locationService;

    @Inject
    CartUtil cartUtil;

    Address address = new Address();
    GeoLocation location = new GeoLocation();

    @BeforeEach
    void setUp() {
        location.setLatitude("1.000");
        address.setLocation(location);
        address.setProvince("Gauteng");
        address.setSuburb("Kempton Park");
        address.setStreet("158 Monument Road");
        address.setAreaCode("1619");
    }

    @Test
    void add_location() throws DataFormatException {
        Cart cart = cartUtil.getCart();
        location.setLongitude("2.000");
        locationService.addLocation(cart, address);
        Assertions.assertEquals(address, cart.getAddress());
    }

    @Test
    void add_location_incomplete_data() {
        Cart cart = cartUtil.getCart();
        Assertions.assertThrows(ValidationException.class,
                () -> locationService.addLocation(cart, address));
    }
}