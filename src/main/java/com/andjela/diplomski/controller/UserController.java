package com.andjela.diplomski.controller;

import com.andjela.diplomski.dto.user.UserDto;
import com.andjela.diplomski.dto.user.UserMapper;
import com.andjela.diplomski.entity.User;
import com.andjela.diplomski.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization") String jwt) {
        User user = userService.getUserByJwt(jwt);
        UserDto userDto = UserMapper.MAPPER.mapToUserDTO(user);
        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }
}
