package com.learning.spring_security_asymmetric_encryption.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.learning.spring_security_asymmetric_encryption.auth.request.AuthenticationRequest;
import com.learning.spring_security_asymmetric_encryption.auth.request.RefreshRequest;
import com.learning.spring_security_asymmetric_encryption.auth.request.RegistrationRequest;
import com.learning.spring_security_asymmetric_encryption.auth.response.AuthenticationResponse;
import com.learning.spring_security_asymmetric_encryption.exception.BusinessException;
import com.learning.spring_security_asymmetric_encryption.exception.ErrorCode;
import com.learning.spring_security_asymmetric_encryption.role.Role;
import com.learning.spring_security_asymmetric_encryption.role.RoleRepository;
import com.learning.spring_security_asymmetric_encryption.security.JwtService;
import com.learning.spring_security_asymmetric_encryption.user.User;
import com.learning.spring_security_asymmetric_encryption.user.UserMapper;
import com.learning.spring_security_asymmetric_encryption.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService{

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        final Authentication auth = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        final User user = (User) auth.getPrincipal();
        final String accessToken = this.jwtService.generateAccessToken(user.getUsername());
        final String refreshToken = this.jwtService.generateRefreshToken(user.getUsername());
        final String tokenType = "Bearer";

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType(tokenType)
                .build();
    }

    @Override
    @Transactional
    public void register(RegistrationRequest request) {
        checkUserEmail(request.getEmail());
        checkUserPhoneNumber(request.getPhoneNumber());
        checkUserPasswords(request.getPassword(), request.getConfirmPassword());
        
        final Role userRole = this.roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Role user does not exists"));

        final List<Role> roles = List.of(userRole);

        final User user = this.userMapper.toUser(request);
        user.setRoles(roles);
        log.debug("Saving user {}", user);
        this.userRepository.save(user);

        final List<User> users = new ArrayList<>();
        users.add(user);
        userRole.setUsers(users);
        this.roleRepository.save(userRole);

    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) {
        final String newAccessToken = this.jwtService.refreshAccessToken(request.getRefreshToken());
        final String tokenType = "Bearer ";
        return AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .tokenType(tokenType)
                .build();
    }

    private void checkUserPasswords(String password, String confirmPassword) {
        if(password == null || !password.equals(confirmPassword)){
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);
        }
    }

    private void checkUserPhoneNumber(String phoneNumber) {
        final boolean phoneNumberExists = this.userRepository.existsByPhoneNumber(phoneNumber);
        if(phoneNumberExists){
            throw new BusinessException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);
        }
    }

    private void checkUserEmail(String email) {
        final boolean emailExists = this.userRepository.existsByEmailIgnoreCase(email);
        if(emailExists){
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

}
