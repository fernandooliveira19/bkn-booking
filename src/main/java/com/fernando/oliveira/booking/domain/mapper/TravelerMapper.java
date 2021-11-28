package com.fernando.oliveira.booking.domain.mapper;

import com.fernando.oliveira.booking.domain.entity.Traveler;
import com.fernando.oliveira.booking.domain.request.CreateTravelerRequest;
import com.fernando.oliveira.booking.domain.request.UpdateTravelerRequest;
import com.fernando.oliveira.booking.domain.response.TravelerDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TravelerMapper {
	
	Traveler requestToCreateTraveler(CreateTravelerRequest request);

	Traveler requestToUpdateTraveler(UpdateTravelerRequest request);

	TravelerDetailResponse travelerToTravelerDetailResponse(Traveler traveler);

}
