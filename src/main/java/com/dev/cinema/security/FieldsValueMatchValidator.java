package com.dev.cinema.security;

import com.dev.cinema.model.dto.UserRegistrationDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsValueMatchValidator
        implements ConstraintValidator<PasswordMatch, UserRegistrationDto> {
    public boolean isValid(UserRegistrationDto user,
                           ConstraintValidatorContext context) {
        return user.getPassword().equals(user.getRepeatPassword());
    }
}
