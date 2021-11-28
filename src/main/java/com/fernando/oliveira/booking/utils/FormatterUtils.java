package com.fernando.oliveira.booking.utils;

import com.fernando.oliveira.booking.exception.TravelerInvalidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

@Slf4j
@Component
public class FormatterUtils {

    private static final String CPF_MASK= "###.###.###-##";
    private static final String PHONE_MASK="#####-####";

    private FormatterUtils(){

    }

    public static String formatCpf(String cpf){
        try {
            String cpfFormatted = removeSpecialCharacters(cpf);
            MaskFormatter mask = new MaskFormatter(CPF_MASK);
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(cpfFormatted);
        }catch (ParseException ex){
            throw new TravelerInvalidException("Erro ao formatar CPF: " + cpf);
        }

    }


    public static String formatPhoneNumber(String phoneNumber){
        try {
            String phoneNumberFormatted = removeSpecialCharacters(phoneNumber);
            MaskFormatter mask = new MaskFormatter(PHONE_MASK);
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(phoneNumberFormatted);
        }catch (ParseException ex){
            throw new TravelerInvalidException("Erro ao formatar telefone: " + phoneNumber);
        }

    }

    public static String removeSpecialCharacters(String value) {
        value = value.replace(".", "");
        value = value.replace("-", "").trim();
        return value;

    }
}
