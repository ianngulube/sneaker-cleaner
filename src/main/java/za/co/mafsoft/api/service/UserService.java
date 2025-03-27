package za.co.mafsoft.api.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.mailer.Mail;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import za.co.mafsoft.api.entity.UserEntity;
import za.co.mafsoft.api.mapper.UserMapper;
import za.co.mafsoft.api.model.User;
import za.co.mafsoft.api.model.UserLogin;
import za.co.mafsoft.api.model.response.UserCreateResponse;
import za.co.mafsoft.api.repository.UserRepository;
import za.co.mafsoft.api.service.interfaces.IEmailService;
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
    @Inject
    IEmailService emailService;

    @ConfigProperty(name = "app.email.verification.subject")
    String verificationEmailSubject;
    @ConfigProperty(name = "app.email.verification.text")
    String verificationEmailText;


    @Transactional
    @Override
    public UserCreateResponse createUser(final User user) {
        log.info("UserService::createUser => {} {}", user.getMsisdn(), user.getEmail());
        String verificationCode = AppUtil.generateVerificationCode();
        System.setProperty("sneaker.test.verification.code", verificationCode);
        user.setVerificationCode(verificationCode);
        try {
            userRepository.persistAndFlush(userMapper.modelToEntity(user));
        } catch (Exception e) {
            log.warn("userRepository::persist failed => {}", e.getMessage());
            return UserCreateResponse.builder()
                    .responseCode(403)
                    .responseDescription(e.getCause().getMessage())
                    .build();
        }
        emailService
                .sendEmail(Mail.withHtml(user.getEmail(),
                        verificationEmailSubject,
                        String.format(verificationEmailText, verificationCode)));
        return UserCreateResponse.builder()
                .responseCode(200)
                .responseDescription(String.format("Email %s/ Msisdn %s user created", user.getEmail(), user.getMsisdn()))
                .build();
    }

    @Transactional
    @Override
    public void verifyUser(final String emailOrMsisdn, final String verificationCode) {
        log.info("UserService::verifyUser => {}", emailOrMsisdn);
        Optional<User> user = Optional.ofNullable(this.getOne(emailOrMsisdn));
        if (user.isPresent() && verificationCode.equals(user.get().getVerificationCode())) {
            userRepository.update("verified = ?1 WHERE id = ?2", true, user.get().getId());
        }
    }

    @Override
    public boolean login(UserLogin userLogin) {
        log.info("UserService::login => {}", userLogin.getEmailOrMsisdn());
        Optional<User> optionalUser = Optional.of(this.getOne(userLogin.getEmailOrMsisdn()));
        return optionalUser
                .map(user -> user.getPin().equals(userLogin.getPin()) && user.isVerified()).
                orElse(false);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll()
                .stream()
                .map(panacheEntityBase -> userMapper.entityToModel(panacheEntityBase))
                .collect(Collectors.toList());
    }

    @Override
    public User getOne(String emailOrMsisdn) {
        log.info("UserService::getOne => {}", emailOrMsisdn);
        PanacheQuery<UserEntity> entity = userRepository.find("email = ?1 OR msisdn = ?2", emailOrMsisdn, emailOrMsisdn);
        if (entity.singleResultOptional().isPresent()) {
            return userMapper.entityToModel(entity.singleResultOptional().get());
        }
        throw new MissingResourceException("No record found", User.class.getName(), emailOrMsisdn);
    }
}
