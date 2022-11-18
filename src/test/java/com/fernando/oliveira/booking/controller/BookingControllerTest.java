package com.fernando.oliveira.booking.controller;

import com.fernando.oliveira.booking.builder.BookingBuilder;
import com.fernando.oliveira.booking.domain.builder.ExceptionResponseBuilder;
import com.fernando.oliveira.booking.service.BookingServiceImpl;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WebMvcTest(controllers = BookingController.class)
public class BookingControllerTest {
	
	private static final String BASE_MAPPING = "/bookings";

	
	@Autowired
	MockMvc mockMvc;
	
	@InjectMocks
	private BookingController controller;
	
	@MockBean
	private BookingServiceImpl bookingService;

	@MockBean
	private ExceptionResponseBuilder exceptionResponseBuilder;

	@MockBean
	private BookingBuilder bookingBuilder;
	

	public void shouldCreateTravelerAndReturnTravelerDetails() throws Exception {
		


	}

}
