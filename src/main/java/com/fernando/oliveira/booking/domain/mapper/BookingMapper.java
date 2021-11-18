package com.fernando.oliveira.booking.domain.mapper;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.request.CreateBookingRequest;
import com.fernando.oliveira.booking.domain.request.UpdateBookingRequest;
import com.fernando.oliveira.booking.domain.response.DetailBookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

    @Mapping(source = "checkIn", target = "checkIn", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(source = "checkOut", target = "checkOut", dateFormat = "yyyy-MM-dd HH:mm")
    Booking createRequestToEntity(CreateBookingRequest request);

    DetailBookingResponse bookingToDetailBookingResponse(Booking booking);

    Booking updateRequestToEntity(UpdateBookingRequest request);
}
