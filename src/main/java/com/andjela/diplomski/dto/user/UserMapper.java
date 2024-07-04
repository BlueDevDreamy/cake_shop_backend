package com.andjela.diplomski.dto.user;

import com.andjela.diplomski.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "address", source = "addresses")
    @Mapping(target = "roles", source = "authorities")
    UserDto mapToUserDTO(User user);

    @Mapping(target = "addresses", source = "address")
    @Mapping(target = "authorities", source = "roles")
    User mapToUser(UserDto userDTO);

}

