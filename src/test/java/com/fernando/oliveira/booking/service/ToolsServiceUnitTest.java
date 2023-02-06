package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.dto.PreviewBookingDto;
import com.fernando.oliveira.booking.domain.enums.ExceptionMessageEnum;
import com.fernando.oliveira.booking.exception.ToolsException;
import com.fernando.oliveira.booking.service.impl.ToolsServiceImpl;
import com.fernando.oliveira.booking.utils.MessageUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ToolsServiceUnitTest {

    @InjectMocks
    private ToolsServiceImpl toolsService;

    @Mock
    private MessageUtils messageUtils;

    @Test
    void givenCheckInAndCheckOutWhenCalculateDailiesThenReturnDailies(){
        LocalDateTime checkIn = LocalDateTime.of(2023, Month.FEBRUARY,5,10,0);
        LocalDateTime checkOut = LocalDateTime.of(2023, Month.FEBRUARY,11,18,0);
        Long result = toolsService.rentDays(checkIn, checkOut);

        then(result).isEqualTo(6);

    }

    @Test
    void givenCheckInAndCheckOutWhenCalculateDailiesThenReturnIncompleteDaily(){
        LocalDateTime checkIn = LocalDateTime.of(2023, Month.FEBRUARY,5,10,0);
        LocalDateTime checkOut = LocalDateTime.of(2023, Month.FEBRUARY,11,9,59);
        Long result = toolsService.rentDays(checkIn, checkOut);

        then(result).isEqualTo(5);

    }
    @Test
    void givenCheckInAndCheckOutInSameDayThenReturnIncompleteDaily(){
        LocalDateTime checkIn = LocalDateTime.of(2023, Month.FEBRUARY,5,10,0);
        LocalDateTime checkOut = LocalDateTime.of(2023, Month.FEBRUARY,6,9,59);
        Long result = toolsService.rentDays(checkIn, checkOut);

        then(result).isEqualTo(1);

    }

    @Test
    void givenCheckInAfterCheckoutThenReturnExceptionMessage(){
        LocalDateTime checkIn = LocalDateTime.of(2023, Month.FEBRUARY,11,10,0);
        LocalDateTime checkOut = LocalDateTime.of(2023, Month.FEBRUARY,11,9,59);

        String exceptionMessage ="Data de check-in não deve ser posterior a de check-out";
        when(messageUtils.getMessage(ExceptionMessageEnum.TOOLS_CHECK_IN_AFTER_CHECK_OUT_ERROR)).thenReturn(exceptionMessage);
        try {
            Long result = toolsService.rentDays(checkIn, checkOut);
            fail(exceptionMessage, ToolsException.class );
        }catch (ToolsException e){
            then(e.getMessage()).isEqualTo(exceptionMessage);
        }
    }

    @Test
    void givenRentDaysAndAmountThenAverageValue(){

        BigDecimal amount = BigDecimal.valueOf(1600.0);
        Long rentDays = Long.valueOf(6);

        BigDecimal result = toolsService.averageValue(rentDays,amount);

        then(result).isEqualByComparingTo(BigDecimal.valueOf(266.67));

    }

    @Test
    void givenZeroAmountThenReturnAverage(){

        BigDecimal amount = BigDecimal.valueOf(0.0);
        Long rentDays = Long.valueOf(1);

        BigDecimal result = toolsService.averageValue(rentDays,amount);

        then(result).isEqualByComparingTo(BigDecimal.valueOf(0.0));

    }



    @Test
    void givenRangeDateAndDailyValueThenReturnTotalAmount(){
        LocalDateTime checkIn = LocalDateTime.of(2023, Month.FEBRUARY,5,10,0);
        LocalDateTime checkOut = LocalDateTime.of(2023, Month.FEBRUARY,11,18,0);
        BigDecimal dailyRate = BigDecimal.valueOf(350.0);
        BigDecimal cleaningFee = BigDecimal.valueOf(120.0);

        PreviewBookingDto result = toolsService.calculateAmount(checkIn,checkOut,dailyRate, cleaningFee);

        then(result.getAmountTotal()).isEqualByComparingTo(BigDecimal.valueOf(2220.0));
        then(result.getRentDays()).isEqualTo(6);

    }
}
