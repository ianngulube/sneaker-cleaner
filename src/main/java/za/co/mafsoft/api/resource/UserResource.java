package za.co.mafsoft.api.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import za.co.mafsoft.api.model.User;
import za.co.mafsoft.api.model.request.UserLogin;
import za.co.mafsoft.api.model.request.UserVerify;
import za.co.mafsoft.api.model.response.UserCreateResponse;
import za.co.mafsoft.api.model.response.UserLoginResponse;
import za.co.mafsoft.api.model.response.UserVerifyResponse;
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

    @Path("/verify-user")
    @POST
    public Response verifyUser(final UserVerify request) {
        UserVerifyResponse userVerifyResponse = userService.verifyUser(request.getEmailOrMsisdn(), request.getVerificationCode());
        return Response.ok(userVerifyResponse).build();
    }

    @Path("/login")
    @POST
    public Response login(final UserLogin userLogin) {
        UserLoginResponse loginResponse = userService.login(userLogin);
        return Response.ok(loginResponse).build();
    }
}
