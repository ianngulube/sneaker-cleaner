package za.co.mafsoft.api.service.interfaces;

import jakarta.transaction.Transactional;
import za.co.mafsoft.api.model.User;
import za.co.mafsoft.api.model.UserLogin;

import java.util.List;

public interface IUserService {
    @Transactional
    void createUser(User user);

    @Transactional
    void verifyUser(String emailOrMsisdn, String verificationCode);

    boolean login(UserLogin userLogin);

    List<User> getAll();

    User getOne(String emailOrMsisdn);
}
