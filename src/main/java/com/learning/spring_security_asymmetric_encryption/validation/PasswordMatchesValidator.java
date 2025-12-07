package com.learning.spring_security_asymmetric_encryption.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    private String passwordField;
    private String confirmPasswordField;
    private String message;

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        this.passwordField = constraintAnnotation.passwordField();
        this.confirmPasswordField = constraintAnnotation.confirmPasswordField();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Field password = value.getClass().getDeclaredField(passwordField);
            Field confirmPassword = value.getClass().getDeclaredField(confirmPasswordField);

            password.setAccessible(true);
            confirmPassword.setAccessible(true);

            Object pass = password.get(value);
            Object confirm = confirmPassword.get(value);

            if (pass == null || confirm == null) {
                return true; // @NotBlank handles nulls separately
            }

            boolean matched = pass.equals(confirm);
            if (!matched) {
                context.disableDefaultConstraintViolation();
                context
                        .buildConstraintViolationWithTemplate(message)
                        .addPropertyNode(confirmPasswordField)
                        .addConstraintViolation();
            }

            return matched;

        } catch (Exception e) {
            return false;
        }
    }
}