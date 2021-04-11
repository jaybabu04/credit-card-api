package com.credit.card.app.validation.validators;


import com.credit.card.app.validation.Luhn;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class LuhnValidator implements ConstraintValidator<Luhn, String> {

    final String regex = "[0-9]+";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValidCard = false;

        if(value==null || value.isEmpty() ){
            return isValidCard;
        }
        value = value.trim();
         if(!value.matches(regex)  || !isValidCreditCardNumber(value)){
          context.disableDefaultConstraintViolation();
          context.buildConstraintViolationWithTemplate("{invalid.card.no}").addConstraintViolation();
          return isValidCard;
        }
      return true;
    }

    private boolean isValidCreditCardNumber(String cardNumber) {
        int[] cardIntArray=new int[cardNumber.length()];

        for(int i=0;i<cardNumber.length();i++) {
            cardIntArray[i] =  Character.getNumericValue(cardNumber.charAt(i));
        }

        for(int i=cardIntArray.length-2;i>=0;i=i-2)
        {
            int num = cardIntArray[i];
            num = num * 2;  // step 1
            if(num>9){
                num = num%10 + num/10;  // step 2
            }
            cardIntArray[i]=num;
        }
        int sum = Arrays.stream(cardIntArray).sum();
        return sum%10==0;
    }

}
