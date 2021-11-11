package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentTypeEnum;
import com.fernando.oliveira.booking.mother.BookingMother;
import com.fernando.oliveira.booking.mother.LaunchMother;
import com.fernando.oliveira.booking.repository.BookingRepository;
import com.fernando.oliveira.booking.repository.LaunchRepository;
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

import static com.fernando.oliveira.booking.mother.BookingMother.getBookingToSave;
import static com.fernando.oliveira.booking.mother.LaunchMother.getLaunchToSave;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BookingServiceUnitTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private LaunchServiceImpl launchService;

    @Mock
    private LaunchRepository launchRepository;


    @Test
    void givenValidRequestWhenCreateBookingThenCreateBookingReservedWithPending(){
        LocalDateTime checkIn = LocalDateTime.of(2021, Month.OCTOBER,8,10,0);
        LocalDateTime checkOut = LocalDateTime.of(2021, Month.DECEMBER,16,18,0);
        Long travelerId = 1L;
        BigDecimal totalAmount = BigDecimal.valueOf(1500.0);
        Integer adults = 2;
        Integer children = 3;

        Launch firstLaunch = getLaunchToSave(BigDecimal.valueOf(1000.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PAID, LocalDate.of(2021, 10,10), LocalDate.of(2021,10,10) );
        Launch secondLaunch = getLaunchToSave(BigDecimal.valueOf(300.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PENDING, LocalDate.of(2021, 11,10), null );
        Launch thirdLaunch = getLaunchToSave(BigDecimal.valueOf(200.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PENDING, LocalDate.of(2021, 12,10), null );

        Booking bookingToSave = getBookingToSave(checkIn, checkOut, totalAmount,travelerId, adults, children, Arrays.asList(firstLaunch, secondLaunch, thirdLaunch));
        Launch firstLaunchSaved = LaunchMother.getLaunchSaved(bookingToSave,firstLaunch.getAmount(), firstLaunch.getPaymentType(), firstLaunch.getPaymentStatus(), firstLaunch.getScheduleDate(), firstLaunch.getPaymentDate());
        firstLaunchSaved.setId(101L);
        Launch secondLaunchSaved = LaunchMother.getLaunchSaved(bookingToSave,secondLaunch.getAmount(), secondLaunch.getPaymentType(), secondLaunch.getPaymentStatus(), secondLaunch.getScheduleDate(), secondLaunch.getPaymentDate());
        secondLaunchSaved.setId(102L);
        Launch thirdLaunchSaved = LaunchMother.getLaunchSaved(bookingToSave,thirdLaunch.getAmount(), thirdLaunch.getPaymentType(), thirdLaunch.getPaymentStatus(), thirdLaunch.getScheduleDate(), thirdLaunch.getPaymentDate());
        thirdLaunchSaved.setId(103L);

        Booking bookingSaved = BookingMother.getBookingSaved(checkIn, checkOut, totalAmount,travelerId, adults, children, Arrays.asList(firstLaunchSaved, secondLaunchSaved, thirdLaunchSaved), BookingStatusEnum.RESERVED, PaymentStatusEnum.PENDING);

        when(bookingRepository.save(Mockito.any(Booking.class))).thenReturn(bookingSaved);
        when(launchService.createLaunch(Mockito.any(Launch.class), Mockito.any(Booking.class))).thenReturn(firstLaunchSaved);

        Booking result = bookingService.createBooking(bookingToSave);

        assertEquals(result.getId(), 1L);
        assertEquals(result.getCheckIn(), checkIn);
        assertEquals(result.getCheckOut(), checkOut);
        assertEquals(result.getTotalAmount(), totalAmount);
        assertEquals(result.getBookingStatus(), BookingStatusEnum.RESERVED);
        assertEquals(result.getPaymentStatus(), PaymentStatusEnum.PENDING);
        assertEquals(result.getAdults(), adults);
        assertEquals(result.getChildren(), children);
        assertNotNull(result.getInsertDate());
        assertNotNull(result.getLaunchs());
        assertEquals(result.getLaunchs().size(), 3);

        then(result.getLaunchs().size()).isGreaterThan(0);

    }
}
