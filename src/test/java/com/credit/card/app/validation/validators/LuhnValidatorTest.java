package com.credit.card.app.validation.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//import org.hibernate.validator.internal.engine.constraintvalidation.constraintValidatorContext;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintValidatorContext;

public class LuhnValidatorTest {

    private ConstraintValidatorContext  constraintValidatorContext;

    @BeforeEach
    void setUp() {
        constraintValidatorContext =  mock(ConstraintValidatorContext.class);
    }

    @Test
    public void shoud_return_true() {
        LuhnValidator luhnValidator = new LuhnValidator();
        assertTrue(luhnValidator.isValid("012850003580200",
                constraintValidatorContext));
    }

    @Test
    public void should_return_false_for_empty_card_number() {
        LuhnValidator luhnValidator = new LuhnValidator();
        assertFalse(luhnValidator.isValid("", constraintValidatorContext));
    }

    @Test
    public void should_return_false_for_non_digit_card_number() {
        LuhnValidator luhnValidator = new LuhnValidator();
        doNothing().when(constraintValidatorContext).disableDefaultConstraintViolation();
        ConstraintValidatorContext.ConstraintViolationBuilder builder= mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(constraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(builder);
        assertFalse(luhnValidator.isValid("s534534593sdf",
                constraintValidatorContext));
    }

    @Test
    public void should_return_false_for_null_card_number() {
        LuhnValidator luhnValidator = new LuhnValidator();
        assertFalse(luhnValidator.isValid(null,
                constraintValidatorContext));
    }

    @Test
    public void should_return_false_invalid_card_number() {
        LuhnValidator luhnValidator = new LuhnValidator();
       doNothing().when(constraintValidatorContext).disableDefaultConstraintViolation();
       ConstraintValidatorContext.ConstraintViolationBuilder builder= mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
       when(constraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(builder);
        assertFalse(luhnValidator.isValid("71927398713",
                constraintValidatorContext));
    }



    @Test
    public void should_return_true_card_number_end_with_space() {
        LuhnValidator luhnValidator = new LuhnValidator();
        assertTrue(luhnValidator.isValid("79927398713 ",
                constraintValidatorContext));
    }
}

