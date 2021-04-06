package com.credit.card.app.validation;

import com.credit.card.app.validation.validators.LuhnValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LuhnValidator.class)
public @interface Luhn {
    String message() default "{card.number.required}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
