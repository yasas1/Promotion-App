package com.promotionservice.domain.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LatitudeValidator implements ConstraintValidator<LatitudeConstraint, String> {
    @Override
    public boolean isValid(String lat, ConstraintValidatorContext constraintValidatorContext) {
        try {
            double latitude = Double.parseDouble(lat);
            return latitude >= -90.0D && latitude <= 90.0D;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
