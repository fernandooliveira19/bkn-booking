package com.fernando.oliveira.booking.domain.mapper;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.request.CreateBookingRequest;
import com.fernando.oliveira.booking.domain.request.UpdateBookingRequest;
import com.fernando.oliveira.booking.domain.response.DetailBookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {LaunchMapper.class})
public interface BookingMapper {

    String LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";

    @Mapping(source = "checkIn", target = "checkIn", qualifiedByName = "formatLocalDateTime")
    @Mapping(source = "checkOut", target = "checkOut", qualifiedByName = "formatLocalDateTime")
    @Mapping(source = "travelerId", target = "traveler.id")
    @Mapping(source = "totalAmount", target = "totalAmount", qualifiedByName = "convertStringToBigDecimal")
    Booking createRequestToEntity(CreateBookingRequest request);

    @Mapping(source = "traveler.id", target = "travelerId")
    DetailBookingResponse bookingToDetailBookingResponse(Booking booking);

    @Mapping(source = "travelerId", target = "traveler.id")
    @Mapping(source = "checkIn", target = "checkIn", qualifiedByName = "formatLocalDateTime")
    @Mapping(source = "checkOut", target = "checkOut", qualifiedByName = "formatLocalDateTime")
    @Mapping(source = "totalAmount", target = "totalAmount", qualifiedByName = "convertStringToBigDecimal")
    Booking updateRequestToEntity(UpdateBookingRequest request);

    @Named("formatLocalDateTime")
    static LocalDateTime formatLocalDateTime(String date){

        return LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Named("convertStringToBigDecimal")
    static BigDecimal convertStringToBigDecimal(String value) throws ParseException {
        value = value.replace(".","");
        value = value.replace(",", ".");
        value = value.trim();
        return BigDecimal.valueOf(Double.parseDouble(value));
    }
}
