package za.co.mafsoft.api.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import za.co.mafsoft.api.model.Cart;
import za.co.mafsoft.api.service.CartService;
import za.co.mafsoft.api.service.CheckoutService;

import java.util.zip.DataFormatException;

@Slf4j
@Path("/checkout")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"User","Admin"})
public class CheckoutResource {
    @Inject
    CheckoutService checkoutService;
    @Inject
    CartService cartService;

    @GET
    public Response checkout() {
        try {
            Cart cart = cartService.viewCart();
            checkoutService.checkout(cart);
            return Response.ok(cart).build();
        } catch (DataFormatException e) {
            log.warn("{}", e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }
}
