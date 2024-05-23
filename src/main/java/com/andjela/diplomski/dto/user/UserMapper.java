package com.andjela.diplomski.dto.user;

import com.andjela.diplomski.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto mapToUserDTO(User user);
    User mapToUser(UserDto userDTO);

    UserUpdateDto mapToUserUpdateDTO(User user);
    User mapUpdateToUser(UserUpdateDto updateDTO);

    UserCreateDto mapToUserCreateDTO(User user);
    User mapCreateToUser(UserCreateDto userCreateDTO);
}

