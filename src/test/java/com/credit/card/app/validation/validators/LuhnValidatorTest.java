package com.credit.card.app.validation.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

public class LuhnValidatorTest {

    private ConstraintValidatorContext  constraintValidatorContext;
    private LuhnValidator luhnValidator;
    
    @BeforeEach
    void setUp() {
        constraintValidatorContext =  mock(ConstraintValidatorContext.class);
         luhnValidator = new LuhnValidator();
    }

    @Test
    public void should_return_true() {
       
        assertTrue(luhnValidator.isValid("012850003580200",
                constraintValidatorContext));
    }

    @Test
    public void should_return_false_for_empty_card_number() {
        assertFalse(luhnValidator.isValid("", constraintValidatorContext));
    }

    @Test
    public void should_return_false_for_non_digit_card_number() {
        doNothing().when(constraintValidatorContext).disableDefaultConstraintViolation();
        ConstraintValidatorContext.ConstraintViolationBuilder builder= mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(constraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(builder);
        assertFalse(luhnValidator.isValid("s534534593sdf",
                constraintValidatorContext));
    }

    @Test
    public void should_return_false_for_null_card_number() {
        assertFalse(luhnValidator.isValid(null,
                constraintValidatorContext));
    }

    @Test
    public void should_return_false_invalid_card_number() {
       doNothing().when(constraintValidatorContext).disableDefaultConstraintViolation();
       ConstraintValidatorContext.ConstraintViolationBuilder builder= mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
       when(constraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(builder);
        assertFalse(luhnValidator.isValid("71927398713",
                constraintValidatorContext));
    }



    @Test
    public void should_return_true_card_number_end_with_space() {
        assertTrue(luhnValidator.isValid("79927398713 ",
                constraintValidatorContext));
    }
}

