package com.promotionservice.domain.validator;

import com.promotionservice.domain.type.UserType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class UserTypeSubSetValidator implements ConstraintValidator<UserTypeSubset, UserType> {
    private UserType[] subset;

    @Override
    public void initialize(UserTypeSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(UserType value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}
