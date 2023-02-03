package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.dto.PreviewBookingDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ToolsService {

    Long rentDays(LocalDateTime checkIn, LocalDateTime checkOut);

    BigDecimal averageValue(Long rentDays, BigDecimal amount);

    PreviewBookingDto calculateAmount(LocalDateTime checkIn, LocalDateTime checkOut, BigDecimal dailyValue);
}
