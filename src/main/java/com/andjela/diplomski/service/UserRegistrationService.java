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
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final RegistrationTokenRepository registrationTokenRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;


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

    @Transactional
    public UserDto registerAdmin(RegisterAdminDto request) {
        return registerUser(request);
    }

    @Transactional
    public UserDto registerClient(RegisterClientDto request) {
        return registerUser(request);
    }

    private UserDto registerUser(UserData request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException("Email already exists!.");
        }

        log.info("Trying to create user " + request + " ...");

        Set<Authority> authorities = authorityRepository.findAll()
                .stream().filter(a -> request.getAuthorities().stream()
                        .filter(ra -> ra.equalsIgnoreCase(a.getName())).findAny().isPresent()
                )
                .collect(Collectors.toSet());
        System.out.println(authorities);
        UserType userType = UserType.valueOf(request.getUserType());

        String passwordEncoded = request.getPassword() != null ? passwordEncoder.encode(request.getPassword()) : null;

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

        try {
            User registeredUser = userRepository.save(user);
            return UserMapper.MAPPER.mapToUserDTO(registeredUser);

        } catch (RuntimeException e) {
            log.error("Error creating user " + user, e);
            throw new AppException("Error creating user: " + e.getMessage());
        }
    }

    @Transactional
    public void saveRegistrationPassword(RegistrationPasswordDto registrationPasswordDto) {
        final TokenStatus resetTokenStatus = validatePasswordResetToken(registrationPasswordDto.getToken());

        if (resetTokenStatus == TokenStatus.INVALID) {
            throw new AppException("Token nije validan.");
        } else if (resetTokenStatus == TokenStatus.EXPIRED) {
            throw new AppException("Token je istekao.");
        }
        Optional<User> userFound = getUserByPasswordResetToken(registrationPasswordDto.getToken());
        if (userFound.isPresent()) {
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
        if (tokenStatus == TokenStatus.EXPIRED) {
            return tokenStatus;
        } else if (tokenStatus == TokenStatus.INVALID) {
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
