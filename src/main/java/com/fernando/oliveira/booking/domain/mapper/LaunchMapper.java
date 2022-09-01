package com.fernando.oliveira.booking.domain.mapper;

import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.request.CreateLaunchRequest;
import com.fernando.oliveira.booking.domain.request.UpdateLaunchRequest;
import com.fernando.oliveira.booking.domain.response.LaunchDetailResponse;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {BookingMapper.class})
public interface LaunchMapper {

    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "paymentDate", target = "paymentDate", qualifiedByName = "formatLocalDate")
    Launch createRequestToEntity(CreateLaunchRequest request);

    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "paymentDate", target = "paymentDate", qualifiedByName = "formatLocalDate")
    Launch updateRequestToEntity(UpdateLaunchRequest request);

    @Named("formatLocalDate")
    static LocalDate formatLocalDate(String date){

        if(StringUtils.isBlank(date)) {
            return null;
        }
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }
    @Mapping(source = "booking.checkIn",target = "checkIn")
    @Mapping(source = "booking.traveler.name", target = "travelerName")
    LaunchDetailResponse launchToDetailLaunchResponse(Launch launch);

}
