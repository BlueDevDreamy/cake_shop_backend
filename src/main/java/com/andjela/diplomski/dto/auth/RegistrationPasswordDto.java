package com.andjela.diplomski.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationPasswordDto {
    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private  String token;
    @NotEmpty
    private String newPassword;
}
