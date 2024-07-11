package com.andjela.diplomski.dto.user;

import com.andjela.diplomski.dto.address.AddressDto;
import com.andjela.diplomski.entity.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<AddressDto> address;
    private List<Authority> roles;

}
