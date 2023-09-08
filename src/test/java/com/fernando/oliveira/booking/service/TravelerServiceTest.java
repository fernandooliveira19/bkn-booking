package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Traveler;
import com.fernando.oliveira.booking.domain.enums.StatusEnum;
import com.fernando.oliveira.booking.domain.request.CreateTravelerRequest;
import com.fernando.oliveira.booking.domain.request.UpdateTravelerRequest;
import com.fernando.oliveira.booking.exception.TravelerException;
import com.fernando.oliveira.booking.mother.TravelerMother;
import com.fernando.oliveira.booking.repository.TravelerRepository;
import com.fernando.oliveira.booking.utils.MessageUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.fernando.oliveira.booking.mother.TravelerMother.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TravelerServiceTest {
	
	@InjectMocks
	private TravelerService travelerService;

	@Mock
	private TravelerRepository repository;

	@Mock
	private MessageUtils messageUtils;

	@Test
	public void shouldCreateTravelerAndReturnTravelerDetails() {

		Traveler travelerToSave = TravelerMother.getTravelerToSaved01();
		Traveler travelerSaved = getTravelerSaved01();

		when(repository.save(travelerToSave)).thenReturn(travelerSaved);

		Traveler result = travelerService.createTraveler(travelerToSave);

		then(result.getId()).isNotNull();
		then(travelerSaved.getId()).isEqualTo(result.getId());
		then(travelerToSave.getName()).isEqualTo(result.getName() );
		then(travelerToSave.getEmail()).isEqualTo(result.getEmail() );
		then(StatusEnum.ACTIVE.getCode()).isEqualTo(result.getStatus());
		then(travelerToSave.getPrefixPhone()).isEqualTo(result.getPrefixPhone());
		then(travelerToSave.getNumberPhone()).isEqualTo(result.getNumberPhone()) ;
		
	}

	@Test
	public void shouldReturnTravelerListByNameOrEmail(){

		String name = "Bianca Silva";
		String email = "bianca.silva@gmail.com";

		when(repository.findByNameOrEmail(name, email)).thenReturn(Arrays.asList(getTravelerSaved02()));
		List<Traveler> result = travelerService.findTravelersByNameOrEmail(name, email);

		then(result.get(0).getId()).isEqualTo(2);
		then(result.get(0).getName()).isEqualTo("Bianca Silva");
		then(result.get(0).getEmail()).isEqualTo("bianca_silva@gmail.com");
		then(result.get(0).getDocument()).isEqualTo("18421484869");
		then(result.get(0).getPrefixPhone()).isEqualTo(22);
		then(result.get(0).getNumberPhone()).isEqualTo("98888-2222");

	}

	@Test
	public void shouldReturnTravelerById(){
		Long id = 1L;
		Traveler traveler = getTravelerSaved01();

		when(repository.findById(id)).thenReturn(Optional.of(traveler));

		Traveler result = travelerService.findById(id);

		then(traveler.getId()).isEqualTo(result.getId());
		then(traveler.getName()).isEqualTo(result.getName());
		then(traveler.getEmail()).isEqualTo( result.getEmail());
		then(traveler.getStatus()).isEqualTo( result.getStatus());
		then(traveler.getDocument()).isEqualTo( result.getDocument());
		then(traveler.getPrefixPhone()).isEqualTo( result.getPrefixPhone());
		then(traveler.getNumberPhone()).isEqualTo( result.getNumberPhone());

	}

	@Test
	public void shouldReturnAllTravelers(){

		when(repository.findAll()).thenReturn(getTravelerSavedList());

		List<Traveler> result = travelerService.findAll();

		then(result.size()).isEqualTo( 6);

	}

	@Test
	public void shouldReturnAllTravelersByNameOrEmail(){
		Traveler traveler = getTravelerSaved02();
		String name = "Bianca Silva";
		String email = "";
		when(repository.findByNameOrEmail(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(Arrays.asList(traveler));

		List<Traveler> result = travelerService.findTravelersByNameOrEmail(name, email);

		then(result.get(0).getName()).isEqualTo( "Bianca Silva");
		then(result.get(0).getEmail()).isEqualTo( "bianca_silva@gmail.com");

	}

	@Test
	public void shouldUpdateTravelerAndReturnTravelerDetails() {

		Long id = 1L;
		Traveler travelerToUpdate = getTravelerToSaved01();

		Traveler travelerUpdated = getTravelerSaved01();

		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(travelerToUpdate));
		when(repository.save(any(Traveler.class))).thenReturn(travelerUpdated);

		Traveler result = travelerService.updateTraveler(id,travelerToUpdate);

		then(result).isNotNull();
		then(travelerUpdated.getId()).isEqualTo(result.getId());
		then(travelerUpdated.getName()).isEqualTo(result.getName() );
		then(travelerUpdated.getEmail()).isEqualTo( result.getEmail() );

		then(travelerUpdated.getStatus()).isEqualTo( result.getStatus());
		then(travelerUpdated.getPrefixPhone()).isEqualTo( result.getPrefixPhone());
		then(travelerUpdated.getNumberPhone()).isEqualTo(result.getNumberPhone() );

	}


	@Test
	public void shouldReturnMessageExceptionWhenTravelerNotFoundById() {

		Long id = 123L;

		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		when(messageUtils.getMessage(any(),any())).thenReturn("Nenhum viajante encontrado pelo id: 123");

		Exception exception = assertThrows(TravelerException.class, () ->{
			travelerService.findById(id);
		});

		then(exception.getMessage()).isEqualTo( "Nenhum viajante encontrado pelo id: " + id);


	}

	@Test
	public void shouldReturnExceptionWhenCreateTravelerAlreadyExistsWithSameName() {

		String name = "Carlos Garcia";
		String email = "teste.email@teste.com";
		Integer prefixPhone = 77;
		String numberPhone = "98888-7777";
		String document = "77874564656";

		Traveler travelerToSave = getNewTraveler(name, email,prefixPhone,numberPhone,document);

		when(repository.findByNameOrEmail(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(getTravelerSavedList());
		when(messageUtils.getMessage(any())).thenReturn("Já existe outro viajante cadastrado com mesmo nome ou email");


		Exception exception = assertThrows(TravelerException.class, () ->{
			travelerService.createTraveler(travelerToSave);
		});

		then(exception.getMessage()).isEqualTo( "Já existe outro viajante cadastrado com mesmo nome ou email" );
	}

	@Test
	public void shouldReturnExceptionWhenUpdateTravelerAlreadyExistsWithSameName() {

		Traveler travelerToUpdate = getTravelerSaved01();
		travelerToUpdate.setName("Carlos Garcia");

		Traveler travelerSaved = getTravelerSaved03();
		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(travelerToUpdate));

		when(repository.findByNameOrEmail(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(Arrays.asList(travelerSaved));
		when(messageUtils.getMessage(any())).thenReturn("Já existe outro viajante cadastrado com mesmo nome ou email");

		Exception exception = assertThrows(TravelerException.class, () ->{
			travelerService.updateTraveler(1L, travelerToUpdate);
		});

		then(exception.getMessage()).isEqualTo("Já existe outro viajante cadastrado com mesmo nome ou email" );
	}

	@Test
	public void givenNameWhenFindByNameThenReturnListOfTravelers(){
		List<Traveler> travelers = getTravelerSavedList();
		when(repository.findByNameContainingIgnoreCaseOrderByNameAsc(Mockito.anyString())).thenReturn(travelers);
		String name = "travelerName";
		List<Traveler> result = travelerService.findByNameContainingOrderByNameAsc(name);

		then(result).isNotNull();
	}

	@Test
	public void givenNewTravelerWithEmptyEmailWhenCreateTravelerThenSaveTraveler(){

		CreateTravelerRequest request = TravelerMother.getNewTravelerRequest("Hugo Carvalho", "", 88, "98888-7677", "");
		Traveler travelerToSave = TravelerMother.createTravelerRequestToTraveler(request);

		when(repository.findByName(anyString())).thenReturn(Arrays.asList());
		when(repository.save(any(Traveler.class))).thenReturn(TravelerMother.getNewTraveler(request.getName(), request.getEmail(), request.getPrefixPhone(), request.getNumberPhone(), request.getDocument()));

		Traveler result = travelerService.createTraveler(travelerToSave);

		then(result).isNotNull();


	}

	@Test
	public void givenNewTravelerWithNullEmailWhenCreateTravelerThenSaveTraveler(){

		CreateTravelerRequest request = TravelerMother.getNewTravelerRequest("Hugo Carvalho", null, 88, "98888-7677", "");
		Traveler travelerToSave = TravelerMother.createTravelerRequestToTraveler(request);

		when(repository.findByName(anyString())).thenReturn(Arrays.asList());
		when(repository.save(any(Traveler.class))).thenReturn(TravelerMother.getNewTraveler(request.getName(), request.getEmail(), request.getPrefixPhone(), request.getNumberPhone(), request.getDocument()));

		Traveler result = travelerService.createTraveler(travelerToSave);

		then(result).isNotNull();

	}

	@Test
	public void givenNewTravelerWithEmptyEmailWhenUpdateTravelerThenSaveTraveler(){
		Long id = 1L;
		UpdateTravelerRequest request = TravelerMother.getUpdateTravelerRequest(id,"Ana Maria", null, 11, "98888-1111", "50042806739");
		Traveler travelerToUpdate = TravelerMother.updateTravelerRequestToTraveler(request);

		when(repository.findById(anyLong())).thenReturn(Optional.of(TravelerMother.getTravelerSaved01()));
		when(repository.findByName(anyString())).thenReturn(Arrays.asList(TravelerMother.getTravelerSaved01()));
		when(repository.save(any(Traveler.class))).thenReturn(TravelerMother.getTravelerSaved01());

		Traveler result = travelerService.updateTraveler(id, travelerToUpdate);

		then(result.getId()).isEqualTo(1);



	}

	@Test
	public void givenNewTravelerWithNullEmailWhenUpdateTravelerThenSaveTraveler(){

		Long id = 1L;
		UpdateTravelerRequest request = TravelerMother.getUpdateTravelerRequest(id,"Ana Maria", null, 11, "98888-1111", "50042806739");
		Traveler travelerToUpdate = TravelerMother.updateTravelerRequestToTraveler(request);
		when(repository.findById(anyLong())).thenReturn(Optional.of(TravelerMother.getTravelerSaved01()));
		when(repository.findByName(anyString())).thenReturn(Arrays.asList(TravelerMother.getTravelerSaved01()));
		when(repository.save(any(Traveler.class))).thenReturn(TravelerMother.getTravelerSaved01());

		Traveler result = travelerService.updateTraveler(id, travelerToUpdate);

		then(result.getId()).isEqualTo(1);

	}

}
