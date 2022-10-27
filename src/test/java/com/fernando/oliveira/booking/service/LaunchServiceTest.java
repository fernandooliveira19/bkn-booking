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
        Long launchId = 100L;
        Launch result = launchService.findById(launchId);

        then(result.getId()).isEqualTo( launchId);
        then(result.getAmount()).isEqualTo( BigDecimal.valueOf(500.0));
        then(result.getScheduleDate()).isEqualTo( LocalDate.of(2021, Month.DECEMBER, 1));
        then(result.getPaymentStatus()).isEqualTo( PAID);
        then(result.getPaymentDate()).isEqualTo( LocalDate.of(2021, Month.DECEMBER, 2));
        then(result.getPaymentType()).isEqualTo( PaymentTypeEnum.PIX);

    }
    @Test
    void shouldReturnNextPendingLaunches(){

        Launch launch01 = getThirdLaunchFromSecondBooking();

        Launch launch02 = getFirstLaunchFromThirdBooking();

        Launch launch03 = getSecondLaunchFromThirdBooking();

        when(launchRepository.findNextLaunches()).thenReturn(Arrays.asList(launch01, launch02, launch03));
        when(launchMapper.launchToDetailLaunchResponse(launch01)).thenReturn(getLaunchDetailResponse(launch01));
        when(launchMapper.launchToDetailLaunchResponse(launch02)).thenReturn(getLaunchDetailResponse(launch02));
        when(launchMapper.launchToDetailLaunchResponse(launch03)).thenReturn(getLaunchDetailResponse(launch03));

        List<LaunchDetailResponse> result = launchService.findNextLaunches();

        then(result.get(0).getPaymentStatus()).isEqualTo(PENDING);
        then(result.get(0).getId()).isEqualTo(105L);

        then(result.get(1).getPaymentStatus()).isEqualTo(PENDING);
        then(result.get(1).getId()).isEqualTo(106L);

        then(result.get(2).getPaymentStatus()).isEqualTo(PENDING);
        then(result.get(2).getId()).isEqualTo(107L);

    }
}
