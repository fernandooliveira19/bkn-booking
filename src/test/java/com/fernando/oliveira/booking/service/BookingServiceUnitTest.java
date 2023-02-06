package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.entity.Traveler;
import com.fernando.oliveira.booking.domain.enums.*;
import com.fernando.oliveira.booking.domain.request.UpdateBookingRequest;
import com.fernando.oliveira.booking.domain.request.UpdateLaunchRequest;
import com.fernando.oliveira.booking.exception.BookingException;
import com.fernando.oliveira.booking.mother.BookingMother;
import com.fernando.oliveira.booking.mother.LaunchMother;
import com.fernando.oliveira.booking.mother.TravelerMother;
import com.fernando.oliveira.booking.repository.BookingRepository;
import com.fernando.oliveira.booking.repository.LaunchRepository;
import com.fernando.oliveira.booking.repository.TravelerRepository;
import com.fernando.oliveira.booking.service.impl.BookingServiceImpl;
import com.fernando.oliveira.booking.service.impl.LaunchServiceImpl;
import com.fernando.oliveira.booking.utils.MessageUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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

    @Mock
    private ToolsService toolsService;

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
    void givenValidRequestWhenCreateBookingThenCreateBookingReservedByWebsite(){
        LocalDateTime checkIn = LocalDateTime.of(2021, Month.OCTOBER,8,10,0);
        LocalDateTime checkOut = LocalDateTime.of(2021, Month.DECEMBER,16,18,0);
        Long travelerId = 4L;
        BigDecimal totalAmount = BigDecimal.valueOf(4000.0);
        BigDecimal websiteServiceFee = BigDecimal.valueOf(300.0);
        Integer adults = 2;
        Integer children = 3;

        Launch firstLaunch = getLaunchToSave(BigDecimal.valueOf(3700.0), PaymentTypeEnum.SITE, PaymentStatusEnum.TO_RECEIVE, LocalDate.of(2021, 10,10), LocalDate.of(2021,10,10) );

        Booking bookingToSave = getBookingToSave(checkIn, checkOut, totalAmount,travelerId, adults, children, Arrays.asList(firstLaunch), TravelerMother.getTravelerSaved04());
        bookingToSave.setWebsiteServiceFee(websiteServiceFee);
        bookingToSave.setContractType(ContractTypeEnum.SITE);

        Booking bookingSaved = getBookingSaved04();

        bookingSaved.setLaunches(Arrays.asList(firstLaunch));

        when(travelerService.findById(anyLong())).thenReturn(TravelerMother.getTravelerToSaved01());
        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingSaved);
        when(launchService.createLaunch(any(Launch.class), any(Booking.class))).thenReturn(firstLaunch);

        Booking result = bookingService.createBooking(bookingToSave);

        then(result.getPaymentStatus()).isEqualTo(PaymentStatusEnum.TO_RECEIVE);
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
        String exceptionMessage ="Não foi encontrado reserva pelo id: 123";
        when(messageUtils.getMessage(anyString(), any())).thenReturn(exceptionMessage);
        try{
            bookingService.findById(bookingId);
            fail(exceptionMessage, BookingException.class);
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo(exceptionMessage);
        }

    }

    @Test
    void givenBookingWithoutLaunchWhenCreateBookingThenReturnExceptionMessage(){
        Booking booking = BookingMother.getBookingRequest01();
        booking.setLaunches(Arrays.asList());
        String exceptionMessage ="Reserva deve possuir lançamentos";
        when(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_MUST_HAVE_LAUNCHES)).thenReturn(exceptionMessage);
        try {
            bookingService.createBooking(booking);
            fail(exceptionMessage,BookingException.class );
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo(exceptionMessage);
        }
    }
    @Test
    void givenBookingWithoutLaunchWhenUpdateBookingThenReturnExceptionMessage(){
        String exceptionMessage = "Reserva deve possuir lançamentos";
        Booking booking = BookingMother.getBookingRequest01();
        booking.setLaunches(Arrays.asList());
        when(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_MUST_HAVE_LAUNCHES)).thenReturn(exceptionMessage);
        try {
            bookingService.updateBooking(booking, 1L);
            fail(exceptionMessage,BookingException.class );
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo(exceptionMessage);
        }
    }

    @Test
    void givenLaunchWithLessAmountWhenCreateBookingThenReturnExceptionMessage(){
        Booking booking = BookingMother.getBookingRequest02();
        booking.getLaunches().get(2).setAmount(BigDecimal.valueOf(900.0));

        String exceptionMessage = "Soma dos lançamentos estão diferentes do valor total da reserva";
        when(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_SUM_LAUNCHES_AMOUNT_ERROR)).thenReturn(exceptionMessage);
        try {
            bookingService.createBooking(booking);
            fail(exceptionMessage,BookingException.class );
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo(exceptionMessage);
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
        String exceptionMessage ="É obrigatório preencher uma observação sobre a reserva";
        UpdateBookingRequest request = BookingMother.getUpdateBookingRequest(6L, "2021-03-15T10:00:00", "2021-03-30T18:00:00", BigDecimal.valueOf(4000.0), observation, BookingStatusEnum.CANCELED,  Arrays.asList());
        Booking bookingToCancel = BookingMother.getBookingToUpdate(request);

        when(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_OBSERVATION_REQUIRED)).thenReturn(exceptionMessage);
        try {
            bookingService.updateBooking(bookingToCancel, 60L);
            fail(exceptionMessage,BookingException.class );
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo(exceptionMessage);
        }
    }

    @Test
    void givenNullObservationWhenUpdateToCancelThenReturnExceptionMessage(){
        String exceptionMessage ="É obrigatório preencher uma observação sobre a reserva";
        UpdateBookingRequest request = BookingMother.getUpdateBookingRequest(6L, "2021-03-15T10:00:00", "2021-03-30T18:00:00", BigDecimal.valueOf(4000.0), null, BookingStatusEnum.CANCELED,  Arrays.asList());
        Booking bookingToCancel = BookingMother.getBookingToUpdate(request);

        when(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_OBSERVATION_REQUIRED)).thenReturn(exceptionMessage);
        try {
            bookingService.updateBooking(bookingToCancel, 60L);
            fail(exceptionMessage,BookingException.class );
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo(exceptionMessage);
        }
    }

    @Test
    void givenLaunchPaidWhenUpdateToCancelThenReturnExceptionMessage(){
        String observation = "canceled successfully";
        String exceptionMessage = "Não é possível cancelar a reserva. Verificar lançamentos pagos";
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

        when(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_CANCEL_LAUNCHES_PAID_ERROR)).thenReturn(exceptionMessage);
        try {
            bookingService.updateBooking(bookingToCancel, 60L);
            fail(exceptionMessage,BookingException.class );
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo(exceptionMessage);
        }
    }

    @Test
    public void givenTravelerIdWhenFindBookingsByTravelerThenReturnBookingList(){
        List<Booking> bookings = Arrays.asList(getBookingSaved02());

        when(bookingRepository.findByTraveler(anyLong())).thenReturn(bookings);
        when(toolsService.rentDays(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(Long.valueOf(14));
        when(toolsService.averageValue(anyLong(), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(107.14));
        Long travelerId = 2L;

        List<Booking> result = bookingService.findBookingsByTraveler(travelerId);

        then(result.size()).isEqualTo(1);
        then(result.get(0).getRentDays()).isEqualTo(14);
        then(result.get(0).getAverageValue()).isEqualByComparingTo(BigDecimal.valueOf(107.14));

    }

    @Test
    void givenLaunchesPendingWhenUpdateToFinishThenReturnExceptionMessage(){
        String observation = "canceled successfully";
        String exceptionMessage = "Não é possível finalizar a reserva. Verificar lançamentos pendentes";
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

        UpdateBookingRequest request = BookingMother.getUpdateBookingRequest(6L, "2021-03-15T10:00:00", "2021-03-30T18:00:00", BigDecimal.valueOf(4000.0), observation, BookingStatusEnum.FINISHED,  Arrays.asList(launch01, launch02));
        Booking bookingToCancel = BookingMother.getBookingToUpdate(request);

        when(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_FINISH_LAUNCHES_PENDING_ERROR)).thenReturn(exceptionMessage);
        try {
            bookingService.updateBooking(bookingToCancel, 60L);
            fail(exceptionMessage,BookingException.class );
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo(exceptionMessage);
        }
    }

    @Test
    void givenEmptyObservationWhenUpdateToFinishThenReturnExceptionMessage(){
        String observation = "";
        String exceptionMessage ="É obrigatório preencher uma observação sobre a reserva";

        UpdateBookingRequest request = BookingMother.getUpdateBookingRequest(6L, "2021-03-15T10:00:00", "2021-03-30T18:00:00", BigDecimal.valueOf(4000.0), observation, BookingStatusEnum.FINISHED,  Arrays.asList());
        Booking bookingToCancel = BookingMother.getBookingToUpdate(request);

        when(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_OBSERVATION_REQUIRED)).thenReturn(exceptionMessage);
        try {
            bookingService.updateBooking(bookingToCancel, 60L);
            fail(exceptionMessage,BookingException.class );
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo(exceptionMessage);
        }
    }

    @Test
    void givenDateBeforeCheckoutWhenUpdateToFinishThenReturnExceptionMessage(){
        String observation = "canceled successfully";
        UpdateLaunchRequest launch01 = LaunchMother.getUpdateLaunchRequest(110L,
                BigDecimal.valueOf(2000.0),
                "2021-03-15",
                "PIX",
                "PAID",
                "2021-03-15");
        UpdateLaunchRequest launch02 = LaunchMother.getUpdateLaunchRequest(111L,
                BigDecimal.valueOf(2000.0),
                "2021-03-15",
                "PIX",
                "PAID",
                "2021-03-15");

        String exceptionMessage = "Não é permitido finalizar a reserva antes do check-out";

        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        String checkOut = tomorrow.toString();

        UpdateBookingRequest request = BookingMother.getUpdateBookingRequest(6L, "2021-03-15T10:00:00", checkOut, BigDecimal.valueOf(4000.0), observation, BookingStatusEnum.FINISHED,  Arrays.asList(launch01, launch02));
        Booking bookingToCancel = BookingMother.getBookingToUpdate(request);

        when(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_FINISH_BEFORE_CHECKOUT_ERROR)).thenReturn(exceptionMessage);
        try {
            bookingService.updateBooking(bookingToCancel, 60L);
            fail(exceptionMessage,BookingException.class );
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo(exceptionMessage);
        }
    }

    @Test
    void givenDateWithConflictWhenUpdateBookingThenReturnExceptionMessage(){
        String exceptionMessage = "Existe outra reserva para o mesmo periodo";
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

        UpdateBookingRequest request = BookingMother.getUpdateBookingRequest(6L, "2021-03-15T10:00:00", "2021-03-30T18:00:00", BigDecimal.valueOf(4000.0), "observation", BookingStatusEnum.PRE_RESERVED,  Arrays.asList(launch01, launch02));
        Booking bookingToCancel = BookingMother.getBookingToUpdate(request);

        when(bookingRepository.findBookingsByDate(any(), any())).thenReturn(Arrays.asList(BookingMother.getBookingSaved02()));
        when(messageUtils.getMessage(ExceptionMessageEnum.BOOKING_ALREADY_EXISTS)).thenReturn(exceptionMessage);
        try {
            bookingService.updateBooking(bookingToCancel, 60L);
            fail(exceptionMessage,BookingException.class );
        }catch (BookingException e){
            then(e.getMessage()).isEqualTo(exceptionMessage);
        }
    }

    @Test
    void givenValidNewLaunchWhenUpdateBookingThenReturnBookingUpdated(){
        String exceptionMessage = "";
        UpdateLaunchRequest launch01 = LaunchMother.getUpdateLaunchRequest(110L,
                BigDecimal.valueOf(2000.0),
                "2021-03-15",
                "PIX",
                "PENDING",
                null);
        UpdateLaunchRequest launch02 = LaunchMother.getUpdateLaunchRequest(111L,
                BigDecimal.valueOf(1000.0),
                "2021-03-15",
                "PIX",
                "PAID",
                "2021-03-15");
        UpdateLaunchRequest launch03 = LaunchMother.getUpdateLaunchRequest(null,
                BigDecimal.valueOf(1000.0),
                "2021-03-15",
                "PIX",
                "PAID",
                "2021-03-15");

        UpdateBookingRequest request = BookingMother.getUpdateBookingRequest(6L, "2021-03-15T10:00:00", "2021-03-30T18:00:00", BigDecimal.valueOf(4000.0), "observation", BookingStatusEnum.PRE_RESERVED,  Arrays.asList(launch01, launch02, launch03));
        Booking bookingToUpdate = BookingMother.getBookingToUpdate(request);
        bookingToUpdate.setTraveler(TravelerMother.getTravelerSaved06());
        bookingToUpdate.setBookingStatus(BookingStatusEnum.RESERVED);

        when(bookingRepository.findBookingsByDate(any(), any())).thenReturn(Arrays.asList(BookingMother.getBookingSaved06()));
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(BookingMother.getBookingSaved06()));
        when(travelerService.findById(anyLong())).thenReturn(TravelerMother.getTravelerSaved06());
        when(bookingRepository.save(any(Booking.class))).thenReturn(BookingMother.getBookingSaved06());

        Booking result = bookingService.updateBooking(bookingToUpdate, 60L);

        then(result).isNotNull();
    }


}
