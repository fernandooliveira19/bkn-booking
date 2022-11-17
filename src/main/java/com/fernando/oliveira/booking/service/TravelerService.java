package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Traveler;

import java.util.List;


public interface TravelerService {

	Traveler createTraveler(Traveler traveler) ;

	List<Traveler> findTravelersByNameOrEmail(String name, String email);

	Traveler findById(Long id);

	Traveler getTravelerDetail(Long id) ;

	List<Traveler> findAll();

	Traveler updateTraveler(Long id, Traveler traveler);

	List<Traveler> findByNameContainingOrderByNameAsc(String name);

	void inactivateTraveler(Long id);

	List<Traveler> findActiveTravelers();


}
