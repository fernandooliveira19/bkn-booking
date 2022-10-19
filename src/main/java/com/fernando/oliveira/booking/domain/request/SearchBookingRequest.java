package com.fernando.oliveira.booking.domain.request;

import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.ContractTypeEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SearchBookingRequest {

    private LocalDateTime date;
    private PaymentStatusEnum paymentStatus;
    private BookingStatusEnum bookingStatus;
    private ContractTypeEnum contractType;
}
