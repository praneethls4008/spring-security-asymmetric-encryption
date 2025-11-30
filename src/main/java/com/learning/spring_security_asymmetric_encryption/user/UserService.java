package com.learning.spring_security_asymmetric_encryption.user;

import com.learning.spring_security_asymmetric_encryption.user.request.ChangePasswordRequest;
import com.learning.spring_security_asymmetric_encryption.user.request.ProfileUpdateRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void updateProfileInfo(final ProfileUpdateRequest request, String userId);

    void changePassword(final ChangePasswordRequest request, String userId);

    void deactivateAccount(String userId);

    void reactivateAccount(String userId);

    void deleteAccount(String userId);


}
