package com.learning.spring_security_asymmetric_encryption.user;

import com.learning.spring_security_asymmetric_encryption.auth.request.RegistrationRequest;
import com.learning.spring_security_asymmetric_encryption.user.request.ProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public void mergerUserInfo(User user, ProfileUpdateRequest request) {
        if(StringUtils.isNotBlank(request.getFirstName())
            && !user.getFirstname().equals(request.getFirstName())){
            user.setFirstname(request.getFirstName());
        }
        if(StringUtils.isNotBlank(request.getLastName())
                && !user.getLastname().equals(request.getLastName())){
            user.setLastname(request.getLastName());
        }
        if(request.getDateOfBirth() != null
                && !user.getDateOfBirth().equals(request.getDateOfBirth())){
            user.setDateOfBirth(request.getDateOfBirth());
        }


    }

    public User toUser(final RegistrationRequest request) {
        return User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .locked(false)
                .credentialsExpired(false)
                .emailVerified(false)
                .phoneVerified(false)
                .build();
    }
}
