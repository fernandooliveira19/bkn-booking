package com.fernando.oliveira.booking.builder;

import com.fernando.oliveira.booking.domain.dto.ContractRequestDto;
import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.utils.ContractUtils;
import com.fernando.oliveira.booking.utils.FormatterUtils;
import org.springframework.stereotype.Component;

@Component
public class ContractRequestDtoBuilder {


    public ContractRequestDto getRequestContractDto(Booking booking){
        return ContractRequestDto.builder()
                .contractName(ContractUtils.getContractName(booking))
                .travelerName(booking.getTraveler().getName())
                .travelerEmail(booking.getTraveler().getEmail())
                .travelerDocument(FormatterUtils.formatCpf(booking.getTraveler().getDocument()))
                .travelerPhone(FormatterUtils.formatNumberPhoneWithPrefix(
                        booking.getTraveler().getPrefixPhone(),
                        booking.getTraveler().getNumberPhone()))
                .descriptionCheckIn(ContractUtils.getDescriptionCheckIn(booking.getCheckIn()))
                .descriptionCheckOut(ContractUtils.getDescriptionCheckOut(booking.getCheckOut()))
                .descriptionPayment(ContractUtils.getDescriptionPayment(booking))
                .summaryBooking(ContractUtils.getSummary(booking))
                .build();

    }

}
