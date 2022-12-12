package com.fernando.oliveira.booking.controller;

import com.fernando.oliveira.booking.domain.builder.ExceptionResponseBuilder;
import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Traveler;
import com.fernando.oliveira.booking.domain.mapper.BookingMapper;
import com.fernando.oliveira.booking.domain.mapper.TravelerMapper;
import com.fernando.oliveira.booking.domain.request.CreateTravelerRequest;
import com.fernando.oliveira.booking.domain.response.TravelerDetailResponse;
import com.fernando.oliveira.booking.mother.BookingMother;
import com.fernando.oliveira.booking.mother.TravelerMother;
import com.fernando.oliveira.booking.service.BookingServiceImpl;
import com.fernando.oliveira.booking.service.TravelerServiceImpl;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

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

	@MockBean
	private BookingMapper bookingMapper;

	@Test
	public void shouldCreateTravelerAndReturnTravelerDetails() throws Exception {
		
		CreateTravelerRequest request = getCreateTraveler01Request();

		Traveler travelerSaved = getTravelerSaved01();
		TravelerDetailResponse response = getDetailTraveler01Response();
		when(travelerMapper.requestToCreateTraveler(any(CreateTravelerRequest.class))).thenReturn(TravelerMother.getTravelerToSaved01());
		when(travelerService.createTraveler(any(Traveler.class))).thenReturn(travelerSaved);
		when(travelerMapper.travelerToTravelerDetailResponse(travelerSaved)).thenReturn(response);

		String requestJson = getCreateRequestJsonValue(request);
		mockMvc.perform(post(BASE_MAPPING)
				.header("Content-Type", ContentType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("Ana Maria"))
				.andExpect(jsonPath("$.email").value("ana_maria@gmail.com"))
				.andExpect(jsonPath("$.document").value("50042806739"))
				.andExpect(jsonPath("$.prefixPhone").value(11))
				.andExpect(jsonPath("$.numberPhone").value("98888-1111"));

	}

	@Test
	public void shouldReturnTravelerDetailById() throws Exception {

		Long id = 2L;
		Traveler travelerSaved = getTravelerSaved02();
		TravelerDetailResponse response = getDetailTraveler02Response();

		when(travelerService.getTravelerDetail(id)).thenReturn(travelerSaved);
		when(travelerMapper.travelerToTravelerDetailResponse(travelerSaved)).thenReturn(response);

		mockMvc.perform(get(BASE_MAPPING +"/"+id)
				.header("Content-Type", ContentType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Bianca Silva"))
				.andExpect(jsonPath("$.email").value("bianca_silva@gmail.com"))
				.andExpect(jsonPath("$.document").value("18421484869"))
				.andExpect(jsonPath("$.prefixPhone").value(22))
				.andExpect(jsonPath("$.numberPhone").value("98888-2222"));

	}

	@Test
	public void shouldReturnAllTravelers() throws Exception {

		List<Traveler> responseList = getTravelerSavedList();
		TravelerDetailResponse response = TravelerMother.getDetailTraveler01Response();

		when(travelerService.findAll()).thenReturn(responseList);
		when(travelerMapper.travelerToTravelerDetailResponse(responseList.get(0))).thenReturn(response);

		mockMvc.perform(get(BASE_MAPPING )
				.header("Content-Type", ContentType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Ana Maria"));
	}

	@Test
	public void shouldReturnAllBookingByTraveler() throws Exception {

		Booking booking = BookingMother.getBookingSaved01();
		when(bookingService.findBookingsByTraveler(anyLong())).thenReturn(Arrays.asList(booking));
		when(bookingMapper.bookingToBookingTravelerResponse(any(Booking.class))).thenReturn(BookingMother.getBookingTravelerResponse(booking));

		Long travelerId = 1L;

		mockMvc.perform(get(BASE_MAPPING +"/"+ travelerId + "/bookings" )
						.header("Content-Type", ContentType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].bookingId").value(10))
				.andExpect(jsonPath("$[0].checkIn").value("2020-12-15T10:00:00"))
				.andExpect(jsonPath("$[0].checkOut").value("2020-12-30T18:00:00"))
				.andExpect(jsonPath("$[0].bookingStatus").value("FINISHED"))
				.andExpect(jsonPath("$[0].amountTotal").value(1000.0))
				.andExpect(jsonPath("$[0].contractType").value("DIRECT"))
				.andExpect(jsonPath("$[0].observation").value("First booking"));

	}

}
