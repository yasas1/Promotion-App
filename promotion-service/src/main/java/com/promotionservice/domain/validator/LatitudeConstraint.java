package com.promotionservice.domain.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LatitudeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LatitudeConstraint {
    String message() default "Invalid latitude. Latitude should be between -90 and 90.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
