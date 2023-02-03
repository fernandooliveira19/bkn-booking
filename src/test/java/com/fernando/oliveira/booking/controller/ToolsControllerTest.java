package com.fernando.oliveira.booking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fernando.oliveira.booking.domain.builder.ExceptionResponseBuilder;
import com.fernando.oliveira.booking.domain.dto.PreviewBookingDto;
import com.fernando.oliveira.booking.domain.mapper.ToolsMapper;
import com.fernando.oliveira.booking.domain.request.PreviewBookingRequest;
import com.fernando.oliveira.booking.domain.response.PreviewBookingResponse;
import com.fernando.oliveira.booking.service.impl.ToolsServiceImpl;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = ToolsController.class)
public class ToolsControllerTest {
	
	private static final String BASE_MAPPING = "/tools";

	@Autowired
	MockMvc mockMvc;
	
	@InjectMocks
	private ToolsController controller;
	
	@MockBean
	private ToolsServiceImpl toolsService;

	@MockBean
	private ToolsMapper toolsMapper;

	@MockBean
	private ExceptionResponseBuilder exceptionResponseBuilder;


	@Test
	void shouldCalculateAmountBooking() throws Exception {

		PreviewBookingRequest request = PreviewBookingRequest.builder()
				.checkIn("2023-02-05T10:00:00")
				.checkOut("2023-02-09T18:00:00")
				.dailyValue(BigDecimal.valueOf(350.0))
				.build();
		PreviewBookingDto dto = PreviewBookingDto.builder()
				.amountTotal(BigDecimal.valueOf(1400.0))
				.rentDays(Long.valueOf(4))
				.build();

		PreviewBookingResponse response = PreviewBookingResponse.builder()
				.amountTotal(BigDecimal.valueOf(1400.0))
				.rentDays(Long.valueOf(4))
				.build();

		when(toolsService.calculateAmount(any(LocalDateTime.class),
				any(LocalDateTime.class),any(BigDecimal.class))).thenReturn(dto);
		when(toolsMapper.previewBookingDtoToResponse(any(PreviewBookingDto.class))).thenReturn(response);

		String requestJson = getRequestJsonValue(request);
		mockMvc.perform(post(BASE_MAPPING + "/bookings/preview")
				.header("Content-Type", ContentType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.rentDays").value(4))
				.andExpect(jsonPath("$.amountTotal").value(1400));

	}

	public static String getRequestJsonValue(PreviewBookingRequest request) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		return writer.writeValueAsString(request);
	}

}
