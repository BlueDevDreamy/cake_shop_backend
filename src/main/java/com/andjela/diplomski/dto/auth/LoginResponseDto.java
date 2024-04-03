package com.andjela.diplomski.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
    private Set<String> roles;
    @Builder.Default
    private String tokenType = "Bearer";
    private Date accessTokenExpiresAt;
    private Date refreshTokenExpiresAt;
}
