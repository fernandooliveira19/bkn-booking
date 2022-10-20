package com.fernando.oliveira.booking.controller;

import com.fernando.oliveira.booking.domain.builder.ExceptionResponseBuilder;
import com.fernando.oliveira.booking.domain.response.ReservedDateResponse;
import com.fernando.oliveira.booking.mother.HomeMother;
import com.fernando.oliveira.booking.service.HomeServiceImpl;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = HomeController.class)
public class HomeControllerTest {
	
	private static final String BASE_MAPPING = "/home";

	
	@Autowired
	MockMvc mockMvc;
	
	@InjectMocks
	private HomeController controller;
	
	@MockBean
	private HomeServiceImpl homeService;

	@MockBean
	private ExceptionResponseBuilder exceptionResponseBuilder;
	
	@Test
	public void shouldReturnReservedDates() throws Exception {

		List<ReservedDateResponse> responseList = HomeMother.getReservedDatesResponse();

		when(homeService.reservedDatesFromNextBookings()).thenReturn(responseList);

		mockMvc.perform(get(BASE_MAPPING )
						.header("Content-Type", ContentType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("reservedDates[0].year").value(2022))
			.andExpect(jsonPath("reservedDates[0].month").value(10))
			.andExpect(jsonPath("reservedDates[0].day").value(15));

	}

}
