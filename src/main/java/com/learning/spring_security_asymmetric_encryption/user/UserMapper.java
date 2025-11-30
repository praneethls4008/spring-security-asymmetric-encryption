package com.learning.spring_security_asymmetric_encryption.user;

import com.learning.spring_security_asymmetric_encryption.user.request.ProfileUpdateRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
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
}
