package com.fernando.oliveira.booking.domain.dto;

import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookingDto {

    private PaymentStatusEnum paymentStatus;
    private BookingStatusEnum bookingStatus;
    private BigDecimal totalAmount;
    private BigDecimal amountPending;
}
