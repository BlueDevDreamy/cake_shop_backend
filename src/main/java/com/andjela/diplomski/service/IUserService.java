package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.user.UserDto;
import com.andjela.diplomski.entity.User;
import com.andjela.diplomski.exception.ResourceNotFoundException;

import java.util.List;

public interface IUserService {

    List<UserDto> getAllUser();

    UserDto getUserById(Long id);

    void deleteUser(Long id);

    User getUserByJwt(String jwt) throws ResourceNotFoundException;
}
