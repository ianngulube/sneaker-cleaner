package za.co.mafsoft.api.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import za.co.mafsoft.api.model.User;
import za.co.mafsoft.api.model.request.UserLogin;
import za.co.mafsoft.api.model.response.UserLoginResponse;
import za.co.mafsoft.api.service.interfaces.IUserService;

@Slf4j
@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
class UserServiceTest {

    @Inject
    IUserService userService;

    @Test
    @Order(1)
    void create_user_in_database_successfully() {
        Assertions.assertEquals(0, userService.getAll().size());
        User user = new User();
        user.setFirstName("Ian");
        user.setLastName("Ngulube");
        user.setEmail("ngulubeian94@gmail.com");
        user.setMsisdn("27619250520");
        user.setPin("1807");
        userService.createUser(user);
        Assertions.assertEquals(1, userService.getAll().size());
    }

    @Test
    @Order(2)
    void verify_user_successfully() {
        String emailOrMsisdn = "ngulubeian94@gmail.com";
        User userToVerify = userService.getOne(emailOrMsisdn);
        Assertions.assertNotNull(userToVerify);
        String verificationCode = System.getProperty("sneaker.test.verification.code");
        log.info("Verification Code => {}", verificationCode);
        userService.verifyUser(emailOrMsisdn, verificationCode);
        User userWithVerificationCodeSet = userService.getOne(emailOrMsisdn);
        Assertions.assertNotNull(userWithVerificationCodeSet);
        Assertions.assertNotNull(userWithVerificationCodeSet.getVerificationCode());

    }

    @Test
    @Order(3)
    void login_successfully() {
        Assertions.assertEquals(1, userService.getAll().size());
        UserLogin userLogin = new UserLogin();
        userLogin.setEmailOrMsisdn("ngulubeian94@gmail.com");
        userLogin.setPin("1807");
        UserLoginResponse login = userService.login(userLogin);
        Assertions.assertNotNull(login.getAuthToken());
    }
}