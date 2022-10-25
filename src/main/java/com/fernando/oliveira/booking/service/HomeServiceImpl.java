package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.response.ReservedDateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeServiceImpl implements HomeService{

    @Autowired
    private BookingService bookingService;


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
}
