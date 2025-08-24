package com.fernando.oliveira.booking.controller;

import com.fernando.oliveira.booking.domain.builder.ExceptionResponseBuilder;
import com.fernando.oliveira.booking.domain.dto.HomeDto;
import com.fernando.oliveira.booking.domain.mapper.HomeMapper;
import com.fernando.oliveira.booking.domain.response.HomeResponse;
import com.fernando.oliveira.booking.domain.response.ReservedDateResponse;
import com.fernando.oliveira.booking.mother.HomeMother;
import com.fernando.oliveira.booking.service.HomeService;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Disabled;
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
	private HomeService homeService;

	@MockBean
	private ExceptionResponseBuilder exceptionResponseBuilder;

	@MockBean
	private HomeMapper homeMapper;

	@Test
	@Disabled
	public void shouldReturnReservedDates() throws Exception {

		List<ReservedDateResponse> responseList = HomeMother.getReservedDatesResponse();

		when(homeService.reservedDatesFromNextBookings()).thenReturn(responseList);

		mockMvc.perform(get(BASE_MAPPING )
						.header("Content-Type", ContentType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("[0].year").value(2022))
			.andExpect(jsonPath("[0].month").value(10))
			.andExpect(jsonPath("[0].day").value(15));

	}

	@Test
	public void shouldReturnHomeResponse() throws Exception {

		HomeResponse homeResponse = HomeMother.getHomeResponse();
		HomeDto homeDto = HomeMother.getHomeDto();
		when(homeService.getHomeDetails()).thenReturn(homeDto);
		when(homeMapper.dtoToResponse(homeDto)).thenReturn(homeResponse);

		mockMvc.perform(get(BASE_MAPPING )
						.header("Content-Type", ContentType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("homeLaunch.amountTotal").value(5500))
				.andExpect(jsonPath("homeLaunch.directOverdueQuantity").value(3))
				.andExpect(jsonPath("homeLaunch.directOverdueAmount").value(3500))
				.andExpect(jsonPath("homeLaunch.directToReceiveQuantity").value(0))
				.andExpect(jsonPath("homeLaunch.directToReceiveAmount").value(0))
				.andExpect(jsonPath("homeLaunch.directOverdueSubTotal").value(3500))
				.andExpect(jsonPath("homeLaunch.siteQuantity").value(1))
				.andExpect(jsonPath("homeLaunch.siteAmount").value(2500))
				.andExpect(jsonPath("homeBooking.bookingReserved").value(3))
				.andExpect(jsonPath("homeBooking.bookingPreReserved").value(1))
				.andExpect(jsonPath("homeBooking.bookingPaid").value(1))
				.andExpect(jsonPath("homeBooking.bookingPending").value(2))
				.andExpect(jsonPath("homeBooking.bookingToReceive").value(1))
				.andExpect(jsonPath("homeBooking.bookingDirect").value(4))
				.andExpect(jsonPath("homeBooking.bookingSite").value(1))
				.andExpect(jsonPath("homeBooking.bookingTotal").value(4));

	}

}
