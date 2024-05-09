package com.andjela.diplomski.controller;

import com.andjela.diplomski.dto.auth.LoginRequestDto;
import com.andjela.diplomski.dto.auth.LoginResponseDto;
import com.andjela.diplomski.dto.auth.TokenRefreshRequestDto;
import com.andjela.diplomski.dto.auth.TokenRefreshResponseDto;
import com.andjela.diplomski.service.auth.AuthService;
import com.andjela.diplomski.service.auth.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    //Istestirano u POSTMAN-u
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        LoginResponseDto responseDto = authService.login(request);
        return ResponseEntity.ok(responseDto);
    }

    //Istestirano u POSTMAN-u
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequestDto request) {
        String requestRefreshToken = request.getRefreshToken();
        TokenRefreshResponseDto tokenRefreshResponseDto = refreshTokenService.refreshToken(requestRefreshToken);
        return ResponseEntity.ok(tokenRefreshResponseDto);
    }

}
