package com.credit.card.app.validation.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LuhnValidatorTest {

    private ConstraintValidatorContextImpl  constraintValidatorContextImpl;

    @BeforeEach
    void setUp() {
        constraintValidatorContextImpl =  new ConstraintValidatorContextImpl(null, PathImpl.createRootPath(),
                null, "");
    }

    @Test
    public void shoud_return_true() {
        LuhnValidator luhnValidator = new LuhnValidator();
        assertTrue(luhnValidator.isValid("012850003580200",
                constraintValidatorContextImpl));
    }

    @Test
    public void should_return_false_for_empty_card_number() {
        LuhnValidator luhnValidator = new LuhnValidator();
        assertFalse(luhnValidator.isValid("", constraintValidatorContextImpl));
    }

    @Test
    public void should_return_false_for_non_digit_card_number() {
        LuhnValidator luhnValidator = new LuhnValidator();
        assertFalse(luhnValidator.isValid("s93sdf",
                constraintValidatorContextImpl));
    }

    @Test
    public void should_return_false_for_null_card_number() {
        LuhnValidator luhnValidator = new LuhnValidator();
        assertFalse(luhnValidator.isValid(null,
                constraintValidatorContextImpl));
    }

    @Test
    public void should_return_false_invalid_card_number() {
        LuhnValidator luhnValidator = new LuhnValidator();
        assertFalse(luhnValidator.isValid("71927398713",
                constraintValidatorContextImpl));
    }



    @Test
    public void should_return_true_card_number_end_with_space() {
        LuhnValidator luhnValidator = new LuhnValidator();
        assertTrue(luhnValidator.isValid("79927398713 ",
                constraintValidatorContextImpl));
    }
}

