package com.fernando.oliveira.booking.domain.mapper;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.enums.ContractTypeEnum;
import com.fernando.oliveira.booking.domain.request.CreateBookingRequest;
import com.fernando.oliveira.booking.domain.request.CreateLaunchRequest;
import com.fernando.oliveira.booking.mother.BookingMother;
import com.fernando.oliveira.booking.mother.LaunchMother;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingMapperUnitTest {

    private final BookingMapper bookingMapper = Mappers.getMapper(BookingMapper.class);

    @Test
    public void shouldConvertCreateBookingRequestToEntity(){

        CreateBookingRequest request = BookingMother.getCreateBookingRequest();
        Booking result = bookingMapper.createRequestToEntity(request);

        assertEquals(LocalDateTime.of(2021, 10,15,12,30),result.getCheckIn());
        assertEquals(LocalDateTime.of(2021, 10,20,18,30),result.getCheckOut());
        assertEquals(BigDecimal.valueOf(1500.00), result.getAmountTotal());
        assertEquals(1L, result.getTraveler().getId());
        assertEquals(ContractTypeEnum.DIRECT, result.getContractType());
        assertEquals(2, result.getAdults());
        assertEquals(1, result.getChildren());
        assertEquals("Observation", result.getObservation());

    }

    @Test
    public void shouldConvertCreateBookingRequestWithDigitsToEntity(){
        CreateBookingRequest request = BookingMother.getCreateBookingRequest();
        request.setAmountTotal(BigDecimal.valueOf(1500.39));
        Booking result = bookingMapper.createRequestToEntity(request);

        assertEquals(LocalDateTime.of(2021, 10,15,12,30),result.getCheckIn());
        assertEquals(LocalDateTime.of(2021, 10,20,18,30),result.getCheckOut());
        assertEquals(BigDecimal.valueOf(1500.39), result.getAmountTotal());
        assertEquals(1L, result.getTraveler().getId());
        assertEquals(ContractTypeEnum.DIRECT, result.getContractType());


    }
    @Test
    public void shouldConvertCreateBookingRequestPendingToEntity(){
        CreateBookingRequest request = BookingMother.getCreateBookingRequest();
        request.setAmountTotal(BigDecimal.valueOf(1500.39));

        Booking result = bookingMapper.createRequestToEntity(request);

        assertEquals(LocalDateTime.of(2021, 10,15,12,30),result.getCheckIn());
        assertEquals(LocalDateTime.of(2021, 10,20,18,30),result.getCheckOut());
        assertEquals(BigDecimal.valueOf(1500.39), result.getAmountTotal());
        assertEquals(1L, result.getTraveler().getId());
        assertEquals(ContractTypeEnum.DIRECT, result.getContractType());


    }
}
