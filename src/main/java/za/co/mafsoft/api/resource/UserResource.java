package za.co.mafsoft.api.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import za.co.mafsoft.api.model.User;
import za.co.mafsoft.api.model.UserLogin;
import za.co.mafsoft.api.model.response.UserCreateResponse;
import za.co.mafsoft.api.service.interfaces.IUserService;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    IUserService userService;

    @Path("/create")
    @POST
    public Response createUser(final User user) {
        UserCreateResponse createResponse = userService.createUser(user);
        return Response.ok(createResponse).build();
    }

    @Path("/verify-user/{email-or-msisdn}/{verification-code}")
    @POST
    public Response verifyUser(@PathParam("email-or-msisdn") final String emailOrMsisdn,
                               @PathParam("verification-code") final String verificationCode) {
        userService.verifyUser(emailOrMsisdn, verificationCode);
        return Response.ok().build();
    }

    @Path("/login")
    @POST
    public Response login(final UserLogin userLogin) {
        Boolean result = userService.login(userLogin);
        return Response.status(Response.Status.OK).entity(result).build();
    }
}
