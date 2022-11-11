package com.fernando.oliveira.booking.controller;

import com.fernando.oliveira.booking.domain.builder.ExceptionResponseBuilder;
import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Traveler;
import com.fernando.oliveira.booking.domain.enums.StatusEnum;
import com.fernando.oliveira.booking.domain.mapper.TravelerMapper;
import com.fernando.oliveira.booking.domain.request.CreateTravelerRequest;
import com.fernando.oliveira.booking.domain.response.BookingTravelerResponse;
import com.fernando.oliveira.booking.domain.response.TravelerDetailResponse;
import com.fernando.oliveira.booking.mother.BookingMother;
import com.fernando.oliveira.booking.service.BookingServiceImpl;
import com.fernando.oliveira.booking.service.TravelerServiceImpl;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.fernando.oliveira.booking.mother.BookingMother.getBookingTravelerResponse;
import static com.fernando.oliveira.booking.mother.TravelerMother.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = TravelerController.class)
public class TravelerControllerTest {
	
	private static final String BASE_MAPPING = "/travelers";

	@Autowired
	MockMvc mockMvc;
	
	@InjectMocks
	private TravelerController controller;
	
	@MockBean
	private TravelerServiceImpl travelerService;

	@MockBean
	private BookingServiceImpl bookingService;

	@MockBean
	private ExceptionResponseBuilder exceptionResponseBuilder;

	@MockBean
	private TravelerMapper travelerMapper;
	
	@Test
	public void shouldCreateTravelerAndReturnTravelerDetails() throws Exception {
		
//		CreateTravelerRequest request = getCreateTraveler01Request();
//
//		TravelerDetailResponse response = getDetailTraveler01Response();
//
//		when(travelerService.createTraveler(any(CreateTravelerRequest.class))).thenReturn(response);
//
//		String requestJson = getCreateRequestJsonValue(request);
//		mockMvc.perform(post(BASE_MAPPING)
//				.header("Content-Type", ContentType.APPLICATION_JSON)
//				.content(requestJson))
//				.andExpect(status().isCreated())
//				.andExpect(jsonPath("$.name").value(response.getName()));

	}

	@Test
	public void shouldReturnTravelerDetailById() throws Exception {

		Long id = 2L;

		Traveler travelerSaved = getTravelerSaved02();
		travelerSaved.setId(id);
		travelerSaved.setStatus(StatusEnum.ACTIVE.getCode());

		TravelerDetailResponse response = getDetailTraveler02Response();

		when(travelerService.getTravelerDetail(id)).thenReturn(response);

		mockMvc.perform(get(BASE_MAPPING +"/2")
				.header("Content-Type", ContentType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(response.getName()));

	}

	@Test
	public void shouldReturnAllTravelers() throws Exception {

		List<TravelerDetailResponse> responseList = getTravelerDetailList();

		when(travelerService.findAll()).thenReturn(responseList);

		mockMvc.perform(get(BASE_MAPPING )
				.header("Content-Type", ContentType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value(responseList.get(0).getName()));
	}

	@Test
	public void shouldReturnAllBookingByTraveler() throws Exception {
		Booking booking = BookingMother.getFirstBookingSaved();
		List<BookingTravelerResponse> responseList = Arrays.asList(getBookingTravelerResponse(booking));

		when(bookingService.findBookingsByTraveler(anyLong())).thenReturn(responseList);
		Long travelerId = 1L;

		mockMvc.perform(get(BASE_MAPPING +"/"+ travelerId + "/bookings" )
						.header("Content-Type", ContentType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].bookingId").value(10))
				.andExpect(jsonPath("$[0].checkIn").value("2021-10-15T12:30:00"))
				.andExpect(jsonPath("$[0].checkOut").value("2021-10-20T18:30:00"))
				.andExpect(jsonPath("$[0].bookingStatus").value("RESERVED"))
				.andExpect(jsonPath("$[0].amountTotal").value(1500.0))
				.andExpect(jsonPath("$[0].contractType").value("DIRECT"))
				.andExpect(jsonPath("$[0].observation").value("First booking saved"));

	}

}
