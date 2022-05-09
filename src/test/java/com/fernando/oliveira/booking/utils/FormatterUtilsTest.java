package com.fernando.oliveira.booking.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormatterUtilsTest {


    @Test
    void givenValidCpfWhenFormatThenReturnCpfFormatted(){
        String input = "12345678900";
        String result = FormatterUtils.formatCpf(input);

        assertEquals("123.456.789-00", result);
    }

    @Test
    void givenTextWhenRemoveAccentThenReturnTextWithoutAccent(){
        String input = "Criação";
        String result = FormatterUtils.removeAccent(input);

        assertEquals("Criacao", result);
    }

    @Test
    void givenPrefixAndNumberPhoneWhenFormatThenReturnPhoneFormatted(){
        Integer prefix = 11;
        String numberPhone = "988887777";

        String result = FormatterUtils.formatNumberPhoneWithPrefix(prefix, numberPhone);

        assertEquals("(11) 98888-7777", result);
    }

    @Test
    void givenDocumentNullWhenFormatCpfThenReturnEmptyValue(){

        String result = FormatterUtils.formatCpf(null);

        assertEquals("", result);
    }

    @Test
    void givenEmptyDocumentWhenFormatCpfThenReturnEmptyValue(){

        String result = FormatterUtils.formatCpf("");

        assertEquals("", result);
    }

    @Test
    void givenBigDecimalValueWhenFormatThenReturnCurrencyValue(){
        BigDecimal value = BigDecimal.valueOf(1500.00);
        String result = FormatterUtils.formatCurrencyValue(value);

        assertEquals("R$ 1.500,00", result);
    }

    @Test
    void givenLocalDateWhenFormatThenReturnLocalDateFormatted(){
        LocalDate localDate = LocalDate.of(2021,10,05);
        String result = FormatterUtils.getLocalDateFormat(localDate);
        assertEquals("05/10/2021", result);
    }

    @Test
    void givenLocalDateTimeWhenFormatThenReturnLocalDateTimeFormatted(){
        LocalDateTime localDateTime = LocalDateTime.of(2021,10,05, 10,30 );
        String result = FormatterUtils.getLocalDateTimeFormat(localDateTime);
        assertEquals("05/10/2021 10:30:00", result);
    }

}


