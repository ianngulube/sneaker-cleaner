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
import za.co.mafsoft.api.model.request.UserLogin;
import za.co.mafsoft.api.model.response.UserCreateResponse;
import za.co.mafsoft.api.model.response.UserLoginResponse;
import za.co.mafsoft.api.model.response.UserVerifyResponse;
import za.co.mafsoft.api.repository.UserRepository;
import za.co.mafsoft.api.service.interfaces.IEmailService;
import za.co.mafsoft.api.service.interfaces.IUserService;
import za.co.mafsoft.api.util.AppUtil;

import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

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
                    .responseCode(SC_FORBIDDEN)
                    .responseDescription(e.getCause().getMessage())
                    .build();
        }
        emailService
                .sendEmail(Mail.withHtml(user.getEmail(),
                        verificationEmailSubject,
                        String.format(verificationEmailText, verificationCode)));
        return UserCreateResponse.builder()
                .responseCode(SC_OK)
                .responseDescription(String.format("Email %s/ Msisdn %s user created", user.getEmail(), user.getMsisdn()))
                .build();
    }

    @Transactional
    @Override
    public UserVerifyResponse verifyUser(final String emailOrMsisdn, final String verificationCode) {
        log.info("UserService::verifyUser => {}", emailOrMsisdn);
        try {
            Optional<User> user = Optional.ofNullable(this.getOne(emailOrMsisdn));
            if (user.isPresent()) {
                if (!verificationCode.equals(user.get().getVerificationCode())) {
                    return UserVerifyResponse.builder()
                            .responseCode(SC_FORBIDDEN)
                            .responseDescription("Incorrect Verification Code")
                            .build();
                } else {
                    userRepository.update("verified = ?1 WHERE id = ?2", true, user.get().getId());
                    return UserVerifyResponse.builder()
                            .responseCode(SC_OK)
                            .responseDescription("Success")
                            .build();
                }
            } else {
                return UserVerifyResponse.builder()
                        .responseCode(SC_FORBIDDEN)
                        .responseDescription("User does not exist")
                        .build();
            }
        } catch (Exception e) {
            log.warn("{}", e.getMessage());
            return UserVerifyResponse.builder()
                    .responseCode(SC_FORBIDDEN)
                    .responseDescription("User does not exist")
                    .build();
        }
    }

    @Override
    public UserLoginResponse login(UserLogin userLogin) {
        log.info("UserService::login => {}", userLogin.getEmailOrMsisdn());
        UserLoginResponse.UserLoginResponseBuilder loginResponseBuilder = UserLoginResponse.builder();
        try {
            Optional<User> optionalUser = Optional.ofNullable(this.getOne(userLogin.getEmailOrMsisdn()));
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (!user.isVerified()) {
                    loginResponseBuilder.responseDescription("User needs to be verified before attempt to login");
                    loginResponseBuilder.responseCode(SC_UNAUTHORIZED);
                    return loginResponseBuilder.build();
                }
                if (!user.getPin().equals(userLogin.getPin())) {
                    loginResponseBuilder.responseDescription("Incorrect PIN");
                    loginResponseBuilder.responseCode(SC_UNAUTHORIZED);
                    return loginResponseBuilder.build();
                }
                loginResponseBuilder.authToken("AUTH_TOKEN");
                loginResponseBuilder.responseDescription("Success");
                loginResponseBuilder.user(user);
                loginResponseBuilder.responseCode(SC_OK);
            } else {
                loginResponseBuilder.responseDescription("User does not exist");
                loginResponseBuilder.responseCode(SC_UNAUTHORIZED);
            }
            return loginResponseBuilder.build();

        } catch (Exception e) {
            log.warn("{}", e.getMessage());
            loginResponseBuilder.responseDescription("User does not exist");
            loginResponseBuilder.responseCode(SC_UNAUTHORIZED);
            return loginResponseBuilder.build();
        }

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
