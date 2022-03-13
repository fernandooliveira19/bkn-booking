package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.response.HomeResponse;
import com.fernando.oliveira.booking.mother.BookingMother;
import com.fernando.oliveira.booking.mother.LaunchMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class HomeServiceUnitTest {

    @InjectMocks
    private HomeServiceImpl homeService;

    @Mock
    private BookingServiceImpl bookingService;

    @Mock
    private LaunchServiceImpl launchService;

    @Test
    public void givenWhenCallHomeDetailsThenReturnListNextLaunchsAndBookings(){
        Booking firstBooking = BookingMother.getFirstBookingSaved();

        Launch firstLaunch = LaunchMother.getFirstLaunchFromFirstBooking();
        Launch secondLaunch = LaunchMother.getSecondLaunchFromFirstBooking();
        Launch thirdLaunch = LaunchMother.getThirdLaunchFromFirstBooking();


        Mockito.when(bookingService.findNextBookings()).thenReturn(Arrays.asList(firstBooking));
        Mockito.when(launchService.findNextLaunchs()).thenReturn(Arrays.asList(firstLaunch, secondLaunch,thirdLaunch));

        HomeResponse result = homeService.getHomeResponse();

        assertEquals(1, result.getBookingHomeResponses().size());

        assertEquals(10L ,result.getBookingHomeResponses().get(0).getBookingId() );
        assertEquals(BigDecimal.valueOf(500) ,result.getBookingHomeResponses().get(0).getAmountPending());
        assertEquals(BigDecimal.valueOf(1500) ,result.getBookingHomeResponses().get(0).getAmountTotal());
        assertEquals(LocalDateTime.of(2021, Month.OCTOBER, 15,12,30,0),result.getBookingHomeResponses().get(0).getCheckIn() );
        assertEquals(LocalDateTime.of(2021, Month.OCTOBER, 20,18,30,0),result.getBookingHomeResponses().get(0).getCheckOut() );
        assertEquals("First Traveler",result.getBookingHomeResponses().get(0).getTravelerName());

        assertEquals(2,result.getLaunchHomeResponses().size());

        assertEquals(10L,result.getLaunchHomeResponses().get(0).getBookingId());
        assertEquals(BigDecimal.valueOf(300.0),result.getLaunchHomeResponses().get(0).getAmount());
        assertEquals("First Traveler",result.getLaunchHomeResponses().get(0).getTravelerName());
        assertEquals(LocalDateTime.of(2021, Month.OCTOBER, 15,12,30,0),result.getLaunchHomeResponses().get(0).getCheckIn());
        assertEquals("Em atraso",result.getLaunchHomeResponses().get(0).getStatus());
        assertEquals(LocalDate.of(2021, Month.OCTOBER, 10),result.getLaunchHomeResponses().get(0).getScheduleDate());

        assertEquals(10L,result.getLaunchHomeResponses().get(1).getBookingId());
        assertEquals(BigDecimal.valueOf(200.0),result.getLaunchHomeResponses().get(1).getAmount());
        assertEquals("First Traveler",result.getLaunchHomeResponses().get(1).getTravelerName());
        assertEquals(LocalDateTime.of(2021, Month.OCTOBER, 15,12,30,0),result.getLaunchHomeResponses().get(1).getCheckIn());
        assertEquals("Em atraso",result.getLaunchHomeResponses().get(1).getStatus());
        assertEquals(LocalDate.of(2021, Month.OCTOBER, 15),result.getLaunchHomeResponses().get(1).getScheduleDate());

    }
}
