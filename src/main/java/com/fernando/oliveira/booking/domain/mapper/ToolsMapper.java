package com.fernando.oliveira.booking.domain.mapper;

import com.fernando.oliveira.booking.domain.dto.PreviewBookingDto;
import com.fernando.oliveira.booking.domain.response.PreviewBookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ToolsMapper {

    PreviewBookingResponse previewBookingDtoToResponse(PreviewBookingDto dto);

}
