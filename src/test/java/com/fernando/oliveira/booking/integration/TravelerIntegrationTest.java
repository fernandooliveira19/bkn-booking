package com.fernando.oliveira.booking.integration;

import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.ContractTypeEnum;
import com.fernando.oliveira.booking.domain.enums.StatusEnum;
import com.fernando.oliveira.booking.domain.request.CreateTravelerRequest;
import com.fernando.oliveira.booking.domain.request.UpdateTravelerRequest;
import com.fernando.oliveira.booking.domain.response.BookingTravelerResponse;
import com.fernando.oliveira.booking.domain.response.ExceptionResponse;
import com.fernando.oliveira.booking.domain.response.TravelerDetailResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static com.fernando.oliveira.booking.mother.TravelerMother.*;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = {"classpath:scripts/load-database.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:scripts/clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TravelerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private static final String TRAVELER_MAPPING = "/travelers";

    @Test
    void shouldReturnTravelerWhenConsultTravelerById() {

        ResponseEntity<TravelerDetailResponse> result = restTemplate
                .getForEntity(
                        TRAVELER_MAPPING + "/1",
                        TravelerDetailResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getName()).isEqualTo("Ana Maria");
        assertThat(result.getBody().getEmail()).isEqualTo("ana_maria@gmail.com");
        assertThat(result.getBody().getNumberPhone()).isEqualTo("98888-1111");
        assertThat(result.getBody().getPrefixPhone()).isEqualTo(11);
        assertThat(result.getBody().getStatus()).isEqualTo(StatusEnum.ACTIVE.getCode());
    }

    @Test
    void shouldReturnTravelerListWhenFindAll() {

        ResponseEntity<TravelerDetailResponse[]> result = restTemplate
                .getForEntity(
                        TRAVELER_MAPPING, TravelerDetailResponse[].class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        TravelerDetailResponse[] response = result.getBody();
        assertThat(response.length).isEqualTo(6);

        assertThat(response[0].getId()).isEqualTo(1L);
        assertThat(response[0].getName()).isEqualTo("Ana Maria");
        assertThat(response[0].getEmail()).isEqualTo("ana_maria@gmail.com");
        assertThat(response[0].getNumberPhone()).isEqualTo("98888-1111");
        assertThat(response[0].getPrefixPhone()).isEqualTo(11);
        assertThat(response[0].getStatus()).isEqualTo(StatusEnum.ACTIVE.getCode());

        assertThat(response[1].getId()).isEqualTo(2L);
        assertThat(response[1].getName()).isEqualTo("Bianca Silva");
        assertThat(response[1].getEmail()).isEqualTo("bianca_silva@gmail.com");
        assertThat(response[1].getNumberPhone()).isEqualTo("98888-2222");
        assertThat(response[1].getPrefixPhone()).isEqualTo(22);
        assertThat(response[1].getStatus()).isEqualTo(StatusEnum.ACTIVE.getCode());

        assertThat(response[2].getId()).isEqualTo(3L);
        assertThat(response[2].getName()).isEqualTo("Carlos Garcia");
        assertThat(response[2].getEmail()).isEqualTo("carlos_garcia@gmail.com");
        assertThat(response[2].getNumberPhone()).isEqualTo("98888-3333");
        assertThat(response[2].getPrefixPhone()).isEqualTo(33);
        assertThat(response[2].getStatus()).isEqualTo(StatusEnum.ACTIVE.getCode());

        assertThat(response[3].getId()).isEqualTo(4L);
        assertThat(response[3].getName()).isEqualTo("David Souza");
        assertThat(response[3].getEmail()).isEqualTo("david_souza@gmail.com");
        assertThat(response[3].getNumberPhone()).isEqualTo("98888-4444");
        assertThat(response[3].getPrefixPhone()).isEqualTo(44);
        assertThat(response[3].getStatus()).isEqualTo(StatusEnum.ACTIVE.getCode());

        assertThat(response[4].getId()).isEqualTo(5L);
        assertThat(response[4].getName()).isEqualTo("Elaine Matos");
        assertThat(response[4].getEmail()).isEqualTo("elaine_matos@gmail.com");
        assertThat(response[4].getNumberPhone()).isEqualTo("98888-5555");
        assertThat(response[4].getPrefixPhone()).isEqualTo(55);
        assertThat(response[4].getStatus()).isEqualTo(StatusEnum.INACTIVE.getCode());

        assertThat(response[5].getId()).isEqualTo(6L);
        assertThat(response[5].getName()).isEqualTo("Fernando Oliveira");
        assertThat(response[5].getEmail()).isEqualTo("fernando_oliveira@gmail.com");
        assertThat(response[5].getNumberPhone()).isEqualTo("98888-6666");
        assertThat(response[5].getPrefixPhone()).isEqualTo(66);
        assertThat(response[5].getStatus()).isEqualTo(StatusEnum.ACTIVE.getCode());

    }

    @Test
    void shouldReturnTravelerDetailsWhenCreateTraveler() {

//        CreateTravelerRequest request = getNewTravelerRequest("Gustavo Andrade", "gustavo_andrade@gmail.com", 77, "988887777",  "");
//
//        ResponseEntity<TravelerDetailResponse> result = restTemplate
//                .postForEntity(
//                        TRAVELER_MAPPING,
//                        request,
//                        TravelerDetailResponse.class
//                );
//
//        assertThat(result.getBody().getId()).isEqualTo(7L);
//        assertThat(result.getBody().getStatus()).isEqualTo(StatusEnum.ACTIVE.getCode());
//

    }

    @Test
    void shouldReturnExceptionMessageWhenCreateTravelerWithDuplicateName() {
        CreateTravelerRequest request = getCreateTraveler02Request();

        ResponseEntity<ExceptionResponse> result = restTemplate
                .postForEntity(
                        TRAVELER_MAPPING, request, ExceptionResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(result.getBody().getMessage()).isEqualTo("Já existe outro viajante cadastrado com mesmo nome ou email");

    }

    @Test
    void shouldReturnActiveTravelerList() {

        ResponseEntity<TravelerDetailResponse[]> result = restTemplate
                .getForEntity(
                        TRAVELER_MAPPING + "/actives/", TravelerDetailResponse[].class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        TravelerDetailResponse[] response = result.getBody();

        assertThat(response.length).isEqualTo(5);

    }

    @Test
    void shouldInactiveTravelerById() {
        Long travelerId = 1L;

        restTemplate
                .put(TRAVELER_MAPPING + "/1/inactive", travelerId);

        ResponseEntity<TravelerDetailResponse[]> result = restTemplate
                .getForEntity(
                        TRAVELER_MAPPING + "/actives/", TravelerDetailResponse[].class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        TravelerDetailResponse[] response = result.getBody();

        assertThat(response.length).isEqualTo(4);

    }

    @Test
    void shouldUpdateTraveler(){

        UpdateTravelerRequest request = getUpdateTraveler01Request();
        request.setName("Mauro Cesar");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UpdateTravelerRequest> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<TravelerDetailResponse> result = restTemplate
                .exchange(TRAVELER_MAPPING,
                        HttpMethod.PUT,
                        httpEntity,TravelerDetailResponse.class
                );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getName()).isEqualTo("Mauro Cesar");
        assertThat(result.getBody().getId()).isEqualTo(1L);

    }



    @Test
    void shouldReturnTravelerByName(){

        String name = "Ana";
        ResponseEntity<TravelerDetailResponse[]> result = restTemplate
                .getForEntity(
                        TRAVELER_MAPPING + "/find?name=" + name, TravelerDetailResponse[].class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        TravelerDetailResponse[] response = result.getBody();
        assertThat(response.length).isEqualTo(1);

        assertThat(response[0].getId()).isEqualTo(1L);
        assertThat(response[0].getName()).isEqualTo("Ana Maria");
        assertThat(response[0].getEmail()).isEqualTo("ana_maria@gmail.com");
        assertThat(response[0].getNumberPhone()).isEqualTo("98888-1111");
        assertThat(response[0].getPrefixPhone()).isEqualTo(11);
        assertThat(response[0].getStatus()).isEqualTo(StatusEnum.ACTIVE.getCode());
    }

    @Test
    public void shouldReturnAllBookingByTraveler() throws Exception {
        Long id = 1L;
        ResponseEntity<BookingTravelerResponse[]> result = restTemplate
                .getForEntity(
                        TRAVELER_MAPPING + "/" + id + "/bookings", BookingTravelerResponse[].class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookingTravelerResponse[] response = result.getBody();
        assertThat(response.length).isEqualTo(1);

        assertThat(response[0].getBookingId()).isEqualTo(10L);
        assertThat(response[0].getCheckIn()).isEqualTo("2020-12-15T10:00:00");
        assertThat(response[0].getCheckOut()).isEqualTo("2020-12-30T18:00:00");
        assertThat(response[0].getAmountTotal()).isEqualByComparingTo(BigDecimal.valueOf(1000.00));

        assertThat(response[0].getBookingStatus()).isEqualTo(BookingStatusEnum.FINISHED);
        assertThat(response[0].getContractType()).isEqualTo(ContractTypeEnum.DIRECT);
        assertThat(response[0].getObservation()).isEqualTo("Primeira reserva");

    }

}
