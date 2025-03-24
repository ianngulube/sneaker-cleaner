package za.co.mafsoft.api.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import za.co.mafsoft.api.service.interfaces.IEmailService;

@Slf4j
@ApplicationScoped
public class MessageService implements IEmailService {
    @Inject
    private Mailer mailer;

    @Override
    public void sendEmail(final Mail mail) {
        mailer.send(mail);
    }

}
