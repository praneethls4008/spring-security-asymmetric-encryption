package com.learning.spring_security_asymmetric_encryption.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)            // because we validate two fields together
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {

    String message() default "VALIDATION.REGISTRATION.PASSWORD.MISMATCH";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // names of the fields inside the class
    String passwordField() default "password";
    String confirmPasswordField() default "confirmPassword";
}
