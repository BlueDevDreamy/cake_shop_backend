package com.andjela.diplomski.dto.address;

import com.andjela.diplomski.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Long id;
    private String streetAddress;
    private String city;
    private String zipCode;
    private UserDto user;
}
