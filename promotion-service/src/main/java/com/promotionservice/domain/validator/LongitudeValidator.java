package com.promotionservice.domain.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LongitudeValidator implements ConstraintValidator<LongitudeConstraint, String> {
    @Override
    public boolean isValid(String lon, ConstraintValidatorContext constraintValidatorContext) {
        try {
            double longitude = Double.parseDouble(lon);
            return longitude >= -180.0D && longitude <= 180.0D;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
