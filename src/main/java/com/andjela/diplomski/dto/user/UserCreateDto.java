package com.andjela.diplomski.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.intellij.lang.annotations.RegExp;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    @NotBlank(message = "Email is mandatory")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Email is not in a valid format"
    )
    private String email;
    @NotBlank(message = "Password is mandatory")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "Password is not in a valid format"
    )
    private String password;
    @NotBlank(message = "First name is mandatory")
    @Pattern(
            regexp = "^[A-Z][a-z ]*$",
            message = "First name is not in a valid format"
    )
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    @Pattern(
            regexp = "^[A-Z][a-z ]*$",
            message = "Last name is not in a valid format"
    )
    private String lastName;
    @NotBlank(message = "Phone number is mandatory")
    @Pattern(
            regexp = "^[0-9 ]+$",
            message = "Phone number is not in a valid format"
    )
    private String phoneNumber;
}
