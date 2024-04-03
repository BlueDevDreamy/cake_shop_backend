package com.andjela.diplomski.dto.auth;

import com.andjela.diplomski.common.AuthorityPermissionEnum;
import com.andjela.diplomski.common.UserType;
import com.andjela.diplomski.dto.user.UserData;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterClientDto implements UserData {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String phoneNumber;

    @Override
    public String getUserType() {
        return UserType.EXTERNAL.name();
    }
    @Override
    public List<String> getAuthorities() {
        return List.of(AuthorityPermissionEnum.CLIENT.name());
    }
}
