package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.response.ReservedDateResponse;
import com.fernando.oliveira.booking.mother.BookingMother;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;


import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HomeServiceUnitTest {

    @InjectMocks
    private HomeServiceImpl homeService;

    @Mock
    private BookingServiceImpl bookingService;

    @Mock
    private LaunchServiceImpl launchService;


    @Test
    @Disabled
    void givenNextBookingsThenReturnReservedDateResponse(){
        Booking firstBooking = BookingMother.getBookingSaved01();
        Booking secondBooking = BookingMother.getBookingSaved02();

        when(bookingService.findNextBookings()).thenReturn(Arrays.asList(firstBooking, secondBooking));

        List<ReservedDateResponse> result = homeService.reservedDatesFromNextBookings();

        then(result.get(0).getYear()).isEqualTo(2020);
        then(result.get(0).getMonth()).isEqualTo(10);
        then(result.get(0).getDay()).isEqualTo(15);

        then(result.get(1).getYear()).isEqualTo(2021);
        then(result.get(1).getMonth()).isEqualTo(10);
        then(result.get(1).getDay()).isEqualTo(16);

        then(result.get(2).getYear()).isEqualTo(2021);
        then(result.get(2).getMonth()).isEqualTo(10);
        then(result.get(2).getDay()).isEqualTo(17);

        then(result.get(3).getYear()).isEqualTo(2021);
        then(result.get(3).getMonth()).isEqualTo(10);
        then(result.get(3).getDay()).isEqualTo(18);

        then(result.get(4).getYear()).isEqualTo(2021);
        then(result.get(4).getMonth()).isEqualTo(10);
        then(result.get(4).getDay()).isEqualTo(19);

        then(result.get(5).getYear()).isEqualTo(2021);
        then(result.get(5).getMonth()).isEqualTo(10);
        then(result.get(5).getDay()).isEqualTo(20);

        then(result.get(6).getYear()).isEqualTo(2021);
        then(result.get(6).getMonth()).isEqualTo(10);
        then(result.get(6).getDay()).isEqualTo(21);

        then(result.get(7).getYear()).isEqualTo(2021);
        then(result.get(7).getMonth()).isEqualTo(10);
        then(result.get(7).getDay()).isEqualTo(22);

        then(result.get(8).getYear()).isEqualTo(2021);
        then(result.get(8).getMonth()).isEqualTo(10);
        then(result.get(8).getDay()).isEqualTo(23);

        then(result.get(9).getYear()).isEqualTo(2021);
        then(result.get(9).getMonth()).isEqualTo(10);
        then(result.get(9).getDay()).isEqualTo(24);

        then(result.get(10).getYear()).isEqualTo(2021);
        then(result.get(10).getMonth()).isEqualTo(10);
        then(result.get(10).getDay()).isEqualTo(25);


    }


}
