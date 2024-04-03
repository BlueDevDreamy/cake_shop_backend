package com.andjela.diplomski.service.email;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;

import com.sendgrid.helpers.mail.objects.Email;
import com.andjela.diplomski.dto.email.EmailSendResponseDto;
import com.andjela.diplomski.dto.email.SendEmailRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
@ConditionalOnProperty(prefix = "email", name = "provider", havingValue = "sendgrid", matchIfMissing = false)
public class SendGridMailerService implements MailerService{

    private static final String EMAIL_PROVIDER = "sendgrid";

    private final SendGrid sendGridClient;
    private final Environment env;

    @Value("${support.email}")
    private String supportEmail;

    public EmailSendResponseDto sendTextEmail(SendEmailRequestDto request) {
        return sendEmail(request.getFrom(), request.getTo(), request.getSubject(), new Content("text/plain", request.getBody()));
    }

    public EmailSendResponseDto sendHtmlEmail(SendEmailRequestDto request) {
        return sendEmail(request.getFrom(), request.getTo(), request.getSubject(), new Content("text/html", request.getBody()));
    }

    private EmailSendResponseDto sendEmail(String from, String to, String subject, Content content) {
        if(StringUtils.isBlank(from)) {
            log.info("From email empty, falling back to support email as from: " + supportEmail);
            from = supportEmail;
        }
        String sendgridReplyToEmail = env.getProperty("sendgrid.email.replyto");
        Mail mail = new Mail(new Email(from), subject, new Email(to), content);
        mail.setReplyTo(new Email(sendgridReplyToEmail));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = this.sendGridClient.api(request);
            return new EmailSendResponseDto(response, to, EMAIL_PROVIDER);
        } catch (IOException e) {
            log.error("Sendgrid error", e);
        }
        return new EmailSendResponseDto(true, to, EMAIL_PROVIDER);
    }
}
