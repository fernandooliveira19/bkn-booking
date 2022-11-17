package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.dto.HomeBookingDto;
import com.fernando.oliveira.booking.domain.dto.HomeDto;
import com.fernando.oliveira.booking.domain.dto.HomeLaunchDto;
import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.ContractTypeEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.response.ReservedDateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HomeServiceImpl implements HomeService{

    @Autowired
    private BookingService bookingService;

    @Autowired
    private LaunchService launchService;


    public List<ReservedDateResponse> reservedDatesFromNextBookings() {

        List<Booking> nextBookings = bookingService.findNextBookings();

        List<ReservedDateResponse> response = new ArrayList<>();
        for(Booking booking : nextBookings){
            response.addAll(reservedDatesFromBooking(booking));
        }

        return response;

    }

    private List<ReservedDateResponse> reservedDatesFromBooking(Booking booking) {

        LocalDateTime checkIn = booking.getCheckIn();
        LocalDateTime checkOut = booking.getCheckOut();

        List<LocalDateTime> reservedDates = new ArrayList<>();

        for(LocalDateTime date = checkIn; !date.isAfter(checkOut); date = date.plusDays(1)){
            reservedDates.add(date);
        }

        return reservedDates.stream()
                .map( e -> extractReservedDate(e))
                .collect(Collectors.toList());


    }

    private ReservedDateResponse extractReservedDate(LocalDateTime localDateTime) {

        return ReservedDateResponse.builder()
                .year(localDateTime.getYear())
                .month(localDateTime.getMonthValue())
                .day(localDateTime.getDayOfMonth())
                .build();
    }

    @Override
    public HomeDto getHomeDetails() {

        return HomeDto.builder()
                .homeLaunch(getHomeLaunchDto())
                .homeBooking(getHomeBookingDto())
                .build();

    }

    private HomeLaunchDto getHomeLaunchDto(){

        List<Launch> nextLaunches = launchService.findNextLaunches();

        BigDecimal directOverDueAmount = nextLaunches
                .stream()
                .filter(e -> e.getPaymentStatus().equals(PaymentStatusEnum.PENDING))
                .filter(e -> e.getScheduleDate().isBefore(LocalDate.now()))
                .map(Launch::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer directOverDueQuantity = Math.toIntExact(nextLaunches
                .stream()
                .filter(e -> e.getPaymentStatus().equals(PaymentStatusEnum.PENDING))
                .filter(e -> e.getScheduleDate().isBefore(LocalDate.now()))
                .count());

        BigDecimal directToReceiveAmount = nextLaunches
                .stream()
                .filter(e -> e.getPaymentStatus().equals(PaymentStatusEnum.PENDING))
                .filter(e -> e.getScheduleDate().isAfter(LocalDate.now()))
                .map(Launch::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer directToReceiveQuantity = Math.toIntExact(nextLaunches
                .stream()
                .filter(e -> e.getPaymentStatus().equals(PaymentStatusEnum.PENDING))
                .filter(e -> e.getScheduleDate().isAfter(LocalDate.now()))
                .count());

        Integer siteQuantity = Math.toIntExact(nextLaunches
                .stream()
                .filter(e -> e.getPaymentStatus().equals(PaymentStatusEnum.TO_RECEIVE))
                .count());

        BigDecimal siteAmount = nextLaunches
                .stream()
                .filter(e -> e.getPaymentStatus().equals(PaymentStatusEnum.TO_RECEIVE))
                .map(Launch::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);





        return HomeLaunchDto.builder()
                .amountTotal(directOverDueAmount.add(directToReceiveAmount).add(siteAmount))
                .directOverdueAmount(directOverDueAmount)
                .directOverdueQuantity(directOverDueQuantity)
                .directToReceiveQuantity(directToReceiveQuantity)
                .directToReceiveAmount(directToReceiveAmount)
                .directOverdueSubTotal(directOverDueAmount.add(directToReceiveAmount))
                .siteQuantity(siteQuantity)
                .siteAmount(siteAmount)
                .build();

    }

    private HomeBookingDto getHomeBookingDto(){

        List<Booking> nextBookings = bookingService.findNextBookings();

        Map<BookingStatusEnum, Long> bookingStatus = nextBookings.stream()
                .collect(Collectors.groupingBy(Booking::getBookingStatus, Collectors.counting()));

        Map<PaymentStatusEnum, Long> paymentStatus = nextBookings.stream()
                .collect(Collectors.groupingBy(Booking::getPaymentStatus, Collectors.counting()));

        Map<ContractTypeEnum, Long> contractType = nextBookings.stream()
                .collect(Collectors.groupingBy(Booking::getContractType, Collectors.counting()));

        return HomeBookingDto.builder()
                .bookingTotal(nextBookings.size())
                .bookingReserved(bookingStatus.get(BookingStatusEnum.RESERVED).intValue())
                .bookingPreReserved(bookingStatus.get(BookingStatusEnum.PRE_RESERVED).intValue())
                .bookingPaid(paymentStatus.get(PaymentStatusEnum.PAID).intValue())
                .bookingPending(paymentStatus.get(PaymentStatusEnum.PENDING).intValue())
                .bookingToReceive(paymentStatus.get(PaymentStatusEnum.TO_RECEIVE).intValue())
                .bookingDirect(contractType.get(ContractTypeEnum.DIRECT).intValue())
                .bookingSite(contractType.get(ContractTypeEnum.SITE).intValue())
                .build();

    }


}
