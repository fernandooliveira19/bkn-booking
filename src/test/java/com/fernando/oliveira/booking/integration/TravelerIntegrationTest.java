package com.fernando.oliveira.booking.integration;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@AutoConfigureMockMvc
//@AutoConfugureWireMock(port=0)
@ActiveProfiles("test")
@Sql("/scripts/traveler.sql")
public class TravelerIntegrationTest {

    private WireMockServer wireMockServer;

//    @Value(value="integration.test.wiremock.port")
//    private Integer port;

//    @Autowired
//    private MockMvc mockMvc;

//    @BeforeEach
//    void setUp(){
//        wireMockServer = new WireMockServer(port);
//        configureFor(port);
//        wireMockServer.start();
//    }
//    void afterEach(){
//        this.wireMockServer.stop();
//    }


    private static final String TRAVELER_MAPPING = "v1/travelers";

//    @Test
    void givenValidRequestThenCreateTravelerThenReturnTravelerDetails(){

    }

}
