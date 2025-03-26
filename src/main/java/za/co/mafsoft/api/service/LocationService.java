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
import java.util.Set;

@Slf4j
@ApplicationScoped
public class LocationService {
    private final Validator validator;

    @Inject
    public LocationService(Validator validator) {
        this.validator = validator;
    }

    public void addLocation(final Cart cart, final Address address) {
        Set<ConstraintViolation<Address>> constraintViolations = validator.validate(address);
        if (constraintViolations.isEmpty()) {
            cart.setAddress(address);
        } else {
            List<String> errors = new ArrayList<>();
            constraintViolations.forEach(addressConstraintViolation -> errors.add(addressConstraintViolation.getMessage()));
            throw new ValidationException(errors.toString());
        }
    }
}
