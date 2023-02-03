package com.fernando.oliveira.booking.service.impl;

import com.fernando.oliveira.booking.domain.dto.PreviewBookingDto;
import com.fernando.oliveira.booking.domain.enums.ExceptionMessageEnum;
import com.fernando.oliveira.booking.exception.ToolsException;
import com.fernando.oliveira.booking.service.ToolsService;
import com.fernando.oliveira.booking.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class ToolsServiceImpl implements ToolsService {

    @Autowired
    MessageUtils messageUtils;

    @Override
    public Long rentDays(LocalDateTime checkIn, LocalDateTime checkOut) {

        if(checkIn.isAfter(checkOut))
            throw new ToolsException(messageUtils.getMessage(ExceptionMessageEnum.TOOLS_CHECK_IN_AFTER_CHECK_OUT_ERROR));

        long rentDays = checkIn.until(checkOut, ChronoUnit.DAYS);

        if(rentDays == 0)
            return Long.valueOf(1);

        return rentDays;
    }

    @Override
    public BigDecimal averageValue(Long rentDays, BigDecimal amount) {

       return amount.divide(BigDecimal.valueOf(rentDays), 2 , RoundingMode.HALF_UP);

    }

    @Override
    public PreviewBookingDto calculateAmount(LocalDateTime checkIn, LocalDateTime checkOut, BigDecimal dailyValue) {

        Long rentDays = this.rentDays(checkIn, checkOut);
        return PreviewBookingDto.builder()
                .rentDays(rentDays)
                .amountTotal(dailyValue.multiply(BigDecimal.valueOf(rentDays)))
                .build();
    }
}
