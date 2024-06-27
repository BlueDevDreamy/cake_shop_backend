package com.andjela.diplomski.dto.user;

import com.andjela.diplomski.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "address", source = "addresses")
    UserDto mapToUserDTO(User user);

    @Mapping(target = "address", source = "addresses")
    User mapToUser(UserDto userDTO);

}

