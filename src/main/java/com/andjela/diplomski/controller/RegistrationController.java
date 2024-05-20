package com.andjela.diplomski.controller;

import com.andjela.diplomski.common.TokenStatus;
import com.andjela.diplomski.dto.auth.RegisterAdminDto;
import com.andjela.diplomski.dto.auth.RegisterClientDto;
import com.andjela.diplomski.dto.auth.RegisterEmployeeDto;
import com.andjela.diplomski.dto.auth.RegistrationPasswordDto;
import com.andjela.diplomski.dto.user.UserDto;
import com.andjela.diplomski.service.CartItemService;
import com.andjela.diplomski.service.CartService;
import com.andjela.diplomski.service.UserRegistrationService;
import com.andjela.diplomski.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private final UserRegistrationService userRegistrationService;
    private final CartService cartService;

    @Value("${frontend.baseUrl}")
    private String frontBaseUrl;

    //Istestirano u POSTMAN-u
    @PostMapping("register-admin")
    public ResponseEntity<UserDto> registerAdmin(@RequestBody @Valid RegisterAdminDto request){
        UserDto user = userRegistrationService.registerAdmin(request);

        final HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    //Istestirano u POSTMAN-u
    @PostMapping("register-employee")
    public ResponseEntity<UserDto> registerEmployee(@RequestBody @Valid RegisterEmployeeDto request){
        UserDto user = userRegistrationService.registerEmployee(request);

        final HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    //Istestirano u POSTMAN-u
    //Cim se klijen t registruje dodeljuje mu se cart
    @PostMapping("register-client")
    public ResponseEntity<UserDto> registerClient(@RequestBody @Valid RegisterClientDto request){
        UserDto user = userRegistrationService.registerClient(request);
        cartService.createCart(user);

        final HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    // Confirm token
    @GetMapping("confirm-token")
    public ResponseEntity<String> confirmRegistrationToken(@RequestParam(value = "token", required = true) String token) {
        TokenStatus tokenStatus = userRegistrationService.confirmRegistrationToken(token);

        if(tokenStatus == TokenStatus.VALID) {
            String response = "Token potvrdjen. Mozete se ulogovati: " + frontBaseUrl;
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            String response = "Nije validan tokenStatus: " + tokenStatus;
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    // User activation - verification
//    @GetMapping("resend-token/{email}")
//    public ResponseEntity<String> resendRegistrationToken(@PathVariable("email") final String email) {
//        String response = userRegistrationService.resendRegistrationTokenForEmail(email);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    // Save password
    @PostMapping("save-password")
    public ResponseEntity<String> saveRegistrationPassword(@Valid RegistrationPasswordDto registrationPasswordDto) {
        userRegistrationService.saveRegistrationPassword(registrationPasswordDto);
        return new ResponseEntity<>("Password successfully changed", HttpStatus.OK);
    }
}
