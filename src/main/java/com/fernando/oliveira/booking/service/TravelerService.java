package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Traveler;
import com.fernando.oliveira.booking.domain.request.CreateTravelerRequest;
import com.fernando.oliveira.booking.domain.request.UpdateTravelerRequest;
import com.fernando.oliveira.booking.domain.response.BookingTravelerResponse;
import com.fernando.oliveira.booking.domain.response.TravelerDetailResponse;

import java.util.List;


public interface TravelerService {

	Traveler createTraveler(Traveler traveler) ;

	List<Traveler> findTravelersByNameOrEmail(String name, String email);

	Traveler findById(Long id);

	Traveler getTravelerDetail(Long id) ;

	List<Traveler> findAll();

	Traveler updateTraveler(Long id, UpdateTravelerRequest request);

	List<Traveler> findByNameContainingOrderByNameAsc(String name);

	void inactivateTraveler(Long id);

	List<Traveler> findActiveTravelers();


}
