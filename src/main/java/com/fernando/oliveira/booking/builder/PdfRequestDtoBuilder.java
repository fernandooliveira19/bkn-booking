package com.fernando.oliveira.booking.builder;

import com.fernando.oliveira.booking.domain.dto.PdfRequestDto;
import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.utils.PdfUtils;
import com.fernando.oliveira.booking.utils.FormatterUtils;
import org.springframework.stereotype.Component;

@Component
public class PdfRequestDtoBuilder {


    public PdfRequestDto getRequestContractDto(Booking booking){
        return PdfRequestDto.builder()
                .contractName(PdfUtils.getContractName(booking))
                .travelerName(booking.getTraveler().getName())
                .travelerEmail(booking.getTraveler().getEmail())
                .travelerDocument(FormatterUtils.formatCpf(booking.getTraveler().getDocument()))
                .travelerPhone(FormatterUtils.formatNumberPhoneWithPrefix(
                        booking.getTraveler().getPrefixPhone(),
                        booking.getTraveler().getNumberPhone()))
                .descriptionCheckIn(PdfUtils.getDescriptionCheckIn(booking.getCheckIn()))
                .descriptionCheckOut(PdfUtils.getDescriptionCheckOut(booking.getCheckOut()))
                .descriptionPayment(PdfUtils.getDescriptionPayment(booking))
                .summaryBooking(PdfUtils.getSummary(booking))
                .build();

    }

    public PdfRequestDto getRequestAuthorizationAccessDto(Booking booking){
        return PdfRequestDto.builder()
                .contractName(PdfUtils.getContractName(booking))
                .travelerName(booking.getTraveler().getName())
                .travelerDocument(FormatterUtils.formatCpf(booking.getTraveler().getDocument()))
                .travelerPhone(FormatterUtils.formatNumberPhoneWithPrefix(
                        booking.getTraveler().getPrefixPhone(),
                        booking.getTraveler().getNumberPhone()))
                .descriptionCheckIn(PdfUtils.getDescriptionCheckIn(booking.getCheckIn()))
                .descriptionCheckOut(PdfUtils.getDescriptionCheckOut(booking.getCheckOut()))
                .summaryBooking(PdfUtils.getSummary(booking))
                .build();

    }

}
