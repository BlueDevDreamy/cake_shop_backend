package com.andjela.diplomski.service.email;

import com.andjela.diplomski.dto.email.EmailSendResponseDto;
import com.andjela.diplomski.dto.email.SendEmailRequestDto;
import jakarta.validation.Valid;

public interface MailerService {
    EmailSendResponseDto sendTextEmail(@Valid SendEmailRequestDto request);
    EmailSendResponseDto sendHtmlEmail(@Valid SendEmailRequestDto request);
}
