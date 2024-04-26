package com.andjela.diplomski.service;

import com.andjela.diplomski.common.TokenStatus;
import com.andjela.diplomski.common.UserType;
import com.andjela.diplomski.dto.auth.*;
import com.andjela.diplomski.dto.user.UserDto;
import com.andjela.diplomski.dto.user.UserData;
import com.andjela.diplomski.dto.user.UserMapper;
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
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
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
        if (users.isEmpty()){
            throw new ResourceNotFoundException("List of users is empty");
        }
        for (User u : users){
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
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = UserMapper.MAPPER.mapToUser(userDto);
        User updatedUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity whit id " + id + " could not be updated"));
        User savedUser = new User();

        boolean isChanged = false;

        if (userDto.getEmail() == null || !userDto.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            throw new DataNotValidException("Email is not in a valid format");
        } else  {
            isChanged = true;
            updatedUser.setEmail(user.getEmail());
        }
        if (userDto.getPassword() == null || userDto.getPassword().trim().isEmpty() || !userDto.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
            throw new DataNotValidException("Password is not in a valid format");
        } else  {
            isChanged = true;
            updatedUser.setPassword(user.getPassword());
        }
        if (userDto.getFirstName() == null || userDto.getFirstName().trim().isEmpty() || !userDto.getFirstName().matches("^[A-Z][a-z ]*$")) {
            throw new DataNotValidException("First name is not in a valid format");
        } else {
            isChanged = true;
            updatedUser.setFirstName(user.getFirstName());
        }
        if (userDto.getLastName() == null || userDto.getLastName().trim().isEmpty() || !userDto.getLastName().matches("^[A-Z][a-z ]*$")) {
            throw new DataNotValidException("Last name is not in a valid format");
        } else  {
            isChanged = true;
            updatedUser.setLastName(user.getLastName());
        }
        if (userDto.getPhoneNumber() == null || userDto.getPhoneNumber().trim().isEmpty() || !userDto.getPhoneNumber().matches("^[0-9 ]+$")) {
            throw new DataNotValidException("Phone number is not in a valid format");
        } else  {
            isChanged = true;
            updatedUser.setPhoneNumber(user.getPhoneNumber());
        }

        if (isChanged){
            updatedUser.setUpdatedAt(LocalDateTime.now());
            savedUser = userRepository.save(updatedUser);
        }
        return UserMapper.MAPPER.mapToUserDTO(savedUser);
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
        return null;
    }
}
