package com.learning.spring_security_asymmetric_encryption.exception;

import ch.qos.logback.core.spi.ErrorCodes;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final ErrorCode errorCode;
    private final Object[] args;

    public BusinessException(final ErrorCode errorCode, final Object... args){
        super(getFormatterMessage(errorCode, args));
        this.errorCode = errorCode;
        this.args = args;
    }

    private static String getFormatterMessage(final ErrorCode errorCode, final Object[] args){
        final String message = errorCode.getDefaultMessage();

        if (args != null && args.length > 0 && message.contains("%")) {
            try {
                return String.format(message, args);
            } catch (Exception e) {
                // Fallback if formatting fails
                return message;
            }
        }
        return message;

    }
}
