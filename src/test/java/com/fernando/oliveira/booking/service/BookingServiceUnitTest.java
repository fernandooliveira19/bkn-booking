package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.entity.Traveler;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.ExceptionMessageEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentTypeEnum;
import com.fernando.oliveira.booking.domain.request.UpdateBookingRequest;
import com.fernando.oliveira.booking.domain.request.UpdateLaunchRequest;
import com.fernando.oliveira.booking.exception.BookingException;
import com.fernando.oliveira.booking.mother.BookingMother;
import com.fernando.oliveira.booking.mother.LaunchMother;
import com.fernando.oliveira.booking.mother.TravelerMother;
import com.fernando.oliveira.booking.repository.BookingRepository;
import com.fernando.oliveira.booking.repository.LaunchRepository;
import com.fernando.oliveira.booking.repository.TravelerRepository;
import com.fernando.oliveira.booking.utils.MessageUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.fernando.oliveira.booking.mother.BookingMother.*;
import static com.fernando.oliveira.booking.mother.LaunchMother.getLaunchToSave;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.*;
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
    private TravelerService travelerService;

    @Mock
    private TravelerRepository travelerRepository;

    @Mock
    private MessageUtils messageUtils;

    static Clock clock;

    @Test
    void givenValidRequestWhenCreateBookingThenCreateBookingReservedWithPending(){

        Booking bookingToSave = getBookingRequest02();

        Booking bookingSaved = getBookingSaved02();


        when(travelerService.findById(anyLong())).thenReturn(TravelerMother.getTravelerSaved02());
        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingSaved);

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
        Booking bookingSaved = getBookingSaved01();
        bookingSaved.setBookingStatus(BookingStatusEnum.RESERVED);
        bookingSaved.setPaymentStatus(PaymentStatusEnum.PAID);
        bookingSaved.setLaunches(Arrays.asList(firstLaunch, secondLaunch, thirdLaunch));

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
    void givenValidRequestWhenUpdateBookingThenReturnBookingUpdated(){
        LocalDateTime checkIn = LocalDateTime.of(2020,Month.DECEMBER,16, 10,0,0);
        Booking bookingToUpdate = getBookingRequest01();
        bookingToUpdate.setCheckIn(checkIn);
        Booking bookingUpdated = getBookingSaved01();

        when(bookingRepository.findBookingsByDate(any(LocalDateTime.class),any(LocalDateTime.class))).thenReturn(Arrays.asList());
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(bookingToUpdate));
        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingUpdated);
        when(travelerService.findById(anyLong())).thenReturn(TravelerMother.getTravelerSaved01());

        Booking result = bookingService.updateBooking(bookingToUpdate, 1L);

        then(result.getBookingStatus()).isEqualTo(BookingStatusEnum.FINISHED);
        then(result.getPaymentStatus()).isEqualTo(PaymentStatusEnum.PAID);
        then(result.getAmountTotal()).isEqualTo(BigDecimal.valueOf(1000.0));
        then(result.getAmountPending()).isEqualTo(BigDecimal.valueOf(0.0));
        then(result.getAmountPaid()).isEqualTo(BigDecimal.valueOf(1000.0));

    }

    @Test
    void givenNonExistentIdWhenConsultBookingThenReturnExceptionMessage(){
        Long bookingId = 123L;

        when(messageUtils.getMessage(anyString(), any())).thenReturn("Não foi encontrado reserva pelo id: "+ bookingId);
        try{
            bookingService.findById(bookingId);
            fail("Não foi encontrado reserva pelo id: "+ bookingId, BookingException.class);
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo("Não foi encontrado reserva pelo id: "+ bookingId);
        }

    }

    @Test
    void givenBookingWithoutLaunchWhenCreateBookingThenReturnExceptionMessage(){
        Booking booking = BookingMother.getBookingRequest01();
        booking.setLaunches(Arrays.asList());
        when(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_MUST_HAVE_LAUNCHES)).thenReturn("Reserva deve possuir lançamentos");
        try {
            bookingService.createBooking(booking);
            fail("Reserva deve possuir lançamentos",BookingException.class );
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo("Reserva deve possuir lançamentos");
        }
    }
    @Test
    void givenBookingWithoutLaunchWhenUpdateBookingThenReturnExceptionMessage(){
        Booking booking = BookingMother.getBookingRequest01();
        booking.setLaunches(Arrays.asList());
        when(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_MUST_HAVE_LAUNCHES)).thenReturn("Reserva deve possuir lançamentos");
        try {
            bookingService.updateBooking(booking, 1L);
            fail("Reserva deve possuir lançamentos",BookingException.class );
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo("Reserva deve possuir lançamentos");
        }
    }

    @Test
    void givenLaunchWithLessAmountWhenCreateBookingThenReturnExceptionMessage(){
        Booking booking = BookingMother.getBookingRequest02();
        booking.getLaunches().get(2).setAmount(BigDecimal.valueOf(900.0));
        when(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_SUM_LAUNCHES_AMOUNT_ERROR)).thenReturn("Soma dos lançamentos estão diferentes do valor total da reserva");
        try {
            bookingService.createBooking(booking);
            fail("Soma dos lançamentos estão diferentes do valor total da reserva",BookingException.class );
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo("Soma dos lançamentos estão diferentes do valor total da reserva");
        }

    }

    @Test
    void shouldReturnNextBookings(){

        when(bookingRepository.findNextBookings()).thenReturn(Arrays.asList(getBookingSaved02(),getBookingSaved03(),getBookingSaved04()));

        when(travelerService.findById(2L)).thenReturn(TravelerMother.getTravelerSaved02());
        when(travelerService.findById(3L)).thenReturn(TravelerMother.getTravelerSaved03());
        when(travelerService.findById(4L)).thenReturn(TravelerMother.getTravelerSaved04());

        List<Booking> result = bookingService.findNextBookings();

        then(result.size()).isEqualTo(3);

        then(result.get(0).getId()).isEqualTo(20L);
        then(result.get(0).getBookingStatus()).isEqualTo(BookingStatusEnum.RESERVED);
        then(result.get(0).getPaymentStatus()).isEqualTo(PaymentStatusEnum.PENDING);

        then(result.get(1).getId()).isEqualTo(30L);
        then(result.get(1).getBookingStatus()).isEqualTo(BookingStatusEnum.PRE_RESERVED);
        then(result.get(1).getPaymentStatus()).isEqualTo(PaymentStatusEnum.PENDING);

        then(result.get(2).getId()).isEqualTo(40L);
        then(result.get(2).getBookingStatus()).isEqualTo(BookingStatusEnum.RESERVED);
        then(result.get(2).getPaymentStatus()).isEqualTo(PaymentStatusEnum.TO_RECEIVE);

    }

    @Test
    void givenBookingWhenUpdateToFinishThenReturnBookingFinished(){
        String observation = "finished successfully";

        UpdateLaunchRequest launch01 = LaunchMother.getUpdateLaunchRequest(110L,
                BigDecimal.valueOf(2000.0),
                "2021-03-15",
                "PIX",
                "PAID",
                "2021-03-15");
        UpdateLaunchRequest launch02 = LaunchMother.getUpdateLaunchRequest(110L,
                BigDecimal.valueOf(2000.0),
                "2021-03-15",
                "PIX",
                "PAID",
                "2021-03-15");

        UpdateBookingRequest request = BookingMother.getUpdateBookingRequest(6L, "2021-03-15T10:00:00", "2021-03-30T18:00:00", BigDecimal.valueOf(4000.0), observation, BookingStatusEnum.FINISHED,  Arrays.asList(launch01, launch02));
        Booking bookingToCancel = BookingMother.getBookingToUpdate(request);
        bookingToCancel.setTraveler(TravelerMother.getTravelerSaved06());


        Booking bookingSaved = BookingMother.getBookingSaved06();
        bookingSaved.setBookingStatus(BookingStatusEnum.FINISHED);
        bookingSaved.setObservation(observation);
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(bookingSaved));
        when(travelerService.findById(anyLong())).thenReturn(TravelerMother.getTravelerSaved06());
        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingSaved);

        Booking result = bookingService.updateBooking(bookingToCancel, 60L);

        then(result.getBookingStatus()).isEqualTo(BookingStatusEnum.FINISHED);

        then(result.getObservation()).isEqualTo(observation);

    }

    @Test
    void givenBookingWhenUpdateToFinishBeforeCheckOutThenReturnExceptionMessage(){
//        String observation = "finished successfully";
//
//        UpdateLaunchRequest launch01 = LaunchMother.getUpdateLaunchRequest(110L,
//                BigDecimal.valueOf(2000.0),
//                "2021-03-15",
//                "PIX",
//                "PAID",
//                "2021-03-15");
//        UpdateLaunchRequest launch02 = LaunchMother.getUpdateLaunchRequest(110L,
//                BigDecimal.valueOf(2000.0),
//                "2021-03-15",
//                "PIX",
//                "PAID",
//                "2021-03-15");
//
//        UpdateBookingRequest request = BookingMother.getUpdateBookingRequest(6L, "2021-03-15T10:00:00", "2021-03-30T18:00:00", BigDecimal.valueOf(4000.0), observation, BookingStatusEnum.FINISHED,  Arrays.asList(launch01, launch02));
//        Booking bookingToCancel = BookingMother.getBookingToUpdate(request);
//        bookingToCancel.setTraveler(TravelerMother.getTravelerSaved06());
//
//
//        Booking bookingSaved = BookingMother.getBookingSaved06();
//        bookingSaved.setBookingStatus(BookingStatusEnum.FINISHED);
//        bookingSaved.setObservation(observation);
//
//        clock = Clock.fixed(
//                Instant.parse("2020-12-01T10:05:23.653Z"),
//                ZoneId.of("Europe/Prague"));
//
//
//        when(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_FINISH_BEFORE_CHECKOUT_ERROR)).thenReturn("Não é permitido finalizar a reserva antes do check-out");
//        try {
//            bookingService.updateBooking(bookingToCancel, 60L);
//            fail("Não é permitido finalizar a reserva antes do check-out",BookingException.class );
//        }catch (BookingException e){
//            then(e.getMessage()).isEqualTo("Não é permitido finalizar a reserva antes do check-out");
//        }

    }

    @Test
    void givenBookingWhenUpdateToCancelThenReturnBookingCanceled(){
        String observation = "canceled successfully";

        UpdateLaunchRequest launch01 = LaunchMother.getUpdateLaunchRequest(110L,
                BigDecimal.valueOf(2000.0),
                "2021-03-15",
                "PIX",
                "PENDING",
                null);
        UpdateLaunchRequest launch02 = LaunchMother.getUpdateLaunchRequest(110L,
                BigDecimal.valueOf(2000.0),
                "2021-03-15",
                "PIX",
                "PENDING",
                null);

        UpdateBookingRequest request = BookingMother.getUpdateBookingRequest(6L, "2021-03-15T10:00:00", "2021-03-30T18:00:00", BigDecimal.valueOf(4000.0), observation, BookingStatusEnum.CANCELED,  Arrays.asList(launch01, launch02));
        Booking bookingToCancel = BookingMother.getBookingToUpdate(request);
        bookingToCancel.setTraveler(TravelerMother.getTravelerSaved06());


        Booking bookingSaved = BookingMother.getBookingSaved06();
        bookingSaved.setBookingStatus(BookingStatusEnum.CANCELED);
        bookingSaved.setObservation(observation);
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(bookingSaved));
        when(travelerService.findById(anyLong())).thenReturn(TravelerMother.getTravelerSaved06());
        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingSaved);

        Booking result = bookingService.updateBooking(bookingToCancel, 60L);

        then(result.getBookingStatus()).isEqualTo(BookingStatusEnum.CANCELED);

        then(result.getObservation()).isEqualTo(observation);

    }

    @Test
    void givenEmptyObservationWhenUpdateToCancelThenReturnExceptionMessage(){
        String observation = "";

        UpdateBookingRequest request = BookingMother.getUpdateBookingRequest(6L, "2021-03-15T10:00:00", "2021-03-30T18:00:00", BigDecimal.valueOf(4000.0), observation, BookingStatusEnum.CANCELED,  Arrays.asList());
        Booking bookingToCancel = BookingMother.getBookingToUpdate(request);

        when(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_OBSERVATION_REQUIRED)).thenReturn("É obrigatório preencher uma observação sobre a reserva");
        try {
            bookingService.updateBooking(bookingToCancel, 60L);
            fail("É obrigatório preencher uma observação sobre a reserva",BookingException.class );
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo("É obrigatório preencher uma observação sobre a reserva");
        }
    }

    @Test
    void givenNullObservationWhenUpdateToCancelThenReturnExceptionMessage(){

        UpdateBookingRequest request = BookingMother.getUpdateBookingRequest(6L, "2021-03-15T10:00:00", "2021-03-30T18:00:00", BigDecimal.valueOf(4000.0), null, BookingStatusEnum.CANCELED,  Arrays.asList());
        Booking bookingToCancel = BookingMother.getBookingToUpdate(request);

        when(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_OBSERVATION_REQUIRED)).thenReturn("É obrigatório preencher uma observação sobre a reserva");
        try {
            bookingService.updateBooking(bookingToCancel, 60L);
            fail("É obrigatório preencher uma observação sobre a reserva",BookingException.class );
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo("É obrigatório preencher uma observação sobre a reserva");
        }
    }

    @Test
    void givenLaunchPaidWhenUpdateToCancelThenReturnExceptionMessage(){
        String observation = "canceled successfully";
        UpdateLaunchRequest launch01 = LaunchMother.getUpdateLaunchRequest(110L,
                BigDecimal.valueOf(2000.0),
                "2021-03-15",
                "PIX",
                "PENDING",
                null);
        UpdateLaunchRequest launch02 = LaunchMother.getUpdateLaunchRequest(111L,
                BigDecimal.valueOf(2000.0),
                "2021-03-15",
                "PIX",
                "PAID",
                "2021-03-15");

        UpdateBookingRequest request = BookingMother.getUpdateBookingRequest(6L, "2021-03-15T10:00:00", "2021-03-30T18:00:00", BigDecimal.valueOf(4000.0), observation, BookingStatusEnum.CANCELED,  Arrays.asList(launch01, launch02));
        Booking bookingToCancel = BookingMother.getBookingToUpdate(request);
        bookingToCancel.setTraveler(TravelerMother.getTravelerSaved06());

        when(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_CANCEL_LAUNCHES_PAID_ERROR)).thenReturn("Não é possível cancelar a reserva. Verificar lançamentos pagos");
        try {
            bookingService.updateBooking(bookingToCancel, 60L);
            fail("Não é possível cancelar a reserva. Verificar lançamentos pagos",BookingException.class );
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo("Não é possível cancelar a reserva. Verificar lançamentos pagos");
        }
    }

    @Test
    public void givenTravelerIdWhenFindBookingsByTravelerThenReturnBookingList(){
        List<Booking> bookings = Arrays.asList(getBookingSaved02());

        when(bookingRepository.findByTraveler(anyLong())).thenReturn(bookings);

        Long travelerId = 2L;

        List<Booking> result = bookingService.findBookingsByTraveler(travelerId);

        then(result.size()).isEqualTo(1);

    }

}
