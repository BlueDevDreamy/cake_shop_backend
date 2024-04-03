package com.andjela.diplomski.service.email;

import com.andjela.diplomski.dto.email.EmailSendResponseDto;
import com.andjela.diplomski.dto.email.SendEmailRequestDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@RequiredArgsConstructor
@Service
@Validated
@ConditionalOnProperty(prefix = "email", name = "provider", havingValue = "smtp", matchIfMissing = true)
public class SmtpMailerService implements MailerService{

    private static final String EMAIL_PROVIDER = "smtp";

    private final JavaMailSender emailSender;


    @Override
    public EmailSendResponseDto sendTextEmail(@Valid SendEmailRequestDto request) {
        return sendEmail(request, false);
    }

    @Override
    public EmailSendResponseDto sendHtmlEmail(@Valid SendEmailRequestDto request) {
        return sendEmail(request, true);
    }

    private EmailSendResponseDto sendEmail(SendEmailRequestDto request, boolean isHtml) {
        String to = request.getTo();
        String subject = request.getSubject();

        MimeMessage mimeMessage = emailSender.createMimeMessage();

        try{
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
//         messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(request.getBody(), isHtml);
            log.info("trying to send email to " + to + " with subject " + subject);
            emailSender.send(mimeMessage);
            return EmailSendResponseDto.builder()
                    .emailProvider(EMAIL_PROVIDER)
                    .emailSentTo(to)
                    .error(false)
                    .build();
        } catch (MessagingException e) {
            log.error("Error sending smtp email to " + to + " with subject " + subject, e);
            return EmailSendResponseDto.builder()
                    .emailProvider(EMAIL_PROVIDER)
                    .emailSentTo(to)
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }
}
