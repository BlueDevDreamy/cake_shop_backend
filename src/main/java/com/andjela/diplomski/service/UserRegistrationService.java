package com.andjela.diplomski.service;

import com.andjela.diplomski.common.TokenStatus;
import com.andjela.diplomski.common.UserType;
import com.andjela.diplomski.dto.auth.*;
import com.andjela.diplomski.dto.user.UserData;
import com.andjela.diplomski.dto.user.UserDto;
import com.andjela.diplomski.dto.user.UserMapper;
import com.andjela.diplomski.entity.Authority;
import com.andjela.diplomski.entity.PasswordResetToken;
import com.andjela.diplomski.entity.RegistrationToken;
import com.andjela.diplomski.entity.User;
import com.andjela.diplomski.events.OnRegistrationCompleteEvent;
import com.andjela.diplomski.exception.*;
import com.andjela.diplomski.repository.UserRepository;
import com.andjela.diplomski.repository.auth.AuthorityRepository;
import com.andjela.diplomski.repository.auth.PasswordResetTokenRepository;
import com.andjela.diplomski.repository.auth.RegistrationTokenRepository;
import com.andjela.diplomski.service.auth.RegistrationTokenService;
import com.andjela.diplomski.utils.AuthHelper;
import com.andjela.diplomski.utils.RequestHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserRegistrationService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final RegistrationTokenRepository registrationTokenRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartService cartService;
    private final RegistrationTokenService registrationTokenService;


    @Value("${frontend.resetPasswordUrl}")
    private String frontendResetPasswordUrl;

    @Value("${frontend.registrationConfirmTokenUrl}")
    private String confirmTokenUiPageUrl;

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    public Optional<User> getUserByPasswordResetToken(final String token) {
        Optional<RegistrationToken> registrationToken = registrationTokenRepository.findByToken(token);
        User user = registrationToken.isPresent() ? registrationToken.get().getUser() : null;
        return Optional.ofNullable(user);
    }

    public ResetPasswordResponseDto resetUserPassword(String resetToken, ResetPasswordRequestDto resetPasswordDto) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(resetToken)
                .orElseThrow(() -> new ResourceNotFoundException("Nije pronadjen password reset token."));
        User user = passwordResetToken.getUser();

        if (passwordResetToken.isExpired()) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return ResetPasswordResponseDto.builder()
                    .tokenStatus(TokenStatus.INVALID)
                    .message("Token je istekao.")
                    .passwordChanged(false)
                    .build();
        }

        user.setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        passwordResetTokenRepository.delete(passwordResetToken);

        log.info("Changed password for user " + user.getEmail());

        return ResetPasswordResponseDto.builder()
                .tokenStatus(TokenStatus.VALID)
                .message("Lozinka promenjena")
                .passwordChanged(true)
                .build();
    }
//ToDo requestResetUserPassword

