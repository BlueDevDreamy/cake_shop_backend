package com.andjela.diplomski.dto.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendEmailRequestDto {
    @Email
    private String from;
    @Email
    @NotNull
    private String to;
    @NotEmpty
    private String subject;
    @NotEmpty
    private String body;
}
