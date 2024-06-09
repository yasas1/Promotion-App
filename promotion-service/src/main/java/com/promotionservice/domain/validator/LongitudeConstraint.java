package com.promotionservice.domain.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LongitudeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LongitudeConstraint {
    String message() default "Invalid longitude. Longitude should be between -180 and 180.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
