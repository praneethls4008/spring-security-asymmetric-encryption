package com.learning.spring_security_asymmetric_encryption.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.management.loading.MLetContent;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND("USER_NOT_FOUND", "User not found with id %s", HttpStatus.NOT_FOUND),
    CHANGE_PASSWORD_MISMATCH("CHANGE_PASSWORD_MISMATCH", "User not found with id %s", HttpStatus.BAD_REQUEST),
    INVALID_CURRENT_PASSWORD("INVALID_CURRENT_PASSWORD", "User not found with id %s", HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_DEACTIVATED("ACCOUNT_ALREADY_DEACTIVATED", "User account is already deactivated", HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_ACTIVATED("ACCOUNT_ALREADY_ACTIVATED", "User account is already activated", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", "Email is already present", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_ALREADY_EXISTS("PHONE_NUMBER_ALREADY_EXISTS", "Phone Number is already present", HttpStatus.BAD_REQUEST),
    PASSWORD_MISMATCH("PASSWORD_MISMATCH", "Password is not matching with confirm password" , HttpStatus.BAD_REQUEST),
    ERR_USER_DISABLED("ERR_USER_DISABLED","User id disabled",HttpStatus.BAD_REQUEST),
    BAD_CREDENTIALS("BAD_CREDENTIALS","provided credentials are bad",HttpStatus.BAD_REQUEST),
    USERNAME_NOT_FOUND("USERNAME_NOT_FOUND","provided credentials are bad",HttpStatus.BAD_REQUEST),
    INTERNAL_EXCEPTION("INTERNAL_EXCEPTION","internal exception",HttpStatus.INTERNAL_SERVER_ERROR),
    ENTITY_NOT_FOUND("ENTITY_NOT_FOUND","Entity not found",HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_EXPIRED("REFRESH_TOKEN_EXPIRED", "Refresh Token is expired", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_TYPE_ERROR("REFRESH_TOKEN_TYPE_ERROR", "Provided token is not of type REFRESH_TOKEN" , HttpStatus.BAD_REQUEST),
    JWT_INVALID("JWT_INVALID", "invalid token provide", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String defaultMessage;
    private final HttpStatus status;

    ErrorCode(final String code,
              final String defaultMessage,
              final HttpStatus status){
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.status = status;
    }

}
