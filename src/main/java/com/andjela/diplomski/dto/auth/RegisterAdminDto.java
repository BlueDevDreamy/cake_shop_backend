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
public class RegisterAdminDto implements UserData {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String phoneNumber;

    private String password;
    @Override
    public String getUserType() {
        return UserType.SYSTEM.name();
    }
    @Override
    public List<String> getAuthorities() {
        return List.of(AuthorityPermissionEnum.ADMIN.name());
    }
}
