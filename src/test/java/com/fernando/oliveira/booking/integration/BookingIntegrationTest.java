package com.fernando.oliveira.booking.integration;

import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentTypeEnum;
import com.fernando.oliveira.booking.domain.request.CreateBookingRequest;
import com.fernando.oliveira.booking.domain.request.LaunchRequest;
import com.fernando.oliveira.booking.domain.response.DetailBookingResponse;
import com.fernando.oliveira.booking.domain.response.ExceptionResponse;
import com.fernando.oliveira.booking.mother.BookingMother;
import com.fernando.oliveira.booking.mother.LaunchMother;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = {"classpath:scripts/load-database.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:scripts/clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookingIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private static final String BOOKING_MAPPING = "/v1/bookings";


    @Test
    void shouldReturnAllBookings(){
        ResponseEntity<DetailBookingResponse[]> result = restTemplate
                .getForEntity(
                        BOOKING_MAPPING, DetailBookingResponse[].class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        DetailBookingResponse [] response = result.getBody();

        assertThat(response.length).isEqualTo(1);

    }

    @Test
    void shouldReturnBookingDetailsById(){

        ResponseEntity<DetailBookingResponse> result = restTemplate
                .getForEntity(
                        BOOKING_MAPPING +"/10" , DetailBookingResponse.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        DetailBookingResponse response = result.getBody();

        assertThat(response.getId()).isEqualTo(10);
        assertThat(response.getTravelerId()).isEqualTo(1);

        assertThat(response.getAdults()).isEqualTo(5);
        assertThat(response.getChildren()).isEqualTo(2);
        assertThat(response.getBookingStatus()).isEqualTo(BookingStatusEnum.RESERVED);
        assertThat(response.getPaymentStatus()).isEqualTo(PaymentStatusEnum.PENDING);
        assertThat(response.getCheckIn()).isEqualTo(LocalDateTime.of(2021, 10,01,10,0 ));
        assertThat(response.getCheckOut()).isEqualTo(LocalDateTime.of(2021, 10,30,18,30 ));
        assertThat(response.getTotalAmount()).isEqualByComparingTo(BigDecimal.valueOf(1500.00));
        assertThat(response.getAmountPending()).isEqualByComparingTo(BigDecimal.valueOf(200.00));

        assertThat(response.getLaunchs().get(0).getId()).isEqualTo(100);
        assertThat(response.getLaunchs().get(0).getAmount()).isEqualByComparingTo(BigDecimal.valueOf(1000.00));
        assertThat(response.getLaunchs().get(0).getScheduleDate()).isEqualTo(LocalDate.of(2021,10,10));
        assertThat(response.getLaunchs().get(0).getPaymentType()).isEqualTo(PaymentTypeEnum.PIX);
        assertThat(response.getLaunchs().get(0).getPaymentStatus()).isEqualTo(PaymentStatusEnum.PAID);
        assertThat(response.getLaunchs().get(0).getPaymentDate()).isEqualTo(LocalDate.of(2021,10,10));

        assertThat(response.getLaunchs().get(1).getId()).isEqualTo(101);
        assertThat(response.getLaunchs().get(1).getAmount()).isEqualByComparingTo(BigDecimal.valueOf(300.00));
        assertThat(response.getLaunchs().get(1).getScheduleDate()).isEqualTo(LocalDate.of(2021,10,20));
        assertThat(response.getLaunchs().get(1).getPaymentType()).isEqualTo(PaymentTypeEnum.TRANSFER);
        assertThat(response.getLaunchs().get(1).getPaymentStatus()).isEqualTo(PaymentStatusEnum.PAID);
        assertThat(response.getLaunchs().get(1).getPaymentDate()).isEqualTo(LocalDate.of(2021,10,20));

        assertThat(response.getLaunchs().get(2).getId()).isEqualTo(102);
        assertThat(response.getLaunchs().get(2).getAmount()).isEqualByComparingTo(BigDecimal.valueOf(200.00));
        assertThat(response.getLaunchs().get(2).getScheduleDate()).isEqualTo(LocalDate.of(2021,10,30));
        assertThat(response.getLaunchs().get(2).getPaymentType()).isEqualTo(PaymentTypeEnum.LOCAL);
        assertThat(response.getLaunchs().get(2).getPaymentStatus()).isEqualTo(PaymentStatusEnum.PENDING);
        assertThat(response.getLaunchs().get(2).getPaymentDate()).isNull();

    }
    @Test
    void shouldCreateReservedBookingWithPaymentPending(){

       Long travelerId = 1L;
       String checkIn = "2021-09-01 10:00";
       String checkOut = "2021-09-30 18:00";;
       BigDecimal totalAmount = BigDecimal.valueOf(2000.0);
       LaunchRequest launch01 = LaunchMother.getLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-09-01",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PAID.name(),
                "2021-08-01");
        LaunchRequest launch02 = LaunchMother.getLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-09-01",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                null);

        CreateBookingRequest request = BookingMother.getCreateBookingRequest(travelerId,checkIn, checkOut, totalAmount, Arrays.asList(launch01, launch02));


        ResponseEntity<DetailBookingResponse> result = restTemplate
                .postForEntity(
                        BOOKING_MAPPING, request, DetailBookingResponse.class);

        assertThat(result.getBody().getBookingStatus()).isEqualTo(BookingStatusEnum.RESERVED);
        assertThat(result.getBody().getPaymentStatus()).isEqualTo(PaymentStatusEnum.PENDING);


    }

    @Test
    void shouldCreateReservedBookingWithPaymentDone(){
        Long travelerId = 1L;
        String checkIn = "2021-09-01 10:00";
        String checkOut = "2021-09-30 18:00";;
        BigDecimal totalAmount = BigDecimal.valueOf(2000.0);
        LaunchRequest launch01 = LaunchMother.getLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-09-01",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PAID.name(),
                "2021-08-01");
        LaunchRequest launch02 = LaunchMother.getLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-09-01",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PAID.name(),
                "2021-09-01");

        CreateBookingRequest request = BookingMother.getCreateBookingRequest(travelerId,checkIn, checkOut, totalAmount, Arrays.asList(launch01, launch02));


        ResponseEntity<DetailBookingResponse> result = restTemplate
                .postForEntity(
                        BOOKING_MAPPING, request, DetailBookingResponse.class);

        assertThat(result.getBody().getBookingStatus()).isEqualTo(BookingStatusEnum.RESERVED);
        assertThat(result.getBody().getPaymentStatus()).isEqualTo(PaymentStatusEnum.PAID);

    }

    @Test
    void shouldCreatePreReservedBooking(){
        Long travelerId = 1L;
        String checkIn = "2021-09-01 10:00";
        String checkOut = "2021-09-30 18:00";;
        BigDecimal totalAmount = BigDecimal.valueOf(2000.0);
        LaunchRequest launch01 = LaunchMother.getLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-09-01",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                null);
        LaunchRequest launch02 = LaunchMother.getLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-09-01",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                null);

        CreateBookingRequest request = BookingMother.getCreateBookingRequest(travelerId,checkIn, checkOut, totalAmount, Arrays.asList(launch01, launch02));


        ResponseEntity<DetailBookingResponse> result = restTemplate
                .postForEntity(
                        BOOKING_MAPPING, request, DetailBookingResponse.class);

        assertThat(result.getBody().getBookingStatus()).isEqualTo(BookingStatusEnum.PRE_RESERVED);
        assertThat(result.getBody().getPaymentStatus()).isEqualTo(PaymentStatusEnum.PENDING);

    }

    @Test
    void givenDateInsideOtherBookingWhenCreateBookingThenReturnExceptionMessage(){

        Long travelerId = 2L;
        String checkIn = "2021-10-05 10:00";
        String checkOut = "2021-10-20 18:00";;
        BigDecimal totalAmount = BigDecimal.valueOf(2000.0);
        LaunchRequest launch01 = LaunchMother.getLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-09-01",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                null);
        LaunchRequest launch02 = LaunchMother.getLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-09-01",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                null);

        CreateBookingRequest request = BookingMother.getCreateBookingRequest(travelerId,checkIn, checkOut, totalAmount, Arrays.asList(launch01, launch02));

        ResponseEntity<ExceptionResponse> result = restTemplate
                .postForEntity(
                        BOOKING_MAPPING, request, ExceptionResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(result.getBody().getMessage()).isEqualTo("Já existe outra reserva para o mesmo periodo");


    }

    @Test
    void givenDateWithConflictCheckInWhenCreateBookingThenReturnExceptionMessage(){
        Long travelerId = 2L;
        String checkIn = "2021-09-30 10:00";
        String checkOut = "2021-10-05 18:00";;
        BigDecimal totalAmount = BigDecimal.valueOf(2000.0);
        LaunchRequest launch01 = LaunchMother.getLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-10-10",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                null);
        LaunchRequest launch02 = LaunchMother.getLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-10-20",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                null);

        CreateBookingRequest request = BookingMother.getCreateBookingRequest(
                travelerId,checkIn, checkOut, totalAmount, Arrays.asList(launch01, launch02));


        ResponseEntity<ExceptionResponse> result = restTemplate
                .postForEntity(
                        BOOKING_MAPPING, request, ExceptionResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(result.getBody().getMessage()).isEqualTo("Já existe outra reserva para o mesmo periodo");



    }

    @Test
    void givenDateWithOutsideConflictCheckOutWhenCreateBookingThenReturnExceptionMessage(){
        Long travelerId = 2L;
        String checkIn = "2021-09-30 10:00";
        String checkOut = "2021-11-05 18:00";;
        BigDecimal totalAmount = BigDecimal.valueOf(2000.0);
        LaunchRequest launch01 = LaunchMother.getLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-10-10",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                null);
        LaunchRequest launch02 = LaunchMother.getLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-10-20",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                null);

        CreateBookingRequest request = BookingMother.getCreateBookingRequest(
                travelerId,checkIn, checkOut, totalAmount, Arrays.asList(launch01, launch02));


        ResponseEntity<ExceptionResponse> result = restTemplate
                .postForEntity(
                        BOOKING_MAPPING, request, ExceptionResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(result.getBody().getMessage()).isEqualTo("Já existe outra reserva para o mesmo periodo");

    }

    @Test
    void givenDateWithConflictCheckOutWhenCreateBookingThenReturnExceptionMessage(){
        Long travelerId = 2L;
        String checkIn = "2021-10-30 10:00";
        String checkOut = "2021-11-05 18:00";;
        BigDecimal totalAmount = BigDecimal.valueOf(2000.0);
        LaunchRequest launch01 = LaunchMother.getLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-10-10",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                null);
        LaunchRequest launch02 = LaunchMother.getLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-10-20",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                null);

        CreateBookingRequest request = BookingMother.getCreateBookingRequest(
                travelerId,checkIn, checkOut, totalAmount, Arrays.asList(launch01, launch02));


        ResponseEntity<ExceptionResponse> result = restTemplate
                .postForEntity(
                        BOOKING_MAPPING, request, ExceptionResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(result.getBody().getMessage()).isEqualTo("Já existe outra reserva para o mesmo periodo");

    }
}