//    public EmailSendResponseDto requestResetUserPassword(Long userId) {
//        log.info("Reseting password for user with is " + userId);
//        UserDto userDto = userService.getUserById(userId);
//        User user = UserMapper.MAPPER.mapToUser(userDto);
//
//        Optional<PasswordResetToken> passwordResetTokenOpt = passwordResetTokenRepository.findByUser(user);
//
//
//        PasswordResetToken passwordResetToken = passwordResetTokenOpt.isEmpty() ?
//                PasswordResetToken.builder().user(user).createdAt(LocalDateTime.now()).build()
//                : passwordResetTokenOpt.get();
//
//        passwordResetToken.setToken(UUID.randomUUID().toString());
//        passwordResetToken.setExpiryDate(ZonedDateTime.now().plusMinutes(PasswordResetToken.EXPIRATION_MINUTES));
//
//        passwordResetTokenRepository.save(passwordResetToken);
//
//        final String subject = "Reset lozinke";
//        final String resetPasswordUrl = frontendResetPasswordUrl + "?token=" + passwordResetToken.getToken();
//        final String message = "Kliknite na link da resetujete lozinku: ";
//        final String messageBody = message + " \r\n" + resetPasswordUrl;
//        SendEmailRequestDto emailRequest = SendEmailRequestDto.builder()
//                .subject(subject)
//                .body(messageBody)
//                .to(user.getEmail())
//                .from(supportEmail)
//                .build();
//
//        EmailSendResponseDto response = mailerService.sendTextEmail(emailRequest);
//
//        return response;
//    }
//
//        @Transactional
//    public UserDto registerAdmin(RegisterAdminDto request) {
//        if(!AuthHelper.currentUserIsSuperAdmin()) {
//            throw new ApiValidationException("Not enough privilegies to create admin.");
//        }
//        return registerUser(request);
//    }

    @Transactional
    public UserDto registerAdmin(RegisterAdminDto request) {
        if(!AuthHelper.currentUserIsSuperAdmin()) {
            throw new ApiValidationException("Not enough privilegies to create admin.");
        }
        return registerUser(request);
    }
    @Transactional
    public UserDto registerEmployee(RegisterEmployeeDto request) {
        if(!AuthHelper.currentUserIsSuperAdmin()) {
            throw new ApiValidationException("Not enough privilegies to create employee.");
        }
        return registerUser(request);
    }

    @Transactional
    public UserDto registerClient(RegisterClientDto request) {
        //CartDto cartDto = cartService.createCart(registerUser(request)); // Nisam sigurna da li je ispravno ovako da dodam cart samo klijent useru
        return registerUser(request);
    }

    private UserDto registerUser(UserData request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new EntityAlreadyExistsException("Email already exists!.");
        }

        log.info("Trying to create user " + request + " ...");

        Set<Authority> authorities = authorityRepository.findAll()
                .stream().filter(a -> request.getAuthorities().stream()
                        .filter(ra -> ra.equalsIgnoreCase(a.getName())).findAny().isPresent()
                )
                .collect(Collectors.toSet());

        UserType userType = UserType.valueOf(request.getUserType());

        String passwordEncoded = request.getPassword() != null ? passwordEncoder.encode(request.getPassword()) : null;

        if (request.getEmail() == null || !request.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            throw new DataNotValidException("Email is not in a valid format");
        }
        if (request.getPassword() == null || request.getPassword().trim().isEmpty() || !request.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
            throw new DataNotValidException("Password is not in a valid format");
        }
        if (request.getFirstName() == null || request.getFirstName().trim().isEmpty() || !request.getFirstName().matches("^[A-Z][a-z ]*$")) {
            throw new DataNotValidException("First name is not in a valid format");
        }
        if (request.getLastName() == null || request.getLastName().trim().isEmpty() || !request.getLastName().matches("^[A-Z][a-z ]*$")) {
            throw new DataNotValidException("Last name is not in a valid format");
        }
        if (request.getPhoneNumber() == null || request.getPhoneNumber().trim().isEmpty() || !request.getPhoneNumber().matches("^[0-9 ]+$")) {
            throw new DataNotValidException("Phone number is not in a valid format");
        }
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoded)
                .createdAt(LocalDateTime.now())
                .authorities(authorities)
                .userType(userType)
                .build();

        try{
            User registeredUser = userRepository.save(user);
            //ToDo send email when successfully registered
//            SendEmailRequestDto sendEmailRequestDto = SendEmailRequestDto.builder()
//                    .subject("Registration email")
//                    .to(user.getEmail())
//                    .body("...")
//                    .from("emailjstest327@gmail.com")
//                    .build();
//            EmailSendResponseDto emailSendResponseDto = mailerService.sendTextEmail(sendEmailRequestDto);
//            log.info("Email response: ", emailSendResponseDto);
            return UserMapper.MAPPER.mapToUserDTO(registeredUser);

        } catch (RuntimeException e) {
            log.error("Error creating user " + user, e);
            throw new AppException("Error creating user: " + e.getMessage());
        }
    }

//    @Transactional
//    public void confirmRegistration(final OnRegistrationCompleteEvent event) {
//        final User user = event.getUser();
//        RegistrationToken verificationRefreshToken = registrationTokenService.createRegistrationTokenForUser(user);
//        final SendEmailRequestDto emailRequest = constructEmailMessageRegToken(event, user, verificationRefreshToken.getToken());
//        EmailSendResponseDto response = mailerService.sendTextEmail(emailRequest);
//        log.info("Email sent, response: " + response);
//    }

