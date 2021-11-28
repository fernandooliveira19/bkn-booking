package com.fernando.oliveira.booking.validation;

import com.fernando.oliveira.booking.utils.FormatterUtils;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<Cpf, String> {

    private boolean required;

    @Override
    public void initialize(Cpf constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (null != value && value.length() > 0) {
            return validarCpf(value);
        } else {
            return !required;
        }
    }

    private boolean validarCpf(String value) {
        value = FormatterUtils.removeSpecialCharacters(value);
        if (value.equals("00000000000") || value.equals("11111111111") ||
                value.equals("22222222222") || value.equals("33333333333") ||
                value.equals("44444444444") || value.equals("55555555555") ||
                value.equals("66666666666") || value.equals("77777777777") ||
                value.equals("88888888888") || value.equals("99999999999")) {
            return false;
        }
        char dig10 = calculateVerifyDigit(value, 10, 9);
        char dig11 = calculateVerifyDigit(value, 11, 10);
        return (dig10 == value.charAt(9)) && (dig11 == value.charAt(10));
    }

   private static char calculateVerifyDigit(String cpf, int weight, int counter){
        int sum = 0;
        int result;
        for (int i = 0; i < counter; i++){
            sum += ((cpf.charAt(i) - 48) * weight);
            weight --;

        }
        result = 11 - (sum % 11);
        return ((result == 10) || (result == 11)) ? '0' : (char) (result + 48);
    }
}
