package com.learning.spring_security_asymmetric_encryption.auth.request;

import com.learning.spring_security_asymmetric_encryption.validation.NonDisposableEmail;
import com.learning.spring_security_asymmetric_encryption.validation.PasswordMatches;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@PasswordMatches(
        passwordField = "password",
        confirmPasswordField = "confirmPassword",
        message = "VALIDATION.REGISTRATION.PASSWORD.MISMATCH"
)
public class RegistrationRequest {

    @NotBlank(message = "VALIDATION.REGISTRATION.FIRSTNAME.NOT_BLANK")
    @Size(
            min = 1,
            max = 10,
            message = "VALIDATION.REGISTRATION.FIRSTNAME.SIZE"
    )
    @Pattern(
            regexp = "^[A-Z].*$",
            message = "VALIDATION.REGISTRATION.FIRSTNAME.PATTERN"

    )
    private String firstName;

    @NotBlank(message = "VALIDATION.REGISTRATION.LASTNAME.NOT_BLANK")
    @Size(
            max = 10,
            message = "VALIDATION.REGISTRATION.LASTNAME.SIZE"
    )
    @Pattern(
            regexp = "^[A-Z].*$",
            message = "VALIDATION.REGISTRATION.LASTNAME.PATTERN"

    )
    private String lastName;

    @NotBlank(message = "VALIDATION.REGISTRATION.EMAIL.NOT_BLANK")
    @Email(message = "VALIDATION.REGISTRATION.EMAIL.FORMAT")
    @Schema(example = "something@any.com")
    @NonDisposableEmail(message = "VALIDATION.REGISTRATION.EMAIL.DISPOSABLE")
    private String email;

    @NotBlank(message = "VALIDATION.REGISTRATION.PHONE.NOT_BLANK")
    @Pattern(
            regexp = "^\\+?[0-9]{10,13}$",
            message = "VALIDATION.REGISTRATION.PHONE.FORMAT"
    )
    @Schema(example = "+919700000000")
    private String phoneNumber;

    @NotBlank(message = "VALIDATION.REGISTRATION.PASSWORD.NOT_BLANK")
    @Size(
            min = 8,
            max = 72,
            message = "VALIDATION.REGISTRATION.PASSWORD.SIZE"
    )
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).+$",
            message = "VALIDATION.REGISTRATION.PASSWORD.WEAK"

    )
    @Schema(example = "<PASSWORD>")
    private String password;

    @NotBlank(message = "VALIDATION.REGISTRATION.CONFIRM_PASSWORD.NOT_BLANK")
    @Size(
            min = 8,
            max = 72,
            message = "VALIDATION.REGISTRATION.CONFIRM_PASSWORD.SIZE"
    )
    @Schema(example = "<PASSWORD>")
    private String confirmPassword;

}
