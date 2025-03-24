package za.co.mafsoft.api.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import za.co.mafsoft.api.entity.UserEntity;
import za.co.mafsoft.api.mapper.UserMapper;
import za.co.mafsoft.api.model.User;
import za.co.mafsoft.api.model.UserLogin;
import za.co.mafsoft.api.repository.UserRepository;
import za.co.mafsoft.api.service.interfaces.IUserService;
import za.co.mafsoft.api.util.AppUtil;

import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
public class UserService implements IUserService {
    @Inject
    UserRepository userRepository;
    @Inject
    UserMapper userMapper;

    @Transactional
    @Override
    public void createUser(final User user) {
        String verificationCode = AppUtil.generateVerificationCode();
        System.setProperty("sneaker.test.verification.code", verificationCode);
        user.setVerificationCode(verificationCode);
        userRepository.persist(userMapper.modelToEntity(user));
    }

    @Transactional
    @Override
    public void verifyUser(final String emailOrMsisdn, final String verificationCode) {
        Optional<User> user = Optional.ofNullable(this.getOne(emailOrMsisdn));
        if (user.isPresent() && verificationCode.equals(user.get().getVerificationCode())) {
            userRepository.update("verified = ?1", true);
        }
    }

    @Override
    public boolean login(UserLogin userLogin) {
        Optional<User> optionalUser = Optional.of(this.getOne(userLogin.getEmailOrMsisdn()));
        return optionalUser
                .map(user -> user.getPin().equals(userLogin.getPin()) && user.isVerified()).
                orElse(false);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll()
                .stream()
                .map(panacheEntityBase -> userMapper.entityToModel((UserEntity) panacheEntityBase))
                .collect(Collectors.toList());
    }

    @Override
    public User getOne(String emailOrMsisdn) {
        PanacheQuery<UserEntity> entity = userRepository.find("email = ?1 OR msisdn = ?2", emailOrMsisdn, emailOrMsisdn);
        if (entity.singleResultOptional().isPresent()) {
            return userMapper.entityToModel(entity.singleResultOptional().get());
        }
        throw new MissingResourceException("No record found", User.class.getName(), emailOrMsisdn);
    }
}
