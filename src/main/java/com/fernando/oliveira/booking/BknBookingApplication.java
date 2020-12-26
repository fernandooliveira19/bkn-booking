package com.fernando.oliveira.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class BknBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BknBookingApplication.class, args);
	}

}
