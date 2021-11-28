package com.fernando.oliveira.booking.controller;

import com.fernando.oliveira.booking.domain.builder.ExceptionResponseBuilder;
import com.fernando.oliveira.booking.domain.entity.Traveler;
import com.fernando.oliveira.booking.domain.enums.StatusEnum;
import com.fernando.oliveira.booking.domain.mapper.TravelerMapper;
import com.fernando.oliveira.booking.domain.request.CreateTravelerRequest;
import com.fernando.oliveira.booking.domain.response.TravelerDetailResponse;
import com.fernando.oliveira.booking.mother.TravelerMother;
import com.fernando.oliveira.booking.service.TravelerServiceImpl;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = TravelerController.class)
public class TravelerControllerTest {
	
	private static final String BASE_MAPPING = "/v1/travelers";

	
	@Autowired
	MockMvc mockMvc;
	
	@InjectMocks
	private TravelerController controller;
	
	@MockBean
	private TravelerServiceImpl travelerService;

	@MockBean
	private TravelerMapper mapper;

	@MockBean
	private ExceptionResponseBuilder exceptionResponseBuilder;
	
	@Test
	public void shouldCreateTravelerAndReturnTravelerDetails() throws Exception {
		
		CreateTravelerRequest request = TravelerMother.getCreateTravelerRequest();
		Traveler travelerToSave = TravelerMother.getTraveler();
		Traveler travelerSaved = TravelerMother.getTraveler();
		travelerSaved.setId(1234L);
		travelerSaved.setStatus(StatusEnum.ACTIVE.getCode());
		TravelerDetailResponse response = TravelerMother.getCreateTravelerResponse();

		Mockito.when(mapper.requestToCreateTraveler(Mockito.any(CreateTravelerRequest.class))).thenReturn(travelerToSave);
		Mockito.when(travelerService.createTraveler(Mockito.any(Traveler.class))).thenReturn(travelerSaved);
		Mockito.when(mapper.travelerToTravelerDetailResponse(Mockito.any(Traveler.class))).thenReturn(response);

		String requestJson = TravelerMother.getCreateRequestJsonValue(request);
		mockMvc.perform(post(BASE_MAPPING)
				.header("Content-Type", ContentType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value(response.getName()));

	}

	@Test
	public void shouldReturnTravelerById() throws Exception {

		Long id = 1234L;

		Traveler travelerSaved = TravelerMother.getTraveler();
		travelerSaved.setId(id);
		travelerSaved.setStatus(StatusEnum.ACTIVE.getCode());

		TravelerDetailResponse response = TravelerMother.getDetailTravelerResponse();

		Mockito.when(travelerService.findById(id)).thenReturn(travelerSaved);
		Mockito.when(mapper.travelerToTravelerDetailResponse(Mockito.any(Traveler.class))).thenReturn(response);

		mockMvc.perform(get(BASE_MAPPING +"/1234")
				.header("Content-Type", ContentType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(response.getName()));

	}

	@Test
	public void shouldReturnAllTravelers() throws Exception {


		Traveler travelerSaved = TravelerMother.getTraveler();
		travelerSaved.setId(1234L);
		travelerSaved.setStatus(StatusEnum.ACTIVE.getCode());

		TravelerDetailResponse response = TravelerMother.getDetailTravelerResponse();

		Mockito.when(travelerService.findAll()).thenReturn(Arrays.asList(travelerSaved));
		Mockito.when(mapper.travelerToTravelerDetailResponse(Mockito.any(Traveler.class))).thenReturn(response);

		mockMvc.perform(get(BASE_MAPPING )
				.header("Content-Type", ContentType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value(response.getName()));

	}

}
