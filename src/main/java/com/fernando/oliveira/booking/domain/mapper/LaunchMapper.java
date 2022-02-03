package com.fernando.oliveira.booking.domain.mapper;

import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.request.CreateLaunchRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {BookingMapper.class})
public interface LaunchMapper {

    @Mapping(source = "amount", target = "amount", qualifiedByName = "convertStringToBigDecimal")
    Launch createRequestToEntity(CreateLaunchRequest request);

}
