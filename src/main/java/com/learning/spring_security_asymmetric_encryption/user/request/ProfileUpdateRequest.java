package com.learning.spring_security_asymmetric_encryption.user.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileUpdateRequest {
    private  String firstName;
    private  String lastName;
    private LocalDate dateOfBirth;

}
