package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.enums.PaymentTypeEnum;
import com.fernando.oliveira.booking.domain.mapper.LaunchMapper;
import com.fernando.oliveira.booking.domain.response.LaunchDetailResponse;
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

import static com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum.PAID;
import static com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum.PENDING;
import static com.fernando.oliveira.booking.mother.LaunchMother.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LaunchServiceTest {

    @InjectMocks
    private LaunchServiceImpl launchService;

    @Mock
    private LaunchRepository launchRepository;

    @Mock
    private LaunchMapper launchMapper;

    @Test
    void shouldReturnLaunchById(){

        Launch launch = getFirstLaunchFromFirstBooking();
        when(launchRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(launch));
        Long launchId = 10L;
        Launch result = launchService.findById(launchId);

        then(result.getId()).isEqualTo( launchId);
        then(result.getAmount()).isEqualTo( BigDecimal.valueOf(1000.0));
        then(result.getScheduleDate()).isEqualTo( LocalDate.of(2021, Month.OCTOBER, 10));
        then(result.getPaymentStatus()).isEqualTo( PAID);
        then(result.getPaymentDate()).isEqualTo( LocalDate.of(2021, Month.OCTOBER, 10));
        then(result.getPaymentType()).isEqualTo( PaymentTypeEnum.PIX);

    }
    @Test
    void shouldReturnNextPendingLaunches(){

//       2021-10-10
        Launch launch01 = getSecondLaunchFromFirstBooking();
        launch01.setId(10L);

//      2021, 10, 15
        Launch launch02 = getThirdLaunchFromFirstBooking();
        launch02.setId(20L);

//      2021-02-27
        Launch launch03 = getSecondLaunchFromSecondBooking();
        launch03.setId(30L);

        when(launchRepository.findNextLaunches()).thenReturn(Arrays.asList(launch01, launch02, launch03));
        when(launchMapper.launchToDetailLaunchResponse(launch01)).thenReturn(getLaunchDetailResponse(launch01));
        when(launchMapper.launchToDetailLaunchResponse(launch02)).thenReturn(getLaunchDetailResponse(launch02));
        when(launchMapper.launchToDetailLaunchResponse(launch03)).thenReturn(getLaunchDetailResponse(launch03));

        List<LaunchDetailResponse> result = launchService.findNextLaunches();

        then(result.get(0).getPaymentStatus()).isEqualTo(PENDING);
        then(result.get(0).getId()).isEqualTo(10L);

        then(result.get(1).getPaymentStatus()).isEqualTo(PENDING);
        then(result.get(1).getId()).isEqualTo(20L);

        then(result.get(2).getPaymentStatus()).isEqualTo(PENDING);
        then(result.get(2).getId()).isEqualTo(30L);

    }
}
