package com.fernando.oliveira.booking.integration;

import com.fernando.oliveira.booking.domain.enums.StatusEnum;
import com.fernando.oliveira.booking.domain.request.CreateTravelerRequest;
import com.fernando.oliveira.booking.domain.response.TravelerDetailResponse;
import com.fernando.oliveira.booking.mother.TravelerMother;
import org.junit.jupiter.api.Disabled;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts={"classpath:scripts/load-database.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:scripts/clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TravelerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;


    private static final String TRAVELER_MAPPING = "/v1/travelers";

    @Test
    void shouldReturnTravelerWhenConsultTravelerById(){

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
    void shouldReturnTravelerListWhenFindAll(){

        ResponseEntity<TravelerDetailResponse[] > result = restTemplate
                .getForEntity(
                        TRAVELER_MAPPING, TravelerDetailResponse[].class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        TravelerDetailResponse[] response = result.getBody();

        assertThat(response[0].getId()).isEqualTo(1L);
        assertThat(response[0].getName()).isEqualTo("Ana Maria");
        assertThat(response[0].getEmail()).isEqualTo("ana_maria@gmail.com");
        assertThat(response[0].getNumberPhone()).isEqualTo("98888-1111");
        assertThat(response[0].getPrefixPhone()).isEqualTo(11);
        assertThat(response[0].getStatus()).isEqualTo(StatusEnum.ACTIVE.getCode());

        assertThat(response[1].getId()).isEqualTo(2L);
        assertThat(response[1].getName()).isEqualTo("Joao Carlos");
        assertThat(response[1].getEmail()).isEqualTo("joao_carlos@gmail.com");
        assertThat(response[1].getNumberPhone()).isEqualTo("98888-2222");
        assertThat(response[1].getPrefixPhone()).isEqualTo(22);
        assertThat(response[1].getStatus()).isEqualTo(StatusEnum.ACTIVE.getCode());

        assertThat(response[2].getId()).isEqualTo(3L);
        assertThat(response[2].getName()).isEqualTo("Maria da Silva");
        assertThat(response[2].getEmail()).isEqualTo("maria_silva@gmail.com");
        assertThat(response[2].getNumberPhone()).isEqualTo("98888-3333");
        assertThat(response[2].getPrefixPhone()).isEqualTo(33);
        assertThat(response[2].getStatus()).isEqualTo(StatusEnum.ACTIVE.getCode());

        assertThat(response[3].getId()).isEqualTo(4L);
        assertThat(response[3].getName()).isEqualTo("Beatriz Lisboa");
        assertThat(response[3].getEmail()).isEqualTo("beatriz_lisboa@gmail.com");
        assertThat(response[3].getNumberPhone()).isEqualTo("98888-4444");
        assertThat(response[3].getPrefixPhone()).isEqualTo(44);
        assertThat(response[3].getStatus()).isEqualTo(StatusEnum.INACTIVE.getCode());

    }

    @Test
    void shouldReturnTravelerDetailsWhenCreateTraveler(){

        CreateTravelerRequest request = TravelerMother.getCreateTraveler05Request();

        ResponseEntity<TravelerDetailResponse> result = restTemplate
                .postForEntity(
                        TRAVELER_MAPPING,
                        request,
                        TravelerDetailResponse.class
                        );

        assertThat(result.getBody().getId()).isEqualTo(5L);
        assertThat(result.getBody().getStatus()).isEqualTo(StatusEnum.ACTIVE.getCode());

    }

    @Test
    @Disabled
    void shouldReturnExceptionMessageWhenCreateTravelerWithDuplicateName(){
        CreateTravelerRequest request = TravelerMother.getCreateTraveler02Request();
        request.setName("Ana Maria");

        try {
            ResponseEntity<TravelerDetailResponse> result = restTemplate
                    .postForEntity(
                            TRAVELER_MAPPING,
                            request,
                            TravelerDetailResponse.class
                    );
        }catch(Exception e){
            assertThat(e.getMessage()).isEqualTo("Já existe viajante cadastrado com mesmo nome ou email");
        }


    }
}
