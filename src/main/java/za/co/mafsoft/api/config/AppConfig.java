package za.co.mafsoft.api.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.Produces;
import za.co.mafsoft.api.model.Cart;

@ApplicationScoped
public class AppConfig {
    @Produces
    @Named("cart")
    public Cart cart() {
        return new Cart();
    }
}
