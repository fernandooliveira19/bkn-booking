package com.fernando.oliveira.booking.builder;

import com.fernando.oliveira.booking.domain.dto.ContractRequestDto;
import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.mother.BookingMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ContractRequestDtoBuilderTest {

    @InjectMocks
    private ContractRequestDtoBuilder requestContractDtoBuilder;

    @Test
    void givenBookingWhenBuilderRequestThenReturnContractName(){
        Booking booking = BookingMother.getFirstBookingSaved();

        ContractRequestDto result = requestContractDtoBuilder.getRequestContractDto(booking);

        assertEquals("contrato_joao_2021-10-15.pdf", result.getContractName());

    }

    @Test
    void givenBookingWhenBuilderRequestThenReturnTravelerDetails(){
        Booking booking = BookingMother.getFirstBookingSaved();

        ContractRequestDto result = requestContractDtoBuilder.getRequestContractDto(booking);

        assertEquals("João da Silva", result.getTravelerName());
        assertEquals("500.428.067-39", result.getTravelerDocument());
        assertEquals("joao.silva@gmail.com", result.getTravelerEmail());
        assertEquals("(11) 98888-7777", result.getTravelerPhone());

    }

    @Test
    void givenBookingWhenBuilderRequestThenReturnRentDetails(){
        Booking booking = BookingMother.getFirstBookingSaved();

        ContractRequestDto result = requestContractDtoBuilder.getRequestContractDto(booking);
        LocalDateTime.of(2021, Month.OCTOBER, 15,12,30,0);
        assertEquals("início: 15/10/2021 após 12:30", result.getDescriptionCheckIn());
        assertEquals("término: 20/10/2021 até 18:30", result.getDescriptionCheckOut());
    }

}
