package com.fernando.oliveira.booking.mother;

import com.fernando.oliveira.booking.domain.dto.HomeBookingDto;
import com.fernando.oliveira.booking.domain.dto.HomeDto;
import com.fernando.oliveira.booking.domain.dto.HomeLaunchDto;
import com.fernando.oliveira.booking.domain.response.HomeBookingResponse;
import com.fernando.oliveira.booking.domain.response.HomeLaunchResponse;
import com.fernando.oliveira.booking.domain.response.HomeResponse;
import com.fernando.oliveira.booking.domain.response.ReservedDateResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HomeMother {

    private static final BigDecimal LAUNCH_AMOUNT_TOTAL = BigDecimal.valueOf(5500.0);
    private static final Integer LAUNCH_DIRECT_OVERDUE_QUANTITY = 3;
    private static final BigDecimal LAUNCH_DIRECT_OVERDUE_AMOUNT = BigDecimal.valueOf(3500.0);
    private static final Integer DIRECT_TO_RECEIVE_QUANTITY = 0;
    private static final BigDecimal LAUNCH_DIRECT_TO_RECEIVE_AMOUNT = BigDecimal.valueOf(0.0);
    private static final BigDecimal LAUNCH_DIRECT_SUB_TOTAL = BigDecimal.valueOf(3500.0);
    private static final Integer LAUNCH_SITE_QUANTITY = 1;
    private static final BigDecimal LAUNCH_SITE_AMOUNT = BigDecimal.valueOf(2500.0);

    private static final Integer BOOKING_RESERVED = 3;
    private static final Integer BOOKING_PRE_RESERVED = 1;
    private static final Integer BOOKING_PAID = 1;
    private static final Integer BOOKING_PENDING = 2;
    private static final Integer BOOKING_TO_RECEIVE = 1;
    private static final Integer BOOKING_DIRECT = 4;
    private static final Integer BOOKING_SITE = 1;
    private static final Integer BOOKING_TOTAL = 4;



    public static List<ReservedDateResponse> getReservedDatesResponse() {

        ReservedDateResponse day01 = ReservedDateResponse
                .builder().year(2022).month(10).day(15).build();
        ReservedDateResponse day02 = ReservedDateResponse
                .builder().year(2022).month(10).day(16).build();
        ReservedDateResponse day03 = ReservedDateResponse
                .builder().year(2022).month(10).day(17).build();
        ReservedDateResponse day04 = ReservedDateResponse
                .builder().year(2022).month(10).day(18).build();
        ReservedDateResponse day05 = ReservedDateResponse
                .builder().year(2022).month(10).day(19).build();
        ReservedDateResponse day06 = ReservedDateResponse
                .builder().year(2022).month(10).day(20).build();

        return Arrays.asList(day01, day02, day03, day04, day05, day06);
    }

    public static HomeResponse getHomeResponse() {
        return HomeResponse.builder()
                .homeLaunch(getLaunchResponse())
                .homeBooking(getHomeBookingResponse())
                .build();
    }

    private static HomeBookingResponse getHomeBookingResponse() {
        return HomeBookingResponse.builder()
                .bookingDirect(BOOKING_DIRECT)
                .bookingPaid(BOOKING_PAID)
                .bookingPending(BOOKING_PENDING)
                .bookingReserved(BOOKING_RESERVED)
                .bookingPreReserved(BOOKING_PRE_RESERVED)
                .bookingSite(BOOKING_SITE)
                .bookingToReceive(BOOKING_TO_RECEIVE)
                .bookingTotal(BOOKING_TOTAL)
                .build();
    }

    private static HomeLaunchResponse getLaunchResponse() {
        return HomeLaunchResponse.builder()
                .amountTotal(LAUNCH_AMOUNT_TOTAL)
                .directOverdueAmount(LAUNCH_DIRECT_OVERDUE_AMOUNT)
                .directOverdueQuantity(LAUNCH_DIRECT_OVERDUE_QUANTITY)
                .directOverdueSubTotal(LAUNCH_DIRECT_SUB_TOTAL)
                .directToReceiveAmount(LAUNCH_DIRECT_TO_RECEIVE_AMOUNT)
                .directToReceiveQuantity(DIRECT_TO_RECEIVE_QUANTITY)
                .siteQuantity(LAUNCH_SITE_QUANTITY)
                .siteAmount(LAUNCH_SITE_AMOUNT)
                .build();
    }

    public static HomeDto getHomeDto() {
        return HomeDto.builder()
                .homeBooking(getHomeBookingDto())
                .homeLaunch(getHomeLaunchDto())
                .build();
    }

    private static HomeLaunchDto getHomeLaunchDto() {
        return HomeLaunchDto.builder()
                .amountTotal(LAUNCH_AMOUNT_TOTAL)
                .directOverdueAmount(LAUNCH_DIRECT_OVERDUE_AMOUNT)
                .directOverdueQuantity(LAUNCH_DIRECT_OVERDUE_QUANTITY)
                .directOverdueSubTotal(LAUNCH_DIRECT_SUB_TOTAL)
                .directToReceiveAmount(LAUNCH_DIRECT_TO_RECEIVE_AMOUNT)
                .directToReceiveQuantity(DIRECT_TO_RECEIVE_QUANTITY)
                .siteQuantity(LAUNCH_SITE_QUANTITY)
                .siteAmount(LAUNCH_SITE_AMOUNT)
                .build();
    }

    private static HomeBookingDto getHomeBookingDto() {
        return HomeBookingDto.builder()
                .bookingDirect(BOOKING_DIRECT)
                .bookingPaid(BOOKING_PAID)
                .bookingPending(BOOKING_PENDING)
                .bookingReserved(BOOKING_RESERVED)
                .bookingPreReserved(BOOKING_PRE_RESERVED)
                .bookingSite(BOOKING_SITE)
                .bookingToReceive(BOOKING_TO_RECEIVE)
                .bookingTotal(BOOKING_TOTAL)
                .build();
    }
}
