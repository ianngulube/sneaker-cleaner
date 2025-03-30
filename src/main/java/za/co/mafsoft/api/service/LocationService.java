package za.co.mafsoft.api.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import za.co.mafsoft.api.model.Address;
import za.co.mafsoft.api.model.Cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.zip.DataFormatException;

@Slf4j
@ApplicationScoped
public class LocationService {
    private final Validator validator;

    @Inject
    public LocationService(Validator validator) {
        this.validator = validator;
    }

    public void addLocation(final Cart cart, final Address address) throws DataFormatException {
        if (!validateCart(cart)) {
            throw new DataFormatException("Cart is missing important data");
        }
        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(address);
        if (constraintViolations.isEmpty()) {
            cart.setAddress(address);
        } else {
            List<String> errors = new ArrayList<>();
            constraintViolations.forEach(addressConstraintViolation -> errors.add(addressConstraintViolation.getMessage()));
            throw new ValidationException(errors.toString());
        }
    }

    private boolean validateCart(final Cart cart) {
        if (Objects.isNull(cart)) {
            return false;
        }
        return !Objects.isNull(cart.getCatalogItemCount()) &&
                !Objects.isNull(cart.getUser()) &&
                !Objects.isNull(cart.getItemTransport());
    }
}
