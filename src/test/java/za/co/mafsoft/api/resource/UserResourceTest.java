package za.co.mafsoft.api.resource;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import za.co.mafsoft.api.model.User;
import za.co.mafsoft.api.model.request.UserLogin;
import za.co.mafsoft.api.model.request.UserVerify;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserResourceTest {

    @Test
    @Order(1)
    void createUser() {

        User user = new User();
        user.setFirstName("Ian");
        user.setLastName("Ngulube");
        user.setEmail("ngulubeian94@gmail.com");
        user.setMsisdn("27619250520");
        user.setPin("1807");

        given()
                .when()
                .body(user)
                .contentType(MediaType.APPLICATION_JSON)
                .post("/users/create")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    void verifyUser() {
        UserVerify request = new UserVerify();
        request.setEmailOrMsisdn("ngulubeian94@gmail.com");
        request.setVerificationCode(System.getProperty("sneaker.test.verification.code"));
        given()
                .when()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON)
                .post("/users/verify-user")
                .then()
                .statusCode(200);
    }

    @Test
    void login() {
        UserLogin userLogin=new UserLogin();
        userLogin.setPin("1807");
        userLogin.setEmailOrMsisdn("27619250520");
        given()
                .when()
                .body(userLogin)
                .contentType(MediaType.APPLICATION_JSON)
                .post("/users/login")
                .then()
                .statusCode(200);
    }
}