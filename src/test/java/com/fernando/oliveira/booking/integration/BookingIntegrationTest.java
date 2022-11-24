package com.fernando.oliveira.booking.integration;

import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentTypeEnum;
import com.fernando.oliveira.booking.domain.request.*;
import com.fernando.oliveira.booking.domain.response.BookingDetailResponse;
import com.fernando.oliveira.booking.domain.response.ExceptionResponse;
import com.fernando.oliveira.booking.mother.BookingMother;
import com.fernando.oliveira.booking.mother.LaunchMother;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    private static final String BOOKING_MAPPING = "/bookings";

    private static final String NEXT_BOOKINGS = BOOKING_MAPPING + "/next";

    private static final String SEARCH_BOOKINGS = BOOKING_MAPPING + "/search";



    @Test
    void shouldReturnAllBookings(){
        ResponseEntity<BookingDetailResponse[]> result = restTemplate
                .getForEntity(
                        BOOKING_MAPPING, BookingDetailResponse[].class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookingDetailResponse[] response = result.getBody();

        assertThat(response.length).isEqualTo(6);

    }

    @Test
    void shouldReturnBookingDetailsById(){

        ResponseEntity<BookingDetailResponse> result = restTemplate
                .getForEntity(
                        BOOKING_MAPPING +"/10" , BookingDetailResponse.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookingDetailResponse response = result.getBody();

        assertThat(response.getId()).isEqualTo(10);
        assertThat(response.getTravelerId()).isEqualTo(1);

        assertThat(response.getAdults()).isEqualTo(3);
        assertThat(response.getChildren()).isEqualTo(2);
        assertThat(response.getBookingStatus()).isEqualTo(BookingStatusEnum.FINISHED);
        assertThat(response.getPaymentStatus()).isEqualTo(PaymentStatusEnum.PAID);
        assertThat(response.getCheckIn()).isEqualTo(LocalDateTime.of(2020, Month.DECEMBER,15,10,0 ));
        assertThat(response.getCheckOut()).isEqualTo(LocalDateTime.of(2020, Month.DECEMBER,30,18,0 ));
        assertThat(response.getAmountTotal()).isEqualByComparingTo(BigDecimal.valueOf(1000.00));
        assertThat(response.getAmountPending()).isEqualByComparingTo(BigDecimal.valueOf(0.00));
        assertThat(response.getAmountPaid()).isEqualByComparingTo(BigDecimal.valueOf(1000.00));

        assertThat(response.getLaunches().get(0).getId()).isEqualTo(100);
        assertThat(response.getLaunches().get(0).getAmount()).isEqualByComparingTo(BigDecimal.valueOf(500.00));
        assertThat(response.getLaunches().get(0).getScheduleDate()).isEqualTo(LocalDate.of(2020,Month.DECEMBER,1));
        assertThat(response.getLaunches().get(0).getPaymentType()).isEqualTo(PaymentTypeEnum.PIX);
        assertThat(response.getLaunches().get(0).getPaymentStatus()).isEqualTo(PaymentStatusEnum.PAID);
        assertThat(response.getLaunches().get(0).getPaymentDate()).isEqualTo(LocalDate.of(2020,Month.DECEMBER,2));

        assertThat(response.getLaunches().get(1).getId()).isEqualTo(101);
        assertThat(response.getLaunches().get(1).getAmount()).isEqualByComparingTo(BigDecimal.valueOf(250.00));
        assertThat(response.getLaunches().get(1).getScheduleDate()).isEqualTo(LocalDate.of(2020,Month.DECEMBER,5));
        assertThat(response.getLaunches().get(1).getPaymentType()).isEqualTo(PaymentTypeEnum.PIX);
        assertThat(response.getLaunches().get(1).getPaymentStatus()).isEqualTo(PaymentStatusEnum.PAID);
        assertThat(response.getLaunches().get(1).getPaymentDate()).isEqualTo(LocalDate.of(2020,Month.DECEMBER,6));

        assertThat(response.getLaunches().get(2).getId()).isEqualTo(102);
        assertThat(response.getLaunches().get(2).getAmount()).isEqualByComparingTo(BigDecimal.valueOf(250.00));
        assertThat(response.getLaunches().get(2).getScheduleDate()).isEqualTo(LocalDate.of(2020,Month.DECEMBER,8));
        assertThat(response.getLaunches().get(2).getPaymentType()).isEqualTo(PaymentTypeEnum.PIX);
        assertThat(response.getLaunches().get(2).getPaymentStatus()).isEqualTo(PaymentStatusEnum.PAID);
        assertThat(response.getLaunches().get(2).getPaymentDate()).isEqualTo(LocalDate.of(2020,Month.DECEMBER,9));

    }
    @Test
    void shouldCreateReservedBookingWithPaymentPending(){

       Long travelerId = 1L;
       String checkIn = "2021-09-01T10:00";
       String checkOut = "2021-09-30T18:00";
       String contractType = "DIRECT";
       BigDecimal amountTotal = BigDecimal.valueOf(2000.00);
        CreateLaunchRequest launch01 = LaunchMother.getCreateLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-09-01",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PAID.name(),
                "2021-08-01");
        CreateLaunchRequest launch02 = LaunchMother.getCreateLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-09-01",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                "");

        CreateBookingRequest request = BookingMother.getCreateBookingRequest(travelerId,checkIn, checkOut, amountTotal,contractType, Arrays.asList(launch01, launch02));


        ResponseEntity<BookingDetailResponse> result = restTemplate
                .postForEntity(
                        BOOKING_MAPPING, request, BookingDetailResponse.class);

        assertThat(result.getBody().getBookingStatus()).isEqualTo(BookingStatusEnum.RESERVED);
        assertThat(result.getBody().getPaymentStatus()).isEqualTo(PaymentStatusEnum.PENDING);


    }

    @Test
    void shouldCreateReservedBookingWithPaymentDone(){
        Long travelerId = 1L;
        String checkIn = "2021-09-01T10:00";
        String checkOut = "2021-09-30T18:00";
        BigDecimal amountTotal = BigDecimal.valueOf(2000.0);
        String contractType = "DIRECT";
        CreateLaunchRequest launch01 = LaunchMother.getCreateLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-09-01",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PAID.name(),
                "2021-08-01");
        CreateLaunchRequest launch02 = LaunchMother.getCreateLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-09-01",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PAID.name(),
                "2021-09-01");

        CreateBookingRequest request = BookingMother.getCreateBookingRequest(travelerId,checkIn, checkOut, amountTotal, contractType, Arrays.asList(launch01, launch02));


        ResponseEntity<BookingDetailResponse> result = restTemplate
                .postForEntity(
                        BOOKING_MAPPING, request, BookingDetailResponse.class);

        assertThat(result.getBody().getBookingStatus()).isEqualTo(BookingStatusEnum.RESERVED);
        assertThat(result.getBody().getPaymentStatus()).isEqualTo(PaymentStatusEnum.PAID);

    }

    @Test
    void shouldCreatePreReservedBooking(){
        Long travelerId = 1L;
        String checkIn = "2021-09-01T10:00";
        String checkOut = "2021-09-30T18:00";
        String contractType = "DIRECT";
        BigDecimal amountTotal = BigDecimal.valueOf(2000.00);
        CreateLaunchRequest launch01 = LaunchMother.getCreateLaunchRequest(
                BigDecimal.valueOf(1000.00),
                "2021-09-01",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                "");
        CreateLaunchRequest launch02 = LaunchMother.getCreateLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-09-01",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                "");

        CreateBookingRequest request = BookingMother.getCreateBookingRequest(travelerId,checkIn, checkOut, amountTotal, contractType, Arrays.asList(launch01, launch02));


        ResponseEntity<BookingDetailResponse> result = restTemplate
                .postForEntity(
                        BOOKING_MAPPING, request, BookingDetailResponse.class);

        assertThat(result.getBody().getBookingStatus()).isEqualTo(BookingStatusEnum.PRE_RESERVED);
        assertThat(result.getBody().getPaymentStatus()).isEqualTo(PaymentStatusEnum.PENDING);

    }

    @Test
    void givenDateInsideOtherBookingWhenCreateBookingThenReturnExceptionMessage(){

        Long travelerId = 6L;
        String checkIn = "2021-01-02T10:00";
        String checkOut = "2021-01-14T18:00";
        String contractType = "DIRECT";
        BigDecimal amountTotal = BigDecimal.valueOf(2000.0);
        CreateLaunchRequest launch01 = LaunchMother.getCreateLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-01-01",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                "");
        CreateLaunchRequest launch02 = LaunchMother.getCreateLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-01-01",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                "");

        CreateBookingRequest request = BookingMother.getCreateBookingRequest(travelerId,checkIn, checkOut, amountTotal, contractType, Arrays.asList(launch01, launch02));

        ResponseEntity<ExceptionResponse> result = restTemplate
                .postForEntity(
                        BOOKING_MAPPING, request, ExceptionResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(result.getBody().getMessage()).isEqualTo("Existe outra reserva para o mesmo periodo");


    }

    @Test
    void givenDateWithConflictCheckInWhenCreateBookingThenReturnExceptionMessage(){
        Long travelerId = 3L;
        String checkIn = "2020-12-31T10:00";
        String checkOut = "2021-01-03T18:00";
        String contractType = "DIRECT";
        BigDecimal amountTotal = BigDecimal.valueOf(2000.0);
        CreateLaunchRequest launch01 = LaunchMother.getCreateLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2020-12-10",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                "");
        CreateLaunchRequest launch02 = LaunchMother.getCreateLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2020-12-20",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                "");

        CreateBookingRequest request = BookingMother.getCreateBookingRequest(
                travelerId,checkIn, checkOut, amountTotal, contractType,Arrays.asList(launch01, launch02));


        ResponseEntity<ExceptionResponse> result = restTemplate
                .postForEntity(
                        BOOKING_MAPPING, request, ExceptionResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(result.getBody().getMessage()).isEqualTo("Existe outra reserva para o mesmo periodo");



    }

    @Test
    void givenDateWithOutsideConflictCheckOutWhenCreateBookingThenReturnExceptionMessage(){
        Long travelerId = 3L;
        String checkIn = "2020-12-30T10:00";
        String checkOut = "2021-01-16T18:00";
        String contractType = "DIRECT";
        BigDecimal amountTotal = BigDecimal.valueOf(2000.0);
        CreateLaunchRequest launch01 = LaunchMother.getCreateLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2020-12-10",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                "");
        CreateLaunchRequest launch02 = LaunchMother.getCreateLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2020-12-20",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                "");

        CreateBookingRequest request = BookingMother.getCreateBookingRequest(
                travelerId,checkIn, checkOut, amountTotal, contractType,Arrays.asList(launch01, launch02));


        ResponseEntity<ExceptionResponse> result = restTemplate
                .postForEntity(
                        BOOKING_MAPPING, request, ExceptionResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(result.getBody().getMessage()).isEqualTo("Existe outra reserva para o mesmo periodo");

    }

    @Test
    void givenDateWithConflictCheckOutWhenCreateBookingThenReturnExceptionMessage(){
        Long travelerId = 3L;
        String checkIn = "2021-01-14T10:00";
        String checkOut = "2021-01-16T18:00";
        String contractType = "DIRECT";
        BigDecimal amountTotal = BigDecimal.valueOf(2000.0);
        CreateLaunchRequest launch01 = LaunchMother.getCreateLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-01-10",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                "");
        CreateLaunchRequest launch02 = LaunchMother.getCreateLaunchRequest(
                BigDecimal.valueOf(1000.0),
                "2021-01-20",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PENDING.name(),
                "");

        CreateBookingRequest request = BookingMother.getCreateBookingRequest(
                travelerId,checkIn, checkOut, amountTotal, contractType,Arrays.asList(launch01, launch02));


        ResponseEntity<ExceptionResponse> result = restTemplate
                .postForEntity(
                        BOOKING_MAPPING, request, ExceptionResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(result.getBody().getMessage()).isEqualTo("Existe outra reserva para o mesmo periodo");

    }

    @Test
    @Disabled
    void shouldUpdateReservedBooking(){
        Long travelerId = 6L;
        String checkIn = "2021-03-16 10:00";
        String checkOut = "2021-03-30 18:00";;
        String observation = "updating booking";
        BigDecimal amountTotal = BigDecimal.valueOf(3800.0);

        BookingStatusEnum bookingStatus = BookingStatusEnum.RESERVED;
        UpdateLaunchRequest launch01 = LaunchMother.getUpdateLaunchRequest(
                110L,
                BigDecimal.valueOf(2000.0),
                "2021-03-01",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PAID.name(),
                "2021-03-02");
        UpdateLaunchRequest launch02 = LaunchMother.getUpdateLaunchRequest(
                111L,
                BigDecimal.valueOf(1800.0),
                "2021-03-05",
                PaymentTypeEnum.PIX.name(),
                PaymentStatusEnum.PAID.name(),
                "2021-03-06");


        UpdateBookingRequest request = BookingMother.getUpdateBookingRequest(travelerId,checkIn, checkOut, amountTotal,observation, bookingStatus,  Arrays.asList(launch01, launch02));

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UpdateBookingRequest> httpEntity = new HttpEntity<UpdateBookingRequest>(request, headers);
        ResponseEntity<BookingDetailResponse> result =
                restTemplate.exchange(BOOKING_MAPPING +"/60", HttpMethod.PUT, httpEntity,
                        BookingDetailResponse.class);

        assertThat(result.getBody().getBookingStatus()).isEqualTo(BookingStatusEnum.RESERVED);
        assertThat(result.getBody().getPaymentStatus()).isEqualTo(PaymentStatusEnum.PAID);

    }

    @Test
    void shouldReturnNextBookings(){
        ResponseEntity<BookingDetailResponse[]> result = restTemplate
                .getForEntity(
                        NEXT_BOOKINGS, BookingDetailResponse[].class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookingDetailResponse[] response = result.getBody();

        assertThat(response.length).isEqualTo(4);

    }

    @Test
    void givenEmptyParamsWhenSearchBookingThenReturnNextBookings(){

        ResponseEntity<BookingDetailResponse[]> result = restTemplate
                .getForEntity(
                        SEARCH_BOOKINGS, BookingDetailResponse[].class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookingDetailResponse[] response = result.getBody();

        assertThat(response.length).isEqualTo(4);

    }

    @Test
    void givenBookingStatusParamsWhenSearchBookingThenReturnReservedBookings(){

        String params = "?bookingStatus=RESERVED";

        ResponseEntity<BookingDetailResponse[]> result = restTemplate
                .getForEntity(
                        SEARCH_BOOKINGS + params, BookingDetailResponse[].class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookingDetailResponse[] response = result.getBody();

        assertThat(response.length).isEqualTo(3);

    }

    @Test
    void givenPaymentStatusParamsWhenSearchBookingThenReturnReservedBookings(){

        String params = "?paymentStatus=PAID";

        ResponseEntity<BookingDetailResponse[]> result = restTemplate
                .getForEntity(
                        SEARCH_BOOKINGS + params, BookingDetailResponse[].class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookingDetailResponse[] response = result.getBody();

        assertThat(response.length).isEqualTo(2);

    }
    @Test
    void givenContractTypeParamsWhenSearchBookingThenReturnReservedBookings(){

        String params = "?contractType=DIRECT";

        ResponseEntity<BookingDetailResponse[]> result = restTemplate
                .getForEntity(
                        SEARCH_BOOKINGS + params, BookingDetailResponse[].class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookingDetailResponse[] response = result.getBody();

        assertThat(response.length).isEqualTo(5);

    }
    @Test
    void givenAllParamsWhenSearchBookingThenReturnReservedBookings(){

        String params = "?paymentStatus=PAID&bookingStatus=RESERVED&contractType=DIRECT";

        ResponseEntity<BookingDetailResponse[]> result = restTemplate
                .getForEntity(
                        SEARCH_BOOKINGS + params, BookingDetailResponse[].class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookingDetailResponse[] response = result.getBody();

        assertThat(response.length).isEqualTo(1);

    }

}
