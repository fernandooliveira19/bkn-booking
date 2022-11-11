package com.fernando.oliveira.booking.integration;

import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentTypeEnum;
import com.fernando.oliveira.booking.domain.response.LaunchDetailResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = {"classpath:scripts/load-database.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:scripts/clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LaunchIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    private static final String LAUNCH_MAPPING = "/launches";

    private static final String NEXT_LAUNCHES = LAUNCH_MAPPING + "/next";


    @Test
    void shouldReturnNextLaunches(){
        ResponseEntity<LaunchDetailResponse[]> result = restTemplate
                .getForEntity(
                        NEXT_LAUNCHES, LaunchDetailResponse[].class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        LaunchDetailResponse [] response = result.getBody();

        assertThat(response.length).isEqualTo(4);

        assertThat(response[0].getId()).isEqualTo(105);
        assertThat(response[0].getAmount()).isEqualByComparingTo(BigDecimal.valueOf(1000.0));
        assertThat(response[0].getScheduleDate()).isEqualTo(LocalDate.of(2021, Month.JANUARY,8));
        assertThat(response[0].getPaymentType()).isEqualTo(PaymentTypeEnum.PIX);
        assertThat(response[0].getPaymentStatus()).isEqualTo(PaymentStatusEnum.PENDING);
        assertThat(response[0].getCheckIn()).isEqualTo(LocalDateTime.of(2021,Month.JANUARY,1, 10,00));
        assertThat(response[0].getTravelerName()).isEqualTo("Bianca Silva");

        assertThat(response[1].getId()).isEqualTo(106);
        assertThat(response[1].getAmount()).isEqualByComparingTo(BigDecimal.valueOf(1200.0));
        assertThat(response[1].getScheduleDate()).isEqualTo(LocalDate.of(2021, Month.JANUARY,12));
        assertThat(response[1].getPaymentType()).isEqualTo(PaymentTypeEnum.PIX);
        assertThat(response[1].getPaymentStatus()).isEqualTo(PaymentStatusEnum.PENDING);
        assertThat(response[1].getCheckIn()).isEqualTo(LocalDateTime.of(2021,Month.JANUARY,16, 10,00));
        assertThat(response[1].getTravelerName()).isEqualTo("Carlos Garcia");

        assertThat(response[2].getId()).isEqualTo(107);
        assertThat(response[2].getAmount()).isEqualByComparingTo(BigDecimal.valueOf(800.0));
        assertThat(response[2].getScheduleDate()).isEqualTo(LocalDate.of(2021, Month.JANUARY,16));
        assertThat(response[2].getPaymentType()).isEqualTo(PaymentTypeEnum.PIX);
        assertThat(response[2].getPaymentStatus()).isEqualTo(PaymentStatusEnum.PENDING);
        assertThat(response[2].getCheckIn()).isEqualTo(LocalDateTime.of(2021,Month.JANUARY,16, 10,00));
        assertThat(response[2].getTravelerName()).isEqualTo("Carlos Garcia");

        assertThat(response[3].getId()).isEqualTo(108);
        assertThat(response[3].getAmount()).isEqualByComparingTo(BigDecimal.valueOf(2500.0));
        assertThat(response[3].getScheduleDate()).isEqualTo(LocalDate.of(2021, Month.FEBRUARY,1));
        assertThat(response[3].getPaymentType()).isEqualTo(PaymentTypeEnum.SITE);
        assertThat(response[3].getPaymentStatus()).isEqualTo(PaymentStatusEnum.TO_RECEIVE);
        assertThat(response[3].getCheckIn()).isEqualTo(LocalDateTime.of(2021,Month.FEBRUARY,1, 10,00));
        assertThat(response[3].getTravelerName()).isEqualTo("David Souza");

    }

}
