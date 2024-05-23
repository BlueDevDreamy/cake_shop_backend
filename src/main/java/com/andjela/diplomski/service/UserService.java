package com.andjela.diplomski.service;

import com.andjela.diplomski.common.TokenStatus;
import com.andjela.diplomski.common.UserType;
import com.andjela.diplomski.dto.auth.*;
import com.andjela.diplomski.dto.user.UserDto;
import com.andjela.diplomski.dto.user.UserData;
import com.andjela.diplomski.dto.user.UserMapper;
import com.andjela.diplomski.dto.user.UserUpdateDto;
import com.andjela.diplomski.entity.Authority;
import com.andjela.diplomski.entity.PasswordResetToken;
import com.andjela.diplomski.entity.RegistrationToken;
import com.andjela.diplomski.entity.User;
import com.andjela.diplomski.events.OnRegistrationCompleteEvent;
import com.andjela.diplomski.exception.*;
import com.andjela.diplomski.repository.*;
import com.andjela.diplomski.repository.auth.AuthorityRepository;
import com.andjela.diplomski.repository.auth.PasswordResetTokenRepository;
import com.andjela.diplomski.repository.auth.RegistrationTokenRepository;
import com.andjela.diplomski.security.JwtTokenProvider;
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
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final RegistrationTokenRepository registrationTokenRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    private final RegistrationTokenService registrationTokenService;


    @Value("${frontend.resetPasswordUrl}")
    private String frontendResetPasswordUrl;

    @Value("${frontend.registrationConfirmTokenUrl}")
    private String confirmTokenUiPageUrl;

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Override
    public List<UserDto> getAllUser() {
        List<UserDto> foundUsers = new ArrayList<>();
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("List of users is empty");
        }
        for (User u : users) {
            UserDto userDTO = UserMapper.MAPPER.mapToUserDTO(u);
            foundUsers.add(userDTO);
        }
        return foundUsers;
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Didn't find user with id:" + id));
        return UserMapper.MAPPER.mapToUserDTO(user);
    }

    @Override
    public UserDto updateUser(Long id, UserUpdateDto userUpdateDto) {
        User updatedUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity whit id " + id + " could not be updated"));
        updatedUser.setFirstName(userUpdateDto.getFirstName());
        updatedUser.setLastName(userUpdateDto.getLastName());
        updatedUser.setEmail(userUpdateDto.getEmail());
        updatedUser.setPassword(userUpdateDto.getPassword());
        updatedUser.setPhoneNumber(userUpdateDto.getPhoneNumber());
        updatedUser.setUpdatedAt(LocalDateTime.now());

        userRepository.save(updatedUser);

        return UserMapper.MAPPER.mapToUserDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Entity whit id " + id + " could not be deleted");
        }
    }

    @Override
    public User getUserByJwt(String jwt) throws ResourceNotFoundException {
        String email = jwtTokenProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " could not be found"));
        if (user == null) {
            throw new UserException("user not exist with email " + email);
        }
        return user;
    }
}
