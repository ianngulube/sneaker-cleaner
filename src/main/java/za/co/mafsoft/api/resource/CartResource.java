package za.co.mafsoft.api.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import za.co.mafsoft.api.mapper.CartFillerRequestCartFillerMapper;
import za.co.mafsoft.api.model.request.CartFillerRequest;
import za.co.mafsoft.api.model.request.CatalogRemove;
import za.co.mafsoft.api.service.CartService;
import za.co.mafsoft.api.service.CatalogService;
import za.co.mafsoft.api.service.UserService;

@Path("/cart")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CartResource {
    @Inject
    CartService cartService;
    @Inject
    CartFillerRequestCartFillerMapper cartFillerRequestCartFillerMapper;
    @Inject
    UserService userService;
    @Inject
    CatalogService catalogService;

    @Path("/add-to-cart")
    @POST
    public Response addToCart(final CartFillerRequest request) {
        cartService.addToCart(cartFillerRequestCartFillerMapper.fromCartFillerRequestToCartFiller(request, userService, catalogService));
        return Response.ok().build();
    }

    @Path("/remove-from-cart")
    @POST
    public Response removeFromCart(final CatalogRemove catalogRemove) {
        cartService.removeFromCart(catalogService.getCatalog(catalogRemove.getCatalogId()));
        return Response.ok().build();
    }

    @Path("/view-cart")
    @GET
    public Response viewCart() {
        return Response.ok(cartService.viewCart()).build();
    }

}
