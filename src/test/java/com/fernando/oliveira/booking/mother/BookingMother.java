package com.fernando.oliveira.booking.mother;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.entity.Traveler;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.ContractTypeEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.request.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingMother {

    private static final LocalDateTime BOOKING_01_CHECK_IN = LocalDateTime.of(2020, Month.DECEMBER, 15,10,0,0);
    private static final LocalDateTime BOOKING_01_CHECK_OUT = LocalDateTime.of(2020, Month.DECEMBER, 30,18,0,0);
    private static final BigDecimal BOOKING_01_TOTAL_AMOUNT = BigDecimal.valueOf(1000.0);
    private static final BigDecimal BOOKING_01_AMOUNT_PAID = BigDecimal.valueOf(1000.0);
    private static final BigDecimal BOOKING_01_AMOUNT_PENDING = BigDecimal.valueOf(0.0);
    private static final ContractTypeEnum BOOKING_01_CONTRACT_TYPE = ContractTypeEnum.DIRECT;
    private static final PaymentStatusEnum BOOKING_01_PAYMENT_STATUS = PaymentStatusEnum.PAID;
    private static final BookingStatusEnum BOOKING_01_BOOKING_STATUS = BookingStatusEnum.FINISHED;
    private static final Long BOOKING_01_BOOKING_ID = 10L;
    private static final Integer BOOKING_01_ADULTS = 3;
    private static final Integer BOOKING_01_CHILDREN = 2;
    private static final String BOOKING_01_OBSERVATION = "First booking";

    private static final LocalDateTime BOOKING_02_CHECK_IN = LocalDateTime.of(2021, Month.JANUARY, 1,10,0,0);
    private static final LocalDateTime BOOKING_02_CHECK_OUT = LocalDateTime.of(2021, Month.JANUARY, 15,18,0,0);
    private static final BigDecimal BOOKING_02_TOTAL_AMOUNT = BigDecimal.valueOf(1500.0);
    private static final BigDecimal BOOKING_02_AMOUNT_PAID = BigDecimal.valueOf(500.0);
    private static final BigDecimal BOOKING_02_AMOUNT_PENDING = BigDecimal.valueOf(1000.0);
    private static final ContractTypeEnum BOOKING_02_CONTRACT_TYPE = ContractTypeEnum.DIRECT;
    private static final PaymentStatusEnum BOOKING_02_PAYMENT_STATUS = PaymentStatusEnum.PENDING;
    private static final BookingStatusEnum BOOKING_02_BOOKING_STATUS = BookingStatusEnum.RESERVED;
    private static final Long BOOKING_02_BOOKING_ID = 20L;
    private static final Integer BOOKING_02_ADULTS = 4;
    private static final Integer BOOKING_02_CHILDREN = 3;
    private static final String BOOKING_02_OBSERVATION = "Second booking";

    private static final LocalDateTime BOOKING_03_CHECK_IN = LocalDateTime.of(2021, Month.JANUARY, 16,10,0,0);
    private static final LocalDateTime BOOKING_03_CHECK_OUT = LocalDateTime.of(2021, Month.JANUARY, 30,18,0,0);
    private static final BigDecimal BOOKING_03_TOTAL_AMOUNT = BigDecimal.valueOf(2000.0);
    private static final BigDecimal BOOKING_03_AMOUNT_PAID = BigDecimal.valueOf(0.0);
    private static final BigDecimal BOOKING_03_AMOUNT_PENDING = BigDecimal.valueOf(2000.0);
    private static final ContractTypeEnum BOOKING_03_CONTRACT_TYPE = ContractTypeEnum.DIRECT;
    private static final PaymentStatusEnum BOOKING_03_PAYMENT_STATUS = PaymentStatusEnum.PENDING;
    private static final BookingStatusEnum BOOKING_03_BOOKING_STATUS = BookingStatusEnum.PRE_RESERVED;
    private static final Long BOOKING_03_BOOKING_ID = 30L;
    private static final Integer BOOKING_03_ADULTS = 3;
    private static final Integer BOOKING_03_CHILDREN = 2;
    private static final String BOOKING_03_OBSERVATION = "Third booking";

    private static final LocalDateTime BOOKING_04_CHECK_IN = LocalDateTime.of(2021, Month.FEBRUARY, 1,10,0,0);
    private static final LocalDateTime BOOKING_04_CHECK_OUT = LocalDateTime.of(2021, Month.FEBRUARY, 15,18,0,0);
    private static final BigDecimal BOOKING_04_TOTAL_AMOUNT = BigDecimal.valueOf(2500.0);
    private static final BigDecimal BOOKING_04_AMOUNT_PAID = BigDecimal.valueOf(2500.0);
    private static final BigDecimal BOOKING_04_AMOUNT_PENDING = BigDecimal.valueOf(0.0);
    private static final ContractTypeEnum BOOKING_04_CONTRACT_TYPE = ContractTypeEnum.SITE;
    private static final PaymentStatusEnum BOOKING_04_PAYMENT_STATUS = PaymentStatusEnum.PAID;
    private static final BookingStatusEnum BOOKING_04_BOOKING_STATUS = BookingStatusEnum.RESERVED;
    private static final Long BOOKING_04_BOOKING_ID = 40L;
    private static final Integer BOOKING_04_ADULTS = 6;
    private static final Integer BOOKING_04_CHILDREN = 1;
    private static final String BOOKING_04_OBSERVATION = "Forth booking";

    private static final LocalDateTime BOOKING_05_CHECK_IN = LocalDateTime.of(2021, Month.FEBRUARY, 20,10,0,0);
    private static final LocalDateTime BOOKING_05_CHECK_OUT = LocalDateTime.of(2021, Month.FEBRUARY, 25,18,0,0);
    private static final BigDecimal BOOKING_05_TOTAL_AMOUNT = BigDecimal.valueOf(3000.0);
    private static final BigDecimal BOOKING_05_AMOUNT_PAID = BigDecimal.valueOf(0.0);
    private static final BigDecimal BOOKING_05_AMOUNT_PENDING = BigDecimal.valueOf(3000.0);
    private static final ContractTypeEnum BOOKING_05_CONTRACT_TYPE = ContractTypeEnum.SITE;
    private static final PaymentStatusEnum BOOKING_05_PAYMENT_STATUS = PaymentStatusEnum.CANCELED;
    private static final BookingStatusEnum BOOKING_05_BOOKING_STATUS = BookingStatusEnum.CANCELED;
    private static final Long BOOKING_05_BOOKING_ID = 50L;
    private static final Integer BOOKING_05_ADULTS = 2;
    private static final Integer BOOKING_05_CHILDREN = 0;
    private static final String BOOKING_05_OBSERVATION = "Fifth booking";


    private static final LocalDateTime CHECK_IN_01 = LocalDateTime.of(2021, Month.OCTOBER, 15,12,30,0);
    private static final LocalDateTime CHECK_OUT_01 = LocalDateTime.of(2021, Month.OCTOBER, 20,18,30,0);
    private static final BigDecimal TOTAL_AMOUNT_01 = BigDecimal.valueOf(1500.0);
    private static final BigDecimal AMOUNT_PAID_01 = BigDecimal.valueOf(1000.0);
    private static final BigDecimal AMOUNT_PENDING_01 = BigDecimal.valueOf(500.0);
    private static final Long BOOKING_ID_01 = 10L;

    private static final String REQUEST_CHECK_IN_01 = "2021-10-15T12:30";
    private static final String REQUEST_CHECK_OUT_01 = "2021-10-20T18:30";
    private static final BigDecimal REQUEST_TOTAL_AMOUNT_01 = BigDecimal.valueOf(1500.00);
    private static final Long REQUEST_TRAVELER_ID_01 = 1L;
    private static final String REQUEST_CONTRACT_TYPE = "DIRECT";
    private static final Integer REQUEST_ADULTS_ID_01 = 2;
    private static final Integer REQUEST_CHILDREN_ID_01 = 1;
    private static final String REQUEST_OBSERVATION = "Observation";

    private static final LocalDateTime CHECK_IN_02 = LocalDateTime.of(2021, Month.OCTOBER, 21,12,30,0);
    private static final LocalDateTime CHECK_OUT_02 = LocalDateTime.of(2021, Month.OCTOBER, 25,18,30,0);
    private static final BigDecimal TOTAL_AMOUNT_02 = BigDecimal.valueOf(1500.0);


    private static final Long BOOKING_ID_02 = 20L;
    private static final BigDecimal AMOUNT_PAID_02 = BigDecimal.valueOf(800.0);
    private static final BigDecimal AMOUNT_PENDING_02 = BigDecimal.valueOf(200.0);

    private static final LocalDateTime CHECK_IN_03 = LocalDateTime.of(2021, Month.OCTOBER, 26,12,30,0);
    private static final LocalDateTime CHECK_OUT_03 = LocalDateTime.of(2021, Month.OCTOBER, 30,18,30,0);
    private static final BigDecimal TOTAL_AMOUNT_03 = BigDecimal.valueOf(1500.0);
    private static final Long BOOKING_ID_03 = 30L;

    private static final Integer ADULTS = 4;
    private static final Integer CHILDREN = 2;


    public static Booking getFirstBooking(){
        return Booking.builder()
                .checkIn(CHECK_IN_01)
                .checkOut(CHECK_OUT_01)
                .amountTotal(TOTAL_AMOUNT_01)
                .adults(ADULTS)
                .children(CHILDREN)
                .traveler(getTraveler())
                .build();
    }
    public static Booking getFirstBookingSaved(){
        Launch launch01 = LaunchMother.getFirstLaunchFromFirstBooking();
        Launch launch02 = LaunchMother.getSecondLaunchFromFirstBooking();
        Launch launch03 = LaunchMother.getThirdLaunchFromFirstBooking();

        return Booking.builder()
                .id(BOOKING_01_BOOKING_ID)
                .insertDate(LocalDateTime.now())
                .checkIn(BOOKING_01_CHECK_IN)
                .checkOut(BOOKING_01_CHECK_OUT)
                .amountTotal(BOOKING_01_TOTAL_AMOUNT)
                .adults(BOOKING_01_ADULTS)
                .children(BOOKING_01_CHILDREN)
                .travelerName(TravelerMother.TRAVELER_01_NAME)
                .traveler(TravelerMother.getTravelerSaved01())
                .paymentStatus(BOOKING_01_PAYMENT_STATUS)
                .amountPaid(BOOKING_01_AMOUNT_PAID)
                .amountPending(BOOKING_01_AMOUNT_PENDING)
                .contractType(BOOKING_01_CONTRACT_TYPE)
                .observation(BOOKING_01_OBSERVATION)
                .bookingStatus(BOOKING_01_BOOKING_STATUS)
                .launchs(Arrays.asList(launch01, launch02, launch03))
                .build();
    }

    public static Booking getSecondBookingSaved(){
        Launch launch01 = LaunchMother.getFirstLaunchFromSecondBooking();
        Launch launch02 = LaunchMother.getSecondLaunchFromSecondBooking();
        Launch launch03 = LaunchMother.getThirdLaunchFromSecondBooking();

        return Booking.builder()
                .id(BOOKING_02_BOOKING_ID)
                .insertDate(LocalDateTime.now())
                .checkIn(BOOKING_02_CHECK_IN)
                .checkOut(BOOKING_02_CHECK_OUT)
                .amountTotal(BOOKING_02_TOTAL_AMOUNT)
                .adults(BOOKING_02_ADULTS)
                .children(BOOKING_02_CHILDREN)
                .travelerName(TravelerMother.TRAVELER_02_NAME)
                .traveler(TravelerMother.getTravelerSaved02())
                .paymentStatus(BOOKING_02_PAYMENT_STATUS)
                .amountPaid(BOOKING_02_AMOUNT_PAID)
                .amountPending(BOOKING_02_AMOUNT_PENDING)
                .contractType(BOOKING_02_CONTRACT_TYPE)
                .observation(BOOKING_02_OBSERVATION)
                .bookingStatus(BOOKING_02_BOOKING_STATUS)
                .launchs(Arrays.asList(launch01, launch02, launch03))
                .build();
    }


    public static Booking getThirdBookingSaved(){
        Launch launch01 = LaunchMother.getFirstLaunchFromThirdBooking();
        Launch launch02 = LaunchMother.getSecondLaunchFromThirdBooking();


        return Booking.builder()
                .id(BOOKING_03_BOOKING_ID)
                .insertDate(LocalDateTime.now())
                .checkIn(BOOKING_03_CHECK_IN)
                .checkOut(BOOKING_03_CHECK_OUT)
                .amountTotal(BOOKING_03_TOTAL_AMOUNT)
                .adults(BOOKING_03_ADULTS)
                .children(BOOKING_03_CHILDREN)
                .travelerName(TravelerMother.getTravelerSaved03().getName())
                .traveler(TravelerMother.getTravelerSaved03())
                .paymentStatus(BOOKING_03_PAYMENT_STATUS)
                .amountPaid(BOOKING_03_AMOUNT_PAID)
                .amountPending(BOOKING_03_AMOUNT_PENDING)
                .contractType(BOOKING_03_CONTRACT_TYPE)
                .observation(BOOKING_03_OBSERVATION)
                .bookingStatus(BOOKING_03_BOOKING_STATUS)
                .launchs(Arrays.asList(launch01, launch02))
                .build();
    }

    public static Booking getForthBookingSaved(){
        Launch launch01 = LaunchMother.getFirstLaunchFromForthBooking();

        return Booking.builder()
                .id(BOOKING_04_BOOKING_ID)
                .insertDate(LocalDateTime.now())
                .checkIn(BOOKING_04_CHECK_IN)
                .checkOut(BOOKING_04_CHECK_OUT)
                .amountTotal(BOOKING_04_TOTAL_AMOUNT)
                .adults(BOOKING_04_ADULTS)
                .children(BOOKING_04_CHILDREN)
                .travelerName(TravelerMother.getTravelerSaved04().getName())
                .traveler(TravelerMother.getTravelerSaved04())
                .paymentStatus(BOOKING_04_PAYMENT_STATUS)
                .amountPaid(BOOKING_04_AMOUNT_PAID)
                .amountPending(BOOKING_04_AMOUNT_PENDING)
                .contractType(BOOKING_04_CONTRACT_TYPE)
                .observation(BOOKING_04_OBSERVATION)
                .bookingStatus(BOOKING_04_BOOKING_STATUS)
                .launchs(Arrays.asList(launch01))
                .build();
    }

    public static Booking getFifthBookingSaved(){
        Launch launch01 = LaunchMother.getFirstLaunchFromFifthBooking();

        return Booking.builder()
                .id(BOOKING_05_BOOKING_ID)
                .insertDate(LocalDateTime.now())
                .checkIn(BOOKING_05_CHECK_IN)
                .checkOut(BOOKING_05_CHECK_OUT)
                .amountTotal(BOOKING_05_TOTAL_AMOUNT)
                .adults(BOOKING_05_ADULTS)
                .children(BOOKING_05_CHILDREN)
                .travelerName(TravelerMother.TRAVELER_01_NAME)
                .traveler(TravelerMother.getTravelerSaved01())
                .paymentStatus(BOOKING_05_PAYMENT_STATUS)
                .amountPaid(BOOKING_05_AMOUNT_PAID)
                .amountPending(BOOKING_05_AMOUNT_PENDING)
                .contractType(BOOKING_05_CONTRACT_TYPE)
                .observation(BOOKING_05_OBSERVATION)
                .bookingStatus(BOOKING_05_BOOKING_STATUS)
                .launchs(Arrays.asList(launch01))
                .build();
    }


    public static Booking getSecondBooking(){
        return Booking.builder()
                .checkIn(CHECK_IN_02)
                .checkOut(CHECK_OUT_02)
                .amountTotal(TOTAL_AMOUNT_02)
                .adults(ADULTS)
                .children(CHILDREN)
                .build();
    }

//    public static Booking getSecondBookingSaved(){
//
//        Launch launch01 = LaunchMother.getFirstLaunchFromSecondBooking();
//        Launch launch02 = LaunchMother.getSecondLaunchFromSecondBooking();
//
//        return Booking.builder()
//                .id(BOOKING_ID_02)
//                .insertDate(LocalDateTime.now())
//                .checkIn(CHECK_IN_02)
//                .checkOut(CHECK_OUT_02)
//                .amountTotal(TOTAL_AMOUNT_02)
//                .adults(ADULTS)
//                .children(CHILDREN)
//                .travelerName(TravelerMother.TRAVELER_02_NAME)
//                .traveler(TravelerMother.getTravelerSaved02())
//                .paymentStatus(PaymentStatusEnum.PENDING)
//                .amountPaid(AMOUNT_PAID_02)
//                .amountPending(AMOUNT_PENDING_02)
//                .launchs(Arrays.asList(launch01, launch02))
//                .build();
//    }

    public static Booking getThirdBooking(){
        return Booking.builder()
                .checkIn(CHECK_IN_03)
                .checkOut(CHECK_OUT_03)
                .amountTotal(TOTAL_AMOUNT_03)
                .adults(ADULTS)
                .children(CHILDREN)
                .build();
    }

    public static Booking getBookingToSave(LocalDateTime checkIn, LocalDateTime checkOut,
                                           BigDecimal amountTotal,
                                           Long travelerId,
                                           Integer adults,
                                           Integer children,
                                           List<Launch> launches,
                                           Traveler traveler) {
        return Booking.builder()
                .checkIn(checkIn)
                .checkOut(checkOut)
                .amountTotal(amountTotal)
                .adults(adults)
                .children(children)
                .launchs(launches)
                .traveler(traveler)
                .build();
    }

    public static Booking getBookingToUpdate(LocalDateTime checkIn, LocalDateTime checkOut,
                                           BigDecimal amountTotal,
                                           Long travelerId,
                                           Integer adults,
                                           Integer children,
                                           String observation,
                                           List<Launch> launchs,
                                           Traveler traveler) {
        return Booking.builder()
                .checkIn(checkIn)
                .checkOut(checkOut)
                .amountTotal(amountTotal)
                .adults(adults)
                .children(children)
                .observation(observation)
                .launchs(launchs)
                .traveler(traveler)
                .build();
    }

    public static Booking getBookingSaved(LocalDateTime checkIn, LocalDateTime checkOut, BigDecimal amountTotal, Long travelerId, Integer adults, Integer children, List<Launch> launchs, BookingStatusEnum bookingStatus, PaymentStatusEnum paymentStatus) {
        return Booking.builder()
                .id(1L)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .amountTotal(amountTotal)
                .adults(adults)
                .children(children)
                .launchs(launchs)
                .bookingStatus(bookingStatus)
                .paymentStatus(paymentStatus)
                .insertDate(LocalDateTime.of(2021, Month.AUGUST,22, 9, 39))
                .build();

    }

    public static Traveler getTraveler(){
        return Traveler.builder()
                .id(TravelerMother.TRAVELER_01_ID)
                .build();
    }

    public static CreateBookingRequest getCreateBookingRequest() {

        CreateBookingRequest request = new CreateBookingRequest();
        request.setCheckIn(REQUEST_CHECK_IN_01);
        request.setCheckOut(REQUEST_CHECK_OUT_01);
        request.setAmountTotal(REQUEST_TOTAL_AMOUNT_01);
        request.setTravelerId(REQUEST_TRAVELER_ID_01);
        request.setAdults(REQUEST_ADULTS_ID_01);
        request.setChildren(REQUEST_CHILDREN_ID_01);
        request.setObservation(REQUEST_OBSERVATION);
        request.setContractType(REQUEST_CONTRACT_TYPE);

        return request;
    }

    public static CreateBookingRequest getCreateBookingRequest(Long travelerId, String checkIn, String checkOut, BigDecimal amountTotal, String contractType, List<CreateLaunchRequest> launchs) {

        CreateBookingRequest request = new CreateBookingRequest();
        request.setCheckIn(checkIn);
        request.setCheckOut(checkOut);
        request.setAmountTotal(amountTotal);
        request.setTravelerId(travelerId);
        request.setLaunchs(launchs);
        request.setContractType(contractType);


        return request;
    }

    public static UpdateBookingRequest getUpdateBookingRequest(Long travelerId, String checkIn, String checkOut, BigDecimal amountTotal, List<UpdateLaunchRequest> launchs) {
        UpdateBookingRequest request = new UpdateBookingRequest();
        request.setCheckIn(checkIn);
        request.setCheckOut(checkOut);
        request.setAmountTotal(amountTotal);
        request.setTravelerId(travelerId);
        request.setLaunchs(launchs);


        return request;
    }

    public static List<Booking> getNextBookings(){
        return Arrays.asList(getFirstBookingSaved(), getSecondBookingSaved());
    }
}
