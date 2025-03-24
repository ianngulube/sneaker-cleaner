package za.co.mafsoft.api.service.interfaces;

import io.quarkus.mailer.Mail;

public interface IEmailService {
    void sendEmail(Mail mail);
}
