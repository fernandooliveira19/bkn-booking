package com.fernando.oliveira.booking.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PreviewBookingDto {

    private BigDecimal amountTotal;
    private Long rentDays;
}
