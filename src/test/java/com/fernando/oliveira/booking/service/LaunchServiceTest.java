package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.enums.PaymentTypeEnum;
import com.fernando.oliveira.booking.repository.LaunchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum.*;
import static com.fernando.oliveira.booking.mother.LaunchMother.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LaunchServiceTest {

    @InjectMocks
    private LaunchServiceImpl launchService;

    @Mock
    private LaunchRepository launchRepository;

    @Test
    void shouldReturnLaunchById(){

        Launch launch = getBooking01Launch01();
        when(launchRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(launch));
        Long launchId = 100L;
        Launch result = launchService.findById(launchId);

        then(result.getId()).isEqualTo( launchId);
        then(result.getAmount()).isEqualTo( BigDecimal.valueOf(500.0));
        then(result.getScheduleDate()).isEqualTo( LocalDate.of(2020, Month.DECEMBER, 1));
        then(result.getPaymentStatus()).isEqualTo( PAID);
        then(result.getPaymentDate()).isEqualTo( LocalDate.of(2020, Month.DECEMBER, 2));
        then(result.getPaymentType()).isEqualTo( PaymentTypeEnum.PIX);

    }
    @Test
    void shouldReturnNextLaunches(){

        when(launchRepository.findNextLaunches()).thenReturn(Arrays.asList(getBooking02Launch03(), getBooking03Launch01(), getBooking03Launch02(), getBooking04Launch01()));

        List<Launch> result = launchService.findNextLaunches();
        then(result.size()).isEqualTo(4);
        then(result.get(0).getPaymentStatus()).isEqualTo(PENDING);
        then(result.get(0).getId()).isEqualTo(105L);

        then(result.get(1).getPaymentStatus()).isEqualTo(PENDING);
        then(result.get(1).getId()).isEqualTo(106L);

        then(result.get(2).getPaymentStatus()).isEqualTo(PENDING);
        then(result.get(2).getId()).isEqualTo(107L);

        then(result.get(3).getPaymentStatus()).isEqualTo(TO_RECEIVE);
        then(result.get(3).getId()).isEqualTo(108L);

    }

    @Test
    void shouldUpdateLaunchAndReturnLaunchUpdated(){
        Launch launch = 
    }
}
