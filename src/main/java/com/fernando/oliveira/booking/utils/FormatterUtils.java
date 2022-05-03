package com.fernando.oliveira.booking.utils;

import com.fernando.oliveira.booking.exception.TravelerInvalidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.text.MaskFormatter;
import java.text.Normalizer;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

@Slf4j
@Component
public class FormatterUtils {

    private static final String CPF_MASK= "###.###.###-##";
    private static final String PHONE_MASK="#####-####";
    private static final String PHONE_MASK_WITH_PREFIX="(##) #####-####";

    private FormatterUtils(){

    }

    public static String formatCpf(String cpf){
        if(cpf == null || cpf.isBlank()){
            return "";
        }
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

    public static String removeAccent(String value){
        String nfdNormalizedString = Normalizer.normalize(value, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static String getLocalDateFormat(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return date.format(formatter);
    }


    public static String formatNumberPhoneWithPrefix(Integer prefix, String numberPhone) {
        String phone = String.valueOf(prefix).concat(numberPhone);
        try {
            String phoneNumberFormatted = removeSpecialCharacters(phone);
            MaskFormatter mask = new MaskFormatter(PHONE_MASK_WITH_PREFIX);
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(phoneNumberFormatted);
        }catch (ParseException ex){
            throw new TravelerInvalidException("Erro ao formatar telefone: " + phone);
        }
    }
}
