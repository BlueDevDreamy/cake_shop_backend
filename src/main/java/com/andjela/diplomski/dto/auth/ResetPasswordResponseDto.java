package com.andjela.diplomski.dto.auth;

import com.andjela.diplomski.common.TokenStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordResponseDto {
    private TokenStatus tokenStatus;
    private boolean passwordChanged;
    private String message;
}
