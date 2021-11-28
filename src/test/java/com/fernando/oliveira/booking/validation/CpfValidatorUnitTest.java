package com.fernando.oliveira.booking.validation;

import org.junit.jupiter.api.Test;

public class CpfValidatorUnitTest {

    @Test
    void givenValidCpfWithSpecialCharactersWhenValidateThenReturnValidatedCpf(){
        CpfValidator cpfValidator = new CpfValidator();
        String cpf = "584.616.219-30";

    }
}
