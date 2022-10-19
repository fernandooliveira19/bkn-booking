package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Traveler;
import com.fernando.oliveira.booking.domain.request.CreateTravelerRequest;
import com.fernando.oliveira.booking.domain.request.UpdateTravelerRequest;
import com.fernando.oliveira.booking.domain.response.TravelerDetailResponse;

import java.util.List;


public interface TravelerService {

	TravelerDetailResponse createTraveler(CreateTravelerRequest request) ;

	List<Traveler> findTravelersByNameOrEmail(String name, String email);

	Traveler findById(Long id);

	TravelerDetailResponse getTravelerDetail(Long id) ;

	List<TravelerDetailResponse> findAll();

	TravelerDetailResponse updateTraveler(Long id, UpdateTravelerRequest request);

	List<TravelerDetailResponse> findByNameContainingOrderByNameAsc(String name);

	void inactivateTraveler(Long id);

	List<TravelerDetailResponse> findActiveTravelers();
}
