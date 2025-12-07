package com.learning.spring_security_asymmetric_encryption.user.impl;

import com.learning.spring_security_asymmetric_encryption.exception.BusinessException;
import com.learning.spring_security_asymmetric_encryption.exception.ErrorCode;
import com.learning.spring_security_asymmetric_encryption.user.User;
import com.learning.spring_security_asymmetric_encryption.user.UserMapper;
import com.learning.spring_security_asymmetric_encryption.user.UserRepository;
import com.learning.spring_security_asymmetric_encryption.user.UserService;
import com.learning.spring_security_asymmetric_encryption.user.request.ChangePasswordRequest;
import com.learning.spring_security_asymmetric_encryption.user.request.ProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return this.userRepository.findByEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with user email: " + userEmail));
    }


    @Override
    public void updateProfileInfo(final ProfileUpdateRequest request, String userId) {
        final User savedUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, userId));

        this.userMapper.mergerUserInfo(savedUser, request);
        this.userRepository.save(savedUser);
    }

    @Override
    public void changePassword(final ChangePasswordRequest request, String userId) {
        if(!request.getNewPassword().equals(request.getConfirmNewPassword())){
            throw new BusinessException(ErrorCode.CHANGE_PASSWORD_MISMATCH);
        }

        final User savedUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, userId));

        if(!this.passwordEncoder.matches(request.getCurrentPassword(),
                savedUser.getPassword())){
            throw new BusinessException(ErrorCode.INVALID_CURRENT_PASSWORD);
        }

        final String encoded = this.passwordEncoder.encode(request.getNewPassword());
        savedUser.setPassword(encoded);
        this.userRepository.save(savedUser);
    }

    @Override
    public void deactivateAccount(String userId) {
        final User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, userId));

        if(!user.isEnabled()){
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_DEACTIVATED);
        }

        user.setEnabled(false);
        this.userRepository.save(user);
    }

    @Override
    public void reactivateAccount(String userId) {
        final User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, userId));

        if(user.isEnabled()){
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_ACTIVATED);
        }

        user.setEnabled(true);
        this.userRepository.save(user);
    }

    @Override
    public void deleteAccount(String userId) {

    }


}
