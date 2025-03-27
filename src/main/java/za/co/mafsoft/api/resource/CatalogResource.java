package za.co.mafsoft.api.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import za.co.mafsoft.api.service.CatalogService;

@Path("/catalog")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CatalogResource {
    @Inject
    CatalogService catalogService;

    @Path("/get-all")
    @GET
    public Response getAll() {
        return Response.ok(catalogService.getAll()).build();
    }

    @Path("/get-one/{catalog-id}")
    @GET
    public Response getOne(@PathParam("catalog-id") final Long catalogId) {
        return Response.ok(catalogService.getCatalog(catalogId)).build();
    }
}
