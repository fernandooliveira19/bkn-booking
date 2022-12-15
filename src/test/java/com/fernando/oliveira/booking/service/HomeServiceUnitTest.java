package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.dto.HomeDto;
import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.response.ReservedDateResponse;
import com.fernando.oliveira.booking.mother.BookingMother;
import com.fernando.oliveira.booking.mother.LaunchMother;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
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

    }

    @Test
    void shouldReturnHomeDtoDetails(){

        when(launchService.findNextLaunches()).thenReturn(Arrays.asList(LaunchMother.getBooking02Launch03(), LaunchMother.getBooking03Launch01(),LaunchMother.getBooking03Launch02(), LaunchMother.getBooking04Launch01()));
        when(bookingService.findNextBookings()).thenReturn(Arrays.asList(BookingMother.getBookingSaved02(), BookingMother.getBookingSaved03(),BookingMother.getBookingSaved04(), BookingMother.getBookingSaved06()));

        HomeDto result = homeService.getHomeDetails();

        then(result.getHomeLaunch().getAmountTotal()).isEqualTo(BigDecimal.valueOf(5200.0));

        then(result.getHomeLaunch().getDirectOverdueAmount()).isEqualTo(BigDecimal.valueOf(3000.0));
        then(result.getHomeLaunch().getDirectOverdueQuantity()).isEqualTo(3);

        then(result.getHomeLaunch().getDirectToReceiveAmount()).isEqualByComparingTo(BigDecimal.valueOf(0.0));
        then(result.getHomeLaunch().getDirectToReceiveQuantity()).isEqualTo(0);
        then(result.getHomeLaunch().getDirectOverdueSubTotal()).isEqualByComparingTo(BigDecimal.valueOf(3000.0));


        then(result.getHomeLaunch().getSiteQuantity()).isEqualTo(1);
        then(result.getHomeLaunch().getSiteAmount()).isEqualTo(BigDecimal.valueOf(2200.0));

        then(result.getHomeBooking().getBookingReserved()).isEqualTo(3);
        then(result.getHomeBooking().getBookingPreReserved()).isEqualTo(1);

        then(result.getHomeBooking().getBookingPaid()).isEqualTo(1);
        then(result.getHomeBooking().getBookingPending()).isEqualTo(2);

        then(result.getHomeBooking().getBookingSite()).isEqualTo(1);
        then(result.getHomeBooking().getBookingDirect()).isEqualTo(3);
        then(result.getHomeBooking().getBookingTotal()).isEqualTo(4);

    }


}
