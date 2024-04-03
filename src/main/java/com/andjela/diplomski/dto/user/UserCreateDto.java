package com.andjela.diplomski.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UUID uuid;
}
