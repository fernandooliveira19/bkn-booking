package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.entity.Traveler;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.ContractTypeEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentTypeEnum;
import com.fernando.oliveira.booking.domain.mapper.BookingMapper;
import com.fernando.oliveira.booking.domain.request.CreateBookingRequest;
import com.fernando.oliveira.booking.domain.request.CreateLaunchRequest;
import com.fernando.oliveira.booking.domain.response.BookingDetailResponse;
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

    @Mock
    private BookingMapper bookingMapper;

    @Test
    void givenValidRequestWhenCreateBookingThenCreateBookingReservedWithPending(){

        LocalDateTime checkIn = LocalDateTime.of(2021,Month.JANUARY,1,10,0,0);
        LocalDateTime checkOut = LocalDateTime.of(2021,Month.JANUARY,15,18,0,0);
        BigDecimal amountTotal = BigDecimal.valueOf(1500.0);
        Traveler traveler = TravelerMother.getTravelerSaved02();
        Integer adults=4;
        Integer children=2;
        ContractTypeEnum contractType = ContractTypeEnum.DIRECT;
        Launch launch01 = getLaunchToSave(BigDecimal.valueOf(300.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PAID, LocalDate.of(2021, Month.JANUARY, 1),LocalDate.of(2021, Month.JANUARY, 2));
        Launch launch02 = getLaunchToSave(BigDecimal.valueOf(200.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PAID, LocalDate.of(2021, Month.JANUARY, 5),LocalDate.of(2021, Month.JANUARY, 6));
        Launch launch03 = getLaunchToSave(BigDecimal.valueOf(1000.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PAID, LocalDate.of(2021, Month.JANUARY, 8),null);

        Booking bookingToSave = getBookingToSave(checkIn,checkOut,amountTotal,traveler.getId(),adults, children, Arrays.asList(launch01, launch02, launch03), traveler );

        CreateLaunchRequest launchRequest01 = LaunchMother.getCreateLaunchRequest(BigDecimal.valueOf(300.0), "2021-01-01", "PIX", "PAID", "2021-01-02");
        CreateLaunchRequest launchRequest02 = LaunchMother.getCreateLaunchRequest(BigDecimal.valueOf(200.0), "2021-01-05", "PIX", "PAID", "2021-01-06");
        CreateLaunchRequest launchRequest03 = LaunchMother.getCreateLaunchRequest(BigDecimal.valueOf(1000.0), "2021-01-01", "PIX", "PENDING", null);
        CreateBookingRequest request = BookingMother.getCreateBookingRequest(traveler.getId(), "2021-01-01T10:00", "2021-01-15T18:00", amountTotal, "DIRECT", Arrays.asList(launchRequest01, launchRequest02,launchRequest03));

        Booking bookingSaved = getSecondBookingSaved();
        BookingDetailResponse response = BookingMother.getBookingDetailResponse(bookingSaved);

        when(bookingMapper.createRequestToEntity(any(CreateBookingRequest.class))).thenReturn(bookingToSave);
        when(travelerService.findById(anyLong())).thenReturn(TravelerMother.getTravelerSaved02());
        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingSaved);
        when(launchService.createLaunch(any(Launch.class), any(Booking.class))).thenReturn(LaunchMother.getLaunchSaved(bookingSaved,BigDecimal.valueOf(200.0),PaymentTypeEnum.PIX,PaymentStatusEnum.PAID, LocalDate.of(2021, Month.OCTOBER, 10),LocalDate.of(2021, Month.OCTOBER, 10) ));
        when(bookingMapper.bookingToDetailBookingResponse(any(Booking.class))).thenReturn(response);

        BookingDetailResponse result = bookingService.createBooking(request);

        then(result.getBookingStatus()).isEqualTo( BookingStatusEnum.RESERVED);
        then(result.getPaymentStatus()).isEqualTo( PaymentStatusEnum.PENDING);

    }

    @Test
    void givenValidRequestWhenCreateBookingThenCreateBookingReservedPaid(){
        LocalDateTime checkIn = LocalDateTime.of(2021,Month.FEBRUARY,1,10,0,0);
        LocalDateTime checkOut = LocalDateTime.of(2021,Month.FEBRUARY,15,18,0,0);
        BigDecimal amountTotal = BigDecimal.valueOf(2500.0);
        Traveler traveler = TravelerMother.getTravelerSaved04();
        Integer adults=6;
        Integer children=1;
        ContractTypeEnum contractType = ContractTypeEnum.SITE;
        Launch launch01 = getLaunchToSave(BigDecimal.valueOf(2500.0), PaymentTypeEnum.SITE, PaymentStatusEnum.PAID, LocalDate.of(2021, Month.FEBRUARY, 25),LocalDate.of(2021, Month.FEBRUARY, 25));

        Booking bookingToSave = getBookingToSave(checkIn,checkOut,amountTotal,traveler.getId(),adults, children, Arrays.asList(launch01), traveler );

        CreateLaunchRequest launchRequest01 = LaunchMother.getCreateLaunchRequest(BigDecimal.valueOf(2500.0), "2021-02-25", "SITE", "PAID", "2021-02-25");

        CreateBookingRequest request = BookingMother.getCreateBookingRequest(traveler.getId(), "2021-01-01T10:00", "2021-01-15T18:00", amountTotal, "DIRECT", Arrays.asList(launchRequest01));

        Booking bookingSaved = getForthBookingSaved();
        BookingDetailResponse response = BookingMother.getBookingDetailResponse(bookingSaved);

        when(bookingMapper.createRequestToEntity(any(CreateBookingRequest.class))).thenReturn(bookingToSave);
        when(travelerService.findById(anyLong())).thenReturn(TravelerMother.getTravelerSaved02());
        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingSaved);
        when(launchService.createLaunch(any(Launch.class), any(Booking.class))).thenReturn(launch01);
        when(bookingMapper.bookingToDetailBookingResponse(any(Booking.class))).thenReturn(response);

        BookingDetailResponse result = bookingService.createBooking(request);

        then(result.getBookingStatus()).isEqualTo( BookingStatusEnum.RESERVED);
        then(result.getPaymentStatus()).isEqualTo( PaymentStatusEnum.PAID);

    }
    @Test
    void givenValidRequestWhenCreateBookingThenCreateBookingPreReserved(){

        LocalDateTime checkIn = LocalDateTime.of(2021,Month.JANUARY,16,10,0,0);
        LocalDateTime checkOut = LocalDateTime.of(2021,Month.JANUARY,30,18,0,0);
        BigDecimal amountTotal = BigDecimal.valueOf(2000.0);
        Traveler traveler = TravelerMother.getTravelerSaved03();
        Integer adults=3;
        Integer children=2;
        ContractTypeEnum contractType = ContractTypeEnum.DIRECT;
        Launch launch01 = getLaunchToSave(BigDecimal.valueOf(1200.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PENDING, LocalDate.of(2021, Month.JANUARY, 12),null);
        Launch launch02 = getLaunchToSave(BigDecimal.valueOf(800.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PENDING, LocalDate.of(2021, Month.JANUARY, 16),null);

        Booking bookingToSave = getBookingToSave(checkIn,checkOut,amountTotal,traveler.getId(),adults, children, Arrays.asList(launch01, launch02), traveler );

        CreateLaunchRequest launchRequest01 = LaunchMother.getCreateLaunchRequest(BigDecimal.valueOf(1200.0), "2021-01-12", "PIX", "PENDING", null);
        CreateLaunchRequest launchRequest02 = LaunchMother.getCreateLaunchRequest(BigDecimal.valueOf(800.0), "2021-01-16", "PIX", "PENDING", null);

        CreateBookingRequest request = BookingMother.getCreateBookingRequest(traveler.getId(), "2021-01-06T10:00", "2021-01-30T18:00", amountTotal, "DIRECT", Arrays.asList(launchRequest01, launchRequest02));

        Booking bookingSaved = getThirdBookingSaved();
        BookingDetailResponse response = BookingMother.getBookingDetailResponse(bookingSaved);

        when(bookingMapper.createRequestToEntity(any(CreateBookingRequest.class))).thenReturn(bookingToSave);
        when(travelerService.findById(anyLong())).thenReturn(TravelerMother.getTravelerSaved02());
        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingSaved);
        when(launchService.createLaunch(any(Launch.class), any(Booking.class))).thenReturn(launch01);
        when(bookingMapper.bookingToDetailBookingResponse(any(Booking.class))).thenReturn(response);

        BookingDetailResponse result = bookingService.createBooking(request);

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
        bookingToUpdate.setLaunches(Arrays.asList(firstLaunch, secondLaunch, thirdLaunch));

        Booking bookingUpdated = getFirstBookingSaved();
        firstLaunch.setId(10L);
        secondLaunch.setId(20L);
        thirdLaunch.setId(30L);
        bookingUpdated.setTraveler(booking.getTraveler());
        bookingUpdated.setLaunches(Arrays.asList(firstLaunch, secondLaunch,thirdLaunch));
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
        LocalDateTime checkIn = LocalDateTime.of(2021,Month.JANUARY,1,10,0,0);
        LocalDateTime checkOut = LocalDateTime.of(2021,Month.JANUARY,15,18,0,0);
        BigDecimal amountTotal = BigDecimal.valueOf(1500.0);
        Traveler traveler = TravelerMother.getTravelerSaved02();
        Integer adults=4;
        Integer children=2;
        Booking bookingToSave = getBookingToSave(checkIn,checkOut,amountTotal,traveler.getId(),adults, children, Arrays.asList(), traveler );

        CreateBookingRequest request = BookingMother.getCreateBookingRequest(traveler.getId(), "2021-01-01T10:00", "2021-01-15T18:00", amountTotal, "DIRECT", Arrays.asList());

        when(bookingMapper.createRequestToEntity(any(CreateBookingRequest.class))).thenReturn(bookingToSave);

        try {
            bookingService.createBooking(request);
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

        LocalDateTime checkIn = LocalDateTime.of(2021,Month.JANUARY,1,10,0,0);
        LocalDateTime checkOut = LocalDateTime.of(2021,Month.JANUARY,15,18,0,0);
        BigDecimal amountTotal = BigDecimal.valueOf(1500.0);
        Traveler traveler = TravelerMother.getTravelerSaved02();
        Integer adults=4;
        Integer children=2;
        ContractTypeEnum contractType = ContractTypeEnum.DIRECT;
        Launch launch01 = getLaunchToSave(BigDecimal.valueOf(300.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PAID, LocalDate.of(2021, Month.JANUARY, 1),LocalDate.of(2021, Month.JANUARY, 2));
        Launch launch02 = getLaunchToSave(BigDecimal.valueOf(200.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PAID, LocalDate.of(2021, Month.JANUARY, 5),LocalDate.of(2021, Month.JANUARY, 6));
        Launch launch03 = getLaunchToSave(BigDecimal.valueOf(900.0), PaymentTypeEnum.PIX, PaymentStatusEnum.PAID, LocalDate.of(2021, Month.JANUARY, 8),null);

        Booking bookingToSave = getBookingToSave(checkIn,checkOut,amountTotal,traveler.getId(),adults, children, Arrays.asList(launch01, launch02, launch03), traveler );

        CreateLaunchRequest launchRequest01 = LaunchMother.getCreateLaunchRequest(BigDecimal.valueOf(300.0), "2021-01-01", "PIX", "PAID", "2021-01-02");
        CreateLaunchRequest launchRequest02 = LaunchMother.getCreateLaunchRequest(BigDecimal.valueOf(200.0), "2021-01-05", "PIX", "PAID", "2021-01-06");
        CreateLaunchRequest launchRequest03 = LaunchMother.getCreateLaunchRequest(BigDecimal.valueOf(900.0), "2021-01-01", "PIX", "PENDING", null);
        CreateBookingRequest request = BookingMother.getCreateBookingRequest(traveler.getId(), "2021-01-01T10:00", "2021-01-15T18:00", amountTotal, "DIRECT", Arrays.asList(launchRequest01, launchRequest02,launchRequest03));

        when(bookingMapper.createRequestToEntity(any(CreateBookingRequest.class))).thenReturn(bookingToSave);

        try {
            bookingService.createBooking(request);
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
