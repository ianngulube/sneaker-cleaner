package za.co.mafsoft.api.service.interfaces;

import jakarta.transaction.Transactional;
import za.co.mafsoft.api.model.User;
import za.co.mafsoft.api.model.request.UserLogin;
import za.co.mafsoft.api.model.response.UserCreateResponse;
import za.co.mafsoft.api.model.response.UserLoginResponse;
import za.co.mafsoft.api.model.response.UserVerifyResponse;

import java.util.List;

public interface IUserService {
    @Transactional
    UserCreateResponse createUser(User user);

    @Transactional
    UserVerifyResponse verifyUser(String emailOrMsisdn, String verificationCode);

    UserLoginResponse login(UserLogin userLogin);

    List<User> getAll();

    User getOne(String emailOrMsisdn);
}
