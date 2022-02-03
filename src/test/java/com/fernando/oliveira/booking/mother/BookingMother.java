package com.fernando.oliveira.booking.mother;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.entity.Traveler;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.request.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class BookingMother {
    private BookingMother(){

    }

    private static final LocalDateTime CHECK_IN_01 = LocalDateTime.of(2021, Month.OCTOBER, 15,12,30,0);
    private static final LocalDateTime CHECK_OUT_01 = LocalDateTime.of(2021, Month.OCTOBER, 20,18,30,0);
    private static final BigDecimal TOTAL_AMOUNT_01 = BigDecimal.valueOf(1500.0);
    private static final Long TRAVELER_ID_01 = 1L;
    private static final Long BOOKING_ID_01 = 10L;

    private static final String REQUEST_CHECK_IN_01 = "2021-10-15T12:30";
    private static final String REQUEST_CHECK_OUT_01 = "2021-10-20T18:30";
    private static final BigDecimal REQUEST_TOTAL_AMOUNT_01 = BigDecimal.valueOf(1500.0);
    private static final Long REQUEST_TRAVELER_ID_01 = 1L;
    private static final String REQUEST_CONTRACT_TYPE = "DIRECT";
    private static final Integer REQUEST_ADULTS_ID_01 = 2;
    private static final Integer REQUEST_CHILDREN_ID_01 = 1;
    private static final String REQUEST_OBSERVATION = "Observation";

    private static final LocalDateTime CHECK_IN_02 = LocalDateTime.of(2021, Month.OCTOBER, 21,12,30,0);
    private static final LocalDateTime CHECK_OUT_02 = LocalDateTime.of(2021, Month.OCTOBER, 25,18,30,0);
    private static final BigDecimal TOTAL_AMOUNT_02 = BigDecimal.valueOf(1500.0);
    private static final Long TRAVELER_ID_02 = 2L;
    private static final Long BOOKING_ID_02 = 20L;

    private static final LocalDateTime CHECK_IN_03 = LocalDateTime.of(2021, Month.OCTOBER, 26,12,30,0);
    private static final LocalDateTime CHECK_OUT_03 = LocalDateTime.of(2021, Month.OCTOBER, 30,18,30,0);
    private static final BigDecimal TOTAL_AMOUNT_03 = BigDecimal.valueOf(1500.0);
    private static final Long TRAVELER_ID_03 = 3L;
    private static final Long BOOKING_ID_03 = 30L;

    private static final Integer ADULTS = 4;
    private static final Integer CHILDREN = 2;

    private static final Long TRAVELER_ID = 1L;

    public static Booking getFirstBooking(){
        return Booking.builder()
                .checkIn(CHECK_IN_01)
                .checkOut(CHECK_OUT_01)
                .totalAmount(TOTAL_AMOUNT_01)
                .adults(ADULTS)
                .children(CHILDREN)
                .traveler(getTraveler())
                .build();
    }
    public static Booking getFirstBookingSaved(){
        return Booking.builder()
                .id(BOOKING_ID_01)
                .insertDate(LocalDateTime.now())
                .checkIn(CHECK_IN_01)
                .checkOut(CHECK_OUT_01)
                .totalAmount(TOTAL_AMOUNT_01)
                .adults(ADULTS)
                .children(CHILDREN)
                .build();
    }

    public static Booking getSecondBooking(){
        return Booking.builder()
                .checkIn(CHECK_IN_02)
                .checkOut(CHECK_OUT_02)
                .totalAmount(TOTAL_AMOUNT_02)
                .adults(ADULTS)
                .children(CHILDREN)
                .build();
    }

    public static Booking getThirdBooking(){
        return Booking.builder()
                .checkIn(CHECK_IN_03)
                .checkOut(CHECK_OUT_03)
                .totalAmount(TOTAL_AMOUNT_03)
                .adults(ADULTS)
                .children(CHILDREN)
                .build();
    }

    public static Booking getBookingToSave(LocalDateTime checkIn, LocalDateTime checkOut,
                                           BigDecimal totalAmount,
                                           Long travelerId,
                                           Integer adults,
                                           Integer children,
                                           List<Launch> launchs,
                                           Traveler traveler) {
        return Booking.builder()
                .checkIn(checkIn)
                .checkOut(checkOut)
                .totalAmount(totalAmount)
                .adults(adults)
                .children(children)
                .launchs(launchs)
                .traveler(traveler)
                .build();
    }

    public static Booking getBookingSaved(LocalDateTime checkIn, LocalDateTime checkOut, BigDecimal totalAmount, Long travelerId, Integer adults, Integer children, List<Launch> launchs, BookingStatusEnum bookingStatus, PaymentStatusEnum paymentStatus) {
        return Booking.builder()
                .id(1L)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .totalAmount(totalAmount)
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
                .id(TRAVELER_ID)
                .build();
    }

    public static CreateBookingRequest getCreateBookingRequest() {

        CreateBookingRequest request = new CreateBookingRequest();
        request.setCheckIn(REQUEST_CHECK_IN_01);
        request.setCheckOut(REQUEST_CHECK_OUT_01);
        request.setTotalAmount(REQUEST_TOTAL_AMOUNT_01);
        request.setTravelerId(REQUEST_TRAVELER_ID_01);
        request.setAdults(REQUEST_ADULTS_ID_01);
        request.setChildren(REQUEST_CHILDREN_ID_01);
        request.setObservation(REQUEST_OBSERVATION);
        request.setContractType(REQUEST_CONTRACT_TYPE);

        return request;
    }

    public static CreateBookingRequest getCreateBookingRequest(Long travelerId, String checkIn, String checkOut, BigDecimal totalAmount, String contractType, List<CreateLaunchRequest> launchs) {

        CreateBookingRequest request = new CreateBookingRequest();
        request.setCheckIn(checkIn);
        request.setCheckOut(checkOut);
        request.setTotalAmount(totalAmount);
        request.setTravelerId(travelerId);
        request.setLaunchs(launchs);
        request.setContractType(contractType);


        return request;
    }

    public static UpdateBookingRequest getUpdateBookingRequest(Long travelerId, String checkIn, String checkOut, BigDecimal totalAmount, List<UpdateLaunchRequest> launchs) {
        UpdateBookingRequest request = new UpdateBookingRequest();
        request.setCheckIn(checkIn);
        request.setCheckOut(checkOut);
        request.setTotalAmount(totalAmount);
        request.setTravelerId(travelerId);
        request.setLaunchs(launchs);


        return request;
    }
}
