package com.learning.spring_security_asymmetric_encryption.auth.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshRequest {

    private String refreshToken;
}
