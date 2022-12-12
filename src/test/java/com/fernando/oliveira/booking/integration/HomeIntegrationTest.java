package com.fernando.oliveira.booking.integration;

import com.fernando.oliveira.booking.domain.response.HomeResponse;
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

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = {"classpath:scripts/load-database.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:scripts/clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class HomeIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private static final String HOME_MAPPING = "/home";

    @Test
    void shouldReturnHomeDetails() {

        ResponseEntity<HomeResponse> result = restTemplate
                .getForEntity(
                        HOME_MAPPING,
                        HomeResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getHomeLaunch().getAmountTotal()).isEqualByComparingTo(BigDecimal.valueOf(5500.0));
        assertThat(result.getBody().getHomeLaunch().getDirectOverdueAmount()).isEqualByComparingTo(BigDecimal.valueOf(3000.0));
        assertThat(result.getBody().getHomeLaunch().getDirectOverdueQuantity()).isEqualTo(3);
        assertThat(result.getBody().getHomeLaunch().getDirectOverdueSubTotal()).isEqualByComparingTo(BigDecimal.valueOf(3000.0));
        assertThat(result.getBody().getHomeLaunch().getDirectToReceiveAmount()).isEqualByComparingTo(BigDecimal.valueOf(0.0));
        assertThat(result.getBody().getHomeLaunch().getDirectToReceiveQuantity()).isEqualTo(0);
        assertThat(result.getBody().getHomeLaunch().getSiteAmount()).isEqualByComparingTo(BigDecimal.valueOf(2500.0));
        assertThat(result.getBody().getHomeLaunch().getSiteQuantity()).isEqualTo(1);

        assertThat(result.getBody().getHomeBooking().getBookingTotal()).isEqualTo(4);
        assertThat(result.getBody().getHomeBooking().getBookingReserved()).isEqualTo(3);
        assertThat(result.getBody().getHomeBooking().getBookingPreReserved()).isEqualTo(1);
        assertThat(result.getBody().getHomeBooking().getBookingPaid()).isEqualTo(1);
        assertThat(result.getBody().getHomeBooking().getBookingPending()).isEqualTo(2);
        assertThat(result.getBody().getHomeBooking().getBookingToReceive()).isEqualTo(1);
        assertThat(result.getBody().getHomeBooking().getBookingSite()).isEqualTo(1);
        assertThat(result.getBody().getHomeBooking().getBookingDirect()).isEqualTo(3);

    }

}
