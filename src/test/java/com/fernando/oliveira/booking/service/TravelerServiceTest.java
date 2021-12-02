package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Traveler;
import com.fernando.oliveira.booking.domain.enums.StatusEnum;
import com.fernando.oliveira.booking.exception.TravelerException;
import com.fernando.oliveira.booking.mother.TravelerMother;
import com.fernando.oliveira.booking.repository.TravelerRepository;
import org.junit.jupiter.api.Assertions;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TravelerServiceTest {
	
	@InjectMocks
	private TravelerServiceImpl travelerService;

	@Mock
	TravelerRepository repository;
	

	@Test
	public void shouldCreateTravelerAndReturnTravelerDetails() {
		
		Traveler travelerToSave = getTravelerToSaved01();
		Traveler travelerSaved = getTravelerSaved01();

		when(repository.save(travelerToSave)).thenReturn(travelerSaved);

		Traveler result = travelerService.createTraveler(travelerToSave);

		assertNotNull(result.getId());
		assertEquals(travelerSaved.getId(),result.getId());
		assertEquals(travelerToSave.getName(),result.getName() );
		assertEquals(travelerToSave.getEmail(), result.getEmail() );

		assertEquals(StatusEnum.ACTIVE.getCode(), result.getStatus());
		assertEquals(travelerToSave.getPrefixPhone(), result.getPrefixPhone());
		assertEquals(travelerToSave.getNumberPhone(),result.getNumberPhone() );
		
	}

	@Test
	public void shouldReturnTravelerListByNameOrEmail(){

		String name = "Joao da Silva";
		String email = "joao.silva@teste.com";

		when(repository.findByNameOrEmail(name, email)).thenReturn(TravelerMother.getTravelerList());
		List<Traveler> result = travelerService.findTravelersByNameOrEmail(name, email);
		assertFalse(result.isEmpty());

	}

	@Test
	public void shouldReturnTravelerById(){
		Long id = 1L;
		Traveler traveler = getTravelerSaved01();

		when(repository.findById(id)).thenReturn(Optional.of(traveler));

		Traveler result = travelerService.findById(id);

		assertEquals(traveler.getId(), result.getId());
		assertEquals(traveler.getName(), result.getName());
		assertEquals(traveler.getEmail(), result.getEmail());
		assertEquals(traveler.getStatus(), result.getStatus());
		assertEquals(traveler.getDocument(), result.getDocument());
		assertEquals(traveler.getPrefixPhone(), result.getPrefixPhone());
		assertEquals(traveler.getNumberPhone(), result.getNumberPhone());

	}

	@Test
	public void shouldReturnAllTravelers(){

		when(repository.findAll()).thenReturn(TravelerMother.getTravelerList());

		List<Traveler> result = travelerService.findAll();


		assertEquals(result.size(), 3);

	}

	@Test
	public void shouldReturnAllTravelersByNameOrEmail(){
		Traveler traveler = getTravelerSaved02();
		String name = "Joao Carlos";
		String email = "joao_carlos@gmail.com";
		when(repository.findByNameOrEmail(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(Arrays.asList(traveler));

		List<Traveler> result = travelerService.findTravelersByNameOrEmail(name, email);

		assertEquals(traveler.getName(), result.get(0).getName());
		assertEquals(traveler.getEmail(), result.get(0).getEmail());

	}

	@Test
	public void shouldUpdateTravelerAndReturnTravelerDetails() {

		Long id = 1L;
		Traveler travelerToUpdate = getTravelerToSaved01();

		Traveler travelerUpdated = getTravelerSaved01();


		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(travelerToUpdate));
		when(repository.save(Mockito.any(Traveler.class))).thenReturn(travelerUpdated);

		Traveler result = travelerService.updateTraveler(id,travelerToUpdate);

		assertNotNull(result.getId());
		assertEquals(travelerUpdated.getId(),result.getId());
		assertEquals(travelerUpdated.getName(),result.getName() );
		assertEquals(travelerUpdated.getEmail(), result.getEmail() );

		assertEquals(travelerUpdated.getStatus(), result.getStatus());
		assertEquals(travelerUpdated.getPrefixPhone(), result.getPrefixPhone());
		assertEquals(travelerUpdated.getNumberPhone(),result.getNumberPhone() );

	}


	@Test
	public void shouldReturnMessageExceptionWhenTravelerNotFoundById() {

		Long id = 123L;

		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		Exception exception = assertThrows(TravelerException.class, () ->{
			travelerService.findById(id);
		});

		assertEquals(exception.getMessage(), "Nenhum viajante encontrado pelo id: " + id);


	}

	@Test
	public void shouldReturnExceptionWhenCreateTravelerAlreadyExistsWithSameName() {

		Traveler travelerToSave = TravelerMother.getTravelerToSaved01();
		travelerToSave.setName("Joao Carlos");
		Traveler travelerSaved = TravelerMother.getTravelerSaved02();
		when(repository.findByNameOrEmail(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(Arrays.asList(travelerSaved));

		Exception exception = assertThrows(TravelerException.class, () ->{
			travelerService.createTraveler(travelerToSave);
		});

		assertEquals(exception.getMessage(), "Já existe outro viajante cadastrado com mesmo nome ou email" );
	}

	@Test
	public void shouldReturnExceptionWhenUpdateTravelerAlreadyExistsWithSameName() {

		Traveler travelerToUpdate = TravelerMother.getTravelerSaved01();
		travelerToUpdate.setName("Maria da Silva");
		Traveler travelerSaved = TravelerMother.getTravelerSaved03();
		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(travelerToUpdate));

		when(repository.findByNameOrEmail(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(Arrays.asList(travelerSaved));

		Exception exception = assertThrows(TravelerException.class, () ->{
			travelerService.updateTraveler(travelerToUpdate.getId(), travelerToUpdate);
		});

		assertEquals(exception.getMessage(), "Já existe outro viajante cadastrado com mesmo nome ou email" );
	}

	@Test
	public void givenNameWhenFindByNameThenReturnListOfTravelers(){
		List<Traveler> travelers = TravelerMother.getTravelerList();
		when(repository.findByNameContainingIgnoreCaseOrderByNameAsc(Mockito.anyString())).thenReturn(travelers);
		String name = "travelerName";
		List<Traveler> result = travelerService.findByNameContainingOrderByNameAsc(name);

		Assertions.assertNotNull(result);
	}

}
