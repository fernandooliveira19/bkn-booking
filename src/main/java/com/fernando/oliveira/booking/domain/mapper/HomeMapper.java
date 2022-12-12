package com.fernando.oliveira.booking.domain.mapper;

import com.fernando.oliveira.booking.domain.dto.HomeDto;
import com.fernando.oliveira.booking.domain.response.HomeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HomeMapper {

    HomeResponse dtoToResponse(HomeDto homeDto);

}