//    @Transactional
//    public String resendRegistrationTokenForEmail(final String email) {
//        final User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
//        RegistrationToken registrationToken = registrationTokenRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("RefreshToken", "user", email));
//        registrationToken.updateToken(UUID.randomUUID().toString());
//        registrationToken = registrationTokenRepository.save(registrationToken);
//
//        final SendEmailRequestDto emailRequest = constructEmailMessageRegToken(RequestHelper.getBaseUrl(), user, registrationToken.getToken());
//        EmailSendResponseDto response = mailerService.sendTextEmail(emailRequest);
//        log.info("Resend tokem email sent", response);
//        return "Registration token resend";
//    }

//    private SendEmailRequestDto constructEmailMessageRegToken(final OnRegistrationCompleteEvent event, final User user, final String token) {
//        return constructEmailMessageRegToken(event.getAppUrl(), user, token);
//    }
//    private SendEmailRequestDto constructEmailMessageRegToken(final String baseUrl, final User user, final String token) {
//        final String subject = "Registration Confirmation";
//        final String confirmationUrl = confirmTokenUiPageUrl + "?token=" + token;
//        String message = "Uspesno ste se registrovali. Za zavrsetak procea registracije kliknite na link: ";
//        String messageBody = message + " \r\n" + confirmationUrl;
//
//        message += "\n\n U slucaju da imate problema sa gornjim linkom kliknite na: ";
//        final String confirmationUrlBackup = baseUrl + "/api/registration/confirm-token?token=" + token;
//        messageBody += message + " \r\n" + confirmationUrlBackup;
//
//        final SendEmailRequestDto emailRequest = constructEmail(subject, messageBody, user);
//        return emailRequest;
//    }

//    private SendEmailRequestDto constructEmail(String subject, String body, User user) {
//        return SendEmailRequestDto.builder()
//                .subject(subject)
//                .body(body)
//                .to(user.getEmail())
//                .from(supportEmail)
//                .build();
//    }

    @Transactional
    public void saveRegistrationPassword(RegistrationPasswordDto registrationPasswordDto) {
        final TokenStatus resetTokenStatus = validatePasswordResetToken(registrationPasswordDto.getToken());

        if(resetTokenStatus == TokenStatus.INVALID) {
            throw new AppException("Token nije validan.");
        }
        else if(resetTokenStatus == TokenStatus.EXPIRED) {
            throw new AppException("Token je istekao.");
        }
        Optional<User> userFound = getUserByPasswordResetToken(registrationPasswordDto.getToken());
        if(userFound.isPresent()) {
            User user = userFound.get();
            user.setPassword(passwordEncoder.encode(registrationPasswordDto.getNewPassword()));
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        } else {
            throw new AppException("Nije pronadjen korisnik sa datim tokenom.");
        }
    }

    @Transactional
    public TokenStatus confirmRegistrationToken(String token) {
        RegistrationToken registrationToken = registrationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Nije pronadjen korisnik sa tokenom " + token));

        User user = registrationToken.getUser();

        TokenStatus tokenStatus = validateRegistrationToken(registrationToken);
        if(tokenStatus == TokenStatus.EXPIRED) {
            return tokenStatus;
        } else if(tokenStatus == TokenStatus.INVALID) {
            // TODO email resend token link
            return tokenStatus;
        }
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        registrationTokenRepository.delete(registrationToken);
        log.info("Registration token " + token + " confirmed for user " + user.getEmail());
        return tokenStatus;
    }


    private TokenStatus validatePasswordResetToken(String token) {
        Optional<RegistrationToken> registrationToken = registrationTokenRepository.findByToken(token);
        return registrationToken.isEmpty() ? TokenStatus.INVALID
                : registrationToken.get().getExpiryDate().isBefore(ZonedDateTime.now()) ? TokenStatus.EXPIRED
                : TokenStatus.VALID;
    }

    private TokenStatus validateRegistrationToken(RegistrationToken registrationToken) {
        final User user = registrationToken.getUser();

        if (registrationToken.isExpired()) {
            log.info("Token expired " + registrationToken.getToken() + " for user " + user.getEmail());
            registrationTokenRepository.delete(registrationToken);
            return TokenStatus.EXPIRED;
        }

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return TokenStatus.VALID;
    }
}
