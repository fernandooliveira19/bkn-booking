package com.fernando.oliveira.booking.mother;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fernando.oliveira.booking.domain.entity.Traveler;
import com.fernando.oliveira.booking.domain.enums.StatusEnum;
import com.fernando.oliveira.booking.domain.request.CreateTravelerRequest;
import com.fernando.oliveira.booking.domain.request.UpdateTravelerRequest;
import com.fernando.oliveira.booking.domain.response.TravelerDetailResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TravelerMother {


	public static final Long TRAVELER_01_ID = 1L;
	public static final String TRAVELER_01_NAME = "Ana Maria";
	private static final String TRAVELER_01_EMAIL = "ana_maria@gmail.com";
	private static final String TRAVELER_01_DOCUMENT = "50042806739";
	private static final Integer TRAVELER_01_PREFIX_PHONE= 11;
	private static final String TRAVELER_01_NUMBER_PHONE = "98888-1111";

	public static final Long TRAVELER_02_ID = 2L;
	public static final String TRAVELER_02_NAME = "Bianca Silva";
	private static final String TRAVELER_02_EMAIL = "bianca_silva@gmail.com";
	private static final String TRAVELER_02_DOCUMENT = "18421484869";
	private static final Integer TRAVELER_02_PREFIX_PHONE= 22;
	private static final String TRAVELER_02_NUMBER_PHONE = "98888-2222";

	private static final String TRAVELER_03_NAME = "Carlos Garcia";
	private static final String TRAVELER_03_EMAIL = "carlos_garcia@gmail.com";
	private static final Integer TRAVELER_03_PREFIX_PHONE= 33;
	private static final String TRAVELER_03_NUMBER_PHONE = "98888-3333";

	private static final String TRAVELER_04_NAME = "David Souza";
	private static final String TRAVELER_04_EMAIL = "david_souza@gmail.com";
	private static final Integer TRAVELER_04_PREFIX_PHONE= 44;
	private static final String TRAVELER_04_NUMBER_PHONE = "98888-4444";

	private static final String TRAVELER_05_NAME = "Elaine Matos";
	private static final String TRAVELER_05_EMAIL = "elaine_matos@gmail.com";
	private static final Integer TRAVELER_05_PREFIX_PHONE= 55;
	private static final String TRAVELER_05_NUMBER_PHONE = "98888-5555";

	public static final String TRAVELER_06_NAME = "Fernando Oliveira";
	private static final String TRAVELER_06_EMAIL = "fernando_oliveira@gmail.com";
	private static final Integer TRAVELER_06_PREFIX_PHONE= 66;
	private static final String TRAVELER_06_NUMBER_PHONE = "98888-6666";

	public static CreateTravelerRequest getCreateTraveler01Request() {
		CreateTravelerRequest request = new CreateTravelerRequest();
		request.setName(TRAVELER_01_NAME);
		request.setEmail(TRAVELER_01_EMAIL);
		request.setPrefixPhone(TRAVELER_01_PREFIX_PHONE);
		request.setNumberPhone(TRAVELER_01_NUMBER_PHONE);
		return request;
	}

	public static CreateTravelerRequest getCreateTraveler02Request() {
		CreateTravelerRequest request = new CreateTravelerRequest();
		request.setName(TRAVELER_02_NAME);
		request.setEmail(TRAVELER_02_EMAIL);
		request.setPrefixPhone(TRAVELER_02_PREFIX_PHONE);
		request.setNumberPhone(TRAVELER_02_NUMBER_PHONE);
		return request;
	}

	public static CreateTravelerRequest getCreateTraveler05Request() {
		CreateTravelerRequest request = new CreateTravelerRequest();
		request.setName(TRAVELER_05_NAME);
		request.setEmail(TRAVELER_05_EMAIL);
		request.setPrefixPhone(TRAVELER_05_PREFIX_PHONE);
		request.setNumberPhone(TRAVELER_05_NUMBER_PHONE);
		return request;
	}
	public static CreateTravelerRequest getNewTravelerRequest(String name, String email, Integer prefixPhone, String numberPhone, String document) {
		CreateTravelerRequest request = new CreateTravelerRequest();
		request.setName(name);
		request.setEmail(email);
		request.setPrefixPhone(prefixPhone);
		request.setNumberPhone(numberPhone);
		request.setDocument(document);
		return request;
	}

	public static TravelerDetailResponse getDetailTraveler01Response() {

		TravelerDetailResponse response = new TravelerDetailResponse();
		response.setName(TRAVELER_01_NAME);
		response.setEmail(TRAVELER_01_EMAIL);
		response.setPrefixPhone(TRAVELER_01_PREFIX_PHONE);
		response.setNumberPhone(TRAVELER_01_NUMBER_PHONE);
		response.setDocument(TRAVELER_01_DOCUMENT);
		response.setId(1L);
		response.setStatus(StatusEnum.ACTIVE.getCode());
		return response;
			
	}

	public static TravelerDetailResponse getDetailTraveler02Response() {

		TravelerDetailResponse response = new TravelerDetailResponse();
		response.setName(TRAVELER_02_NAME);
		response.setEmail(TRAVELER_02_EMAIL);
		response.setPrefixPhone(TRAVELER_02_PREFIX_PHONE);
		response.setNumberPhone(TRAVELER_02_NUMBER_PHONE);
		response.setDocument(TRAVELER_02_DOCUMENT);
		response.setId(2L);
		response.setStatus(StatusEnum.ACTIVE.getCode());

		return response;

	}

	public static TravelerDetailResponse getDetailTraveler03Response() {

		TravelerDetailResponse response = new TravelerDetailResponse();
		response.setName(TRAVELER_03_NAME);
		response.setEmail(TRAVELER_03_EMAIL);
		response.setPrefixPhone(TRAVELER_03_PREFIX_PHONE);
		response.setNumberPhone(TRAVELER_03_NUMBER_PHONE);
		response.setId(3L);
		response.setStatus(StatusEnum.ACTIVE.getCode());

		return response;

	}
	public static TravelerDetailResponse getDetailTraveler04Response() {

		TravelerDetailResponse response = new TravelerDetailResponse();
		response.setName(TRAVELER_04_NAME);
		response.setEmail(TRAVELER_04_EMAIL);
		response.setPrefixPhone(TRAVELER_04_PREFIX_PHONE);
		response.setNumberPhone(TRAVELER_04_NUMBER_PHONE);
		response.setId(4L);
		response.setStatus(StatusEnum.ACTIVE.getCode());

		return response;

	}
	public static TravelerDetailResponse getDetailTraveler05Response() {

		TravelerDetailResponse response = new TravelerDetailResponse();
		response.setName(TRAVELER_05_NAME);
		response.setEmail(TRAVELER_05_EMAIL);
		response.setPrefixPhone(TRAVELER_05_PREFIX_PHONE);
		response.setNumberPhone(TRAVELER_05_NUMBER_PHONE);
		response.setId(5L);
		response.setStatus(StatusEnum.ACTIVE.getCode());

		return response;

	}

    public static String getCreateRequestJsonValue(CreateTravelerRequest request) throws JsonProcessingException {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        return writer.writeValueAsString(request);
    }

	public static List<Traveler> getTravelerSavedList() {

		return Arrays.asList(getTravelerSaved01(), getTravelerSaved02(), getTravelerSaved03(), getTravelerSaved04(),getTravelerSaved05(),getTravelerSaved06());

	}

	public static Traveler getTravelerToSaved01(){
		return Traveler.builder()
				.name(TRAVELER_01_NAME)
				.email(TRAVELER_01_EMAIL)
				.prefixPhone(TRAVELER_01_PREFIX_PHONE)
				.numberPhone(TRAVELER_01_NUMBER_PHONE)
				.document(TRAVELER_01_DOCUMENT)
				.build();
	}

	public static Traveler getTravelerSaved01(){
		return Traveler.builder()
				.id(TRAVELER_01_ID)
				.name(TRAVELER_01_NAME)
				.email(TRAVELER_01_EMAIL)
				.prefixPhone(TRAVELER_01_PREFIX_PHONE)
				.numberPhone(TRAVELER_01_NUMBER_PHONE)
				.document(TRAVELER_01_DOCUMENT)
				.status(StatusEnum.ACTIVE.getCode())
				.build();
	}
	public static Traveler getTravelerSaved02(){
		return Traveler.builder()
				.id(TRAVELER_02_ID)
				.name(TRAVELER_02_NAME)
				.email(TRAVELER_02_EMAIL)
				.prefixPhone(TRAVELER_02_PREFIX_PHONE)
				.numberPhone(TRAVELER_02_NUMBER_PHONE)
				.document(TRAVELER_02_DOCUMENT)
				.status(StatusEnum.ACTIVE.getCode())
				.build();
	}

	public static Traveler getTravelerSaved03(){
		return Traveler.builder()
				.id(3L)
				.name(TRAVELER_03_NAME)
				.email(TRAVELER_03_EMAIL)
				.prefixPhone(TRAVELER_03_PREFIX_PHONE)
				.numberPhone(TRAVELER_03_NUMBER_PHONE)
				.status(StatusEnum.ACTIVE.getCode())
				.build();
	}

	public static Traveler getTravelerSaved04(){
		return Traveler.builder()
				.id(4L)
				.name(TRAVELER_04_NAME)
				.email(TRAVELER_04_EMAIL)
				.prefixPhone(TRAVELER_04_PREFIX_PHONE)
				.numberPhone(TRAVELER_04_NUMBER_PHONE)
				.status(StatusEnum.ACTIVE.getCode())
				.build();
	}

	public static Traveler getTravelerSaved05(){
		return Traveler.builder()
				.id(5L)
				.name(TRAVELER_05_NAME)
				.email(TRAVELER_05_EMAIL)
				.prefixPhone(TRAVELER_05_PREFIX_PHONE)
				.numberPhone(TRAVELER_05_NUMBER_PHONE)
				.status(StatusEnum.ACTIVE.getCode())
				.build();
	}
	public static Traveler getTravelerSaved06(){
		return Traveler.builder()
				.id(6L)
				.name(TRAVELER_06_NAME)
				.email(TRAVELER_06_EMAIL)
				.prefixPhone(TRAVELER_06_PREFIX_PHONE)
				.numberPhone(TRAVELER_06_NUMBER_PHONE)
				.status(StatusEnum.ACTIVE.getCode())
				.build();
	}

	public static List<TravelerDetailResponse> getAllListTravelerDetailResponse(){
		return Arrays.asList(getDetailTraveler01Response(), getDetailTraveler02Response(), getDetailTraveler03Response());
	}

	public static UpdateTravelerRequest getUpdateTraveler01Request() {
		UpdateTravelerRequest request = new UpdateTravelerRequest();
		request.setName(TRAVELER_01_NAME);
		request.setEmail(TRAVELER_01_EMAIL);
		request.setPrefixPhone(TRAVELER_01_PREFIX_PHONE);
		request.setNumberPhone(TRAVELER_01_NUMBER_PHONE);
		request.setId(1L);
		request.setStatus(StatusEnum.INACTIVE.getCode());
		return request;
	}

	public static Traveler getNewTraveler(String name, String email, Integer prefixPhone, String numberPhone, String document) {
		Traveler traveler = new Traveler();
		traveler.setName(name);
		traveler.setEmail(email);
		traveler.setPrefixPhone(prefixPhone);
		traveler.setNumberPhone(numberPhone);
		traveler.setDocument(document);
		return traveler;
	}

	public static Traveler createTravelerRequestToTraveler(CreateTravelerRequest request) {
		return Traveler.builder()
				.name(request.getName())
				.email(request.getEmail())
				.prefixPhone(request.getPrefixPhone())
				.numberPhone(request.getNumberPhone())
				.document(request.getDocument())
				.build();
	}

	public static UpdateTravelerRequest getUpdateTravelerRequest(Long id, String name, String email, Integer prefixPhone, String numberPhone, String document) {
		UpdateTravelerRequest request = new UpdateTravelerRequest();
		request.setId(id);
		request.setName(name);
		request.setEmail(email);
		request.setPrefixPhone(prefixPhone);
		request.setNumberPhone(numberPhone);
		request.setDocument(document);
		return request;
	}

	public static Traveler updateTravelerRequestToTraveler(UpdateTravelerRequest request) {
		return Traveler.builder()
				.name(request.getName())
				.email(request.getEmail())
				.prefixPhone(request.getPrefixPhone())
				.numberPhone(request.getNumberPhone())
				.document(request.getDocument())
				.build();
	}
}
