package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.entity.Traveler;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentTypeEnum;
import com.fernando.oliveira.booking.exception.BookingException;
import com.fernando.oliveira.booking.mother.BookingMother;
import com.fernando.oliveira.booking.mother.LaunchMother;
import com.fernando.oliveira.booking.mother.TravelerMother;
import com.fernando.oliveira.booking.repository.BookingRepository;
import com.fernando.oliveira.booking.repository.LaunchRepository;
import com.fernando.oliveira.booking.repository.TravelerRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.fernando.oliveira.booking.mother.BookingMother.*;
import static com.fernando.oliveira.booking.mother.LaunchMother.getLaunchToSave;
import static com.fernando.oliveira.booking.mother.LaunchMother.getLaunchsFromFirstBooking;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @Mock
    private TravelerServiceImpl travelerService;

    @Mock
    private TravelerRepository travelerRepository;

    @Test
    void givenValidRequestWhenCreateBookingThenCreateBookingReservedWithPending(){

        LocalDateTime checkIn = LocalDateTime.of(2021,Month.JANUARY,1,10,0,0);
        LocalDateTime checkOut = LocalDateTime.of(2021,Month.JANUARY,15,18,0,0);
        BigDecimal amountTotal = BigDecimal.valueOf(1500.0);
        Traveler traveler = TravelerMother.getTravelerSaved02();
        Integer adults=4;
        Integer children=2;
        Launch launch01 = LaunchMother.getLaunchToSave(BigDecimal.valueOf(300.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PAID, LocalDate.of(2021, Month.JANUARY, 1),LocalDate.of(2021, Month.JANUARY, 2));
        Launch launch02 = LaunchMother.getLaunchToSave(BigDecimal.valueOf(200.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PAID, LocalDate.of(2021, Month.JANUARY, 5),LocalDate.of(2021, Month.JANUARY, 6));
        Launch launch03 = LaunchMother.getLaunchToSave(BigDecimal.valueOf(1000.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PAID, LocalDate.of(2021, Month.JANUARY, 8),null);

        Booking bookingToSave = getBookingToSave(checkIn,checkOut,amountTotal,traveler.getId(),adults, children, Arrays.asList(launch01, launch02, launch03), traveler );

        Booking bookingSaved = getSecondBookingSaved();


        when(travelerService.findById(anyLong())).thenReturn(TravelerMother.getTravelerSaved02());
        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingSaved);
        when(launchService.createLaunch(any(Launch.class), any(Booking.class))).thenReturn(LaunchMother.getLaunchSaved(bookingSaved,BigDecimal.valueOf(200.0),PaymentTypeEnum.PIX,PaymentStatusEnum.PAID, LocalDate.of(2021, Month.OCTOBER, 10),LocalDate.of(2021, Month.OCTOBER, 10) ));

        Booking result = bookingService.createBooking(bookingToSave);

        then(result.getBookingStatus()).isEqualTo( BookingStatusEnum.RESERVED);
        then(result.getPaymentStatus()).isEqualTo( PaymentStatusEnum.PENDING);

    }

    @Test
    void givenValidRequestWhenCreateBookingThenCreateBookingReservedPaid(){
        LocalDateTime checkIn = LocalDateTime.of(2021, Month.OCTOBER,8,10,0);
        LocalDateTime checkOut = LocalDateTime.of(2021, Month.DECEMBER,16,18,0);
        Long travelerId = 1L;
        BigDecimal totalAmount = BigDecimal.valueOf(1500.0);
        Integer adults = 2;
        Integer children = 3;

        Launch firstLaunch = getLaunchToSave(BigDecimal.valueOf(1000.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PAID, LocalDate.of(2021, 10,10), LocalDate.of(2021,10,10) );
        Launch secondLaunch = getLaunchToSave(BigDecimal.valueOf(300.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PAID, LocalDate.of(2021, 11,10), LocalDate.of(2021,10,10) );
        Launch thirdLaunch = getLaunchToSave(BigDecimal.valueOf(200.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PAID, LocalDate.of(2021, 12,10), LocalDate.of(2021,10,10) );

        Booking bookingToSave = getBookingToSave(checkIn, checkOut, totalAmount,travelerId, adults, children, Arrays.asList(firstLaunch, secondLaunch, thirdLaunch), TravelerMother.getTravelerSaved01());
        Booking bookingSaved = getFirstBookingSaved();
        bookingSaved.setBookingStatus(BookingStatusEnum.RESERVED);
        bookingSaved.setPaymentStatus(PaymentStatusEnum.PAID);
        bookingSaved.setLaunchs(Arrays.asList(firstLaunch, secondLaunch, thirdLaunch));

        when(travelerService.findById(anyLong())).thenReturn(TravelerMother.getTravelerToSaved01());
        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingSaved);
        when(launchService.createLaunch(any(Launch.class), any(Booking.class))).thenReturn(firstLaunch);

        Booking result = bookingService.createBooking(bookingToSave);

        then(result.getPaymentStatus()).isEqualTo(PaymentStatusEnum.PAID);
        then(result.getBookingStatus()).isEqualTo(BookingStatusEnum.RESERVED);

    }
    @Test
    void givenValidRequestWhenCreateBookingThenCreateBookingPreReserved(){

        LocalDateTime checkIn = LocalDateTime.of(2021, Month.OCTOBER,8,10,0);
        LocalDateTime checkOut = LocalDateTime.of(2021, Month.DECEMBER,16,18,0);
        Long travelerId = 1L;
        Traveler traveler = TravelerMother.getTravelerSaved01();
        BigDecimal totalAmount = BigDecimal.valueOf(1500.0);
        Integer adults = 2;
        Integer children = 3;

        Launch firstLaunch = getLaunchToSave(BigDecimal.valueOf(1000.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PENDING, LocalDate.of(2021, 10,10), null );
        Launch secondLaunch = getLaunchToSave(BigDecimal.valueOf(300.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PENDING, LocalDate.of(2021, 11,10), null );
        Launch thirdLaunch = getLaunchToSave(BigDecimal.valueOf(200.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PENDING, LocalDate.of(2021, 12,10), null );

        Booking bookingToSave = getBookingToSave(checkIn, checkOut, totalAmount,travelerId, adults, children, Arrays.asList(firstLaunch, secondLaunch, thirdLaunch), TravelerMother.getTravelerSaved01());
        Booking bookingSaved = BookingMother.getBookingSaved(checkIn, checkOut, totalAmount,travelerId, adults, children, Arrays.asList(firstLaunch, secondLaunch, thirdLaunch), BookingStatusEnum.PRE_RESERVED, PaymentStatusEnum.PENDING);

        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingSaved);
        when(launchService.createLaunch(any(Launch.class), any(Booking.class))).thenReturn(firstLaunch);
        when(travelerService.findById(anyLong())).thenReturn(traveler);

        Booking result = bookingService.createBooking(bookingToSave);

        then(result.getPaymentStatus()).isEqualTo(PaymentStatusEnum.PENDING);
        then(result.getBookingStatus()).isEqualTo(BookingStatusEnum.PRE_RESERVED);

    }

    @Test
    void givenValidParamWhenFindBookingThenReturnBooking(){

        LocalDateTime checkIn = LocalDateTime.of(2021, Month.OCTOBER,8,10,0);
        LocalDateTime checkOut = LocalDateTime.of(2021, Month.DECEMBER,16,18,0);
        Long travelerId = 1L;
        BigDecimal totalAmount = BigDecimal.valueOf(1500.0);
        Integer adults = 2;
        Integer children = 3;

        Launch firstLaunch = getLaunchToSave(BigDecimal.valueOf(1000.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PENDING, LocalDate.of(2021, 10,10), null );
        Launch secondLaunch = getLaunchToSave(BigDecimal.valueOf(300.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PENDING, LocalDate.of(2021, 11,10), null );
        Launch thirdLaunch = getLaunchToSave(BigDecimal.valueOf(200.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PENDING, LocalDate.of(2021, 12,10), null );

        Booking bookingSaved = BookingMother.getBookingSaved(checkIn, checkOut, totalAmount,travelerId, adults, children, Arrays.asList(firstLaunch, secondLaunch, thirdLaunch), BookingStatusEnum.PRE_RESERVED, PaymentStatusEnum.PENDING);

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(bookingSaved));

        Booking result = bookingService.findById(1L);

        then(result.getId()).isNotNull();
        then(result.getInsertDate()).isNotNull();
    }

    @Test
    @Disabled
    void givenValidRequestWhenUpdateBookingThenReturnBookingUpdated(){
        LocalDateTime checkIn = LocalDateTime.of(2021, Month.OCTOBER,15,12,30);
        LocalDateTime checkOut = LocalDateTime.of(2021, Month.OCTOBER,20,18,30);
        Long travelerId = 1L;
        BigDecimal totalAmount = BigDecimal.valueOf(1500.0);
        Integer adults = 2;
        Integer children = 3;

        Launch firstLaunch = LaunchMother.getFirstLaunchFromFirstBooking();
        Launch secondLaunch = LaunchMother.getSecondLaunchFromFirstBooking();
        Launch thirdLaunch = LaunchMother.getThirdLaunchFromFirstBooking();
        Long bookingId = 1L;

        Booking booking = BookingMother.getBookingToSave(
                checkIn, checkOut, totalAmount,travelerId, adults, children, Arrays.asList(firstLaunch, secondLaunch, thirdLaunch),TravelerMother.getTravelerSaved01());
        booking.setId(bookingId);
        Booking bookingToUpdate = BookingMother.getFirstBooking();
        firstLaunch.setBooking(bookingToUpdate);
        bookingToUpdate.setLaunchs(Arrays.asList(firstLaunch, secondLaunch, thirdLaunch));

        Booking bookingUpdated = getFirstBookingSaved();
        firstLaunch.setId(10L);
        secondLaunch.setId(20L);
        thirdLaunch.setId(30L);
        bookingUpdated.setTraveler(booking.getTraveler());
        bookingUpdated.setLaunchs(Arrays.asList(firstLaunch, secondLaunch,thirdLaunch));
        bookingUpdated.setBookingStatus(BookingStatusEnum.RESERVED);
        bookingUpdated.setPaymentStatus(PaymentStatusEnum.PENDING);
        bookingUpdated.setAmountPending(BigDecimal.valueOf(500.0));
        bookingUpdated.setAmountPaid(BigDecimal.valueOf(1000.0));

        when(bookingRepository.findBookingsByDate(any(LocalDateTime.class),any(LocalDateTime.class))).thenReturn(Arrays.asList());
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(bookingToUpdate));
        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingUpdated);
        when(travelerService.findById(anyLong())).thenReturn(booking.getTraveler());

        Booking result = bookingService.updateBooking(booking, bookingId);

        then(result.getCheckIn()).isEqualTo(checkIn);
        then(result.getCheckOut()).isEqualTo(checkOut);
        then(result.getBookingStatus()).isEqualTo(BookingStatusEnum.RESERVED);
        then(result.getPaymentStatus()).isEqualTo(PaymentStatusEnum.PENDING);
        then(result.getAmountTotal()).isEqualTo(BigDecimal.valueOf(1500.0));
        then(result.getAmountPending()).isEqualTo(BigDecimal.valueOf(500.0));
        then(result.getAmountPaid()).isEqualTo(BigDecimal.valueOf(1000.0));

    }

    @Test
    void givenNonExistentIdWhenConsultBookingThenReturnExceptionMessage(){
        Long bookingId = 123L;
        try{
            bookingService.findById(bookingId);
            fail("Não foi encontrado reserva pelo id: "+ bookingId, BookingException.class);
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo("Não foi encontrado reserva pelo id: "+ bookingId);
        }

    }

    @Test
    void givenBookingWithoutLaunchWhenCreateBookingThenReturnExceptionMessage(){
        Booking booking = BookingMother.getFirstBooking();
        try {
            bookingService.createBooking(booking);
            fail("Reserva deve possuir lançamentos",BookingException.class );
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo("Reserva deve possuir lançamentos");
        }
    }
    @Test
    void givenBookingWithoutLaunchWhenUpdateBookingThenReturnExceptionMessage(){
        Booking booking = BookingMother.getFirstBooking();
        try {
            bookingService.updateBooking(booking, 1L);
            fail("Reserva deve possuir lançamentos",BookingException.class );
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo("Reserva deve possuir lançamentos");
        }
    }

    @Test
    void givenLaunchWithLessAmountWhenCreateBookingThenReturnExceptionMessage(){
        Booking booking = BookingMother.getFirstBooking();
        Launch firstLaunch = getLaunchToSave(BigDecimal.valueOf(1000.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PAID, LocalDate.of(2021, 10,10), LocalDate.of(2021,10,10) );
        Launch secondLaunch = getLaunchToSave(BigDecimal.valueOf(300.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PENDING, LocalDate.of(2021, 11,10), null );
        Launch thirdLaunch = getLaunchToSave(BigDecimal.valueOf(100.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PENDING, LocalDate.of(2021, 12,10), null );

        booking.setLaunchs(Arrays.asList(firstLaunch, secondLaunch, thirdLaunch));
        try {
            bookingService.createBooking(booking);
            fail("Soma dos lançamentos estão diferentes do valor total da reserva",BookingException.class );
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo("Soma dos lançamentos estão diferentes do valor total da reserva");
        }

    }

    @Test
    void givenWhenCallNextBookingsThenReturnNextBookings(){

        Booking secondBooking = getSecondBookingSaved();
        Booking thirdBooking = getThirdBookingSaved();
        Booking forthBooking = getForthBookingSaved();

        when(bookingRepository.findNextBookings()).thenReturn(Arrays.asList( secondBooking, thirdBooking, forthBooking));
        when(travelerService.findById(2L)).thenReturn(TravelerMother.getTravelerSaved02());
        when(travelerService.findById(3L)).thenReturn(TravelerMother.getTravelerSaved03());
        when(travelerService.findById(4L)).thenReturn(TravelerMother.getTravelerSaved04());

        List<Booking> result = bookingService.findNextBookings();

        then(result.size()).isEqualTo(3);

        then(result.get(0).getId()).isEqualTo(20L);
        then(result.get(0).getAmountTotal()).isEqualTo(BigDecimal.valueOf(1500.0));
        then(result.get(0).getAmountPaid()).isEqualTo(BigDecimal.valueOf(500.0));
        then(result.get(0).getAmountPending()).isEqualTo(BigDecimal.valueOf(1000.0));
        then(result.get(0).getCheckIn()).isEqualTo(LocalDateTime.of(2021,1,1,10,0,0));
        then(result.get(0).getCheckOut()).isEqualTo(LocalDateTime.of(2021,1,15,18,0,0));
        then(result.get(0).getPaymentStatus()).isEqualTo(PaymentStatusEnum.PENDING);
        then(result.get(0).getTravelerName()).isEqualTo("Bianca Silva");

        then(result.get(1).getId()).isEqualTo(30L);
        then(result.get(1).getAmountTotal()).isEqualTo(BigDecimal.valueOf(2000.0));
        then(result.get(1).getAmountPaid()).isEqualByComparingTo(BigDecimal.ZERO);
        then(result.get(1).getAmountPending()).isEqualTo(BigDecimal.valueOf(2000.0));
        then(result.get(1).getCheckIn()).isEqualTo(LocalDateTime.of(2021,1,16,10,0,0));
        then(result.get(1).getCheckOut()).isEqualTo(LocalDateTime.of(2021,1,30,18,0,0));
        then(result.get(1).getPaymentStatus()).isEqualTo(PaymentStatusEnum.PENDING);
        then(result.get(1).getTravelerName()).isEqualTo("Carlos Garcia");

        then(result.get(2).getId()).isEqualTo(40L);
        then(result.get(2).getAmountTotal()).isEqualTo(BigDecimal.valueOf(2500.0));
        then(result.get(2).getAmountPaid()).isEqualTo(BigDecimal.valueOf(2500.0));
        then(result.get(2).getAmountPending()).isEqualTo(BigDecimal.ZERO);
        then(result.get(2).getCheckIn()).isEqualTo(LocalDateTime.of(2021,2,1,10,0,0));
        then(result.get(2).getCheckOut()).isEqualTo(LocalDateTime.of(2021,2,15,18,0,0));
        then(result.get(2).getPaymentStatus()).isEqualTo(PaymentStatusEnum.PAID);
        then(result.get(2).getTravelerName()).isEqualTo("David Souza");

    }

    @Test
    void givenBookingWhenUpdateToFinishThenReturnBookingFinished(){
//        String observation = "finished successfully";
//        Booking bookingSaved = BookingMother.getFirstBookingSaved();
//        Long bookingId = 10L;
//
//        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(bookingSaved));
//        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingSaved);
//
//        Booking result = bookingService.finishBooking(observation, bookingId);
//
//        then(result.getBookingStatus()).isEqualTo(BookingStatusEnum.FINISHED);
//        then(result.getLastUpdate()).isNotNull();
//        then(result.getObservation()).isEqualTo(observation);

    }

    @Test
    void givenBookingWhenUpdateToCancelThenReturnBookingCanceled(){
//        String observation = "canceled successfully";
//
//        Booking bookingSaved = BookingMother.getFirstBookingSaved();
//        Long bookingId = 10L;
//
//        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(bookingSaved));
//        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingSaved);
//
//        Booking result = bookingService.cancelBooking(observation, bookingId);
//
//        then(result.getBookingStatus()).isEqualTo(BookingStatusEnum.CANCELED);
//        then(result.getLastUpdate()).isNotNull();
//        then(result.getObservation()).isEqualTo(observation);

    }

}
