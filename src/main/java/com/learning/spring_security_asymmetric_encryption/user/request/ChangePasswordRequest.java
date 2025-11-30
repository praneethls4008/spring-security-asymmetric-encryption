package com.learning.spring_security_asymmetric_encryption.user.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequest {
    private  String currentPassword;
    private  String newPassword;
    private  String confirmNewPassword;
}
