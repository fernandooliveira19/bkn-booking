package com.fernando.oliveira.booking.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ContractRequestDto {

    private String contractName;
    private String travelerName;
    private String travelerEmail;
    private String travelerDocument;
    private String travelerPhone;
    private String descriptionCheckIn;
    private String descriptionCheckOut;
    private String descriptionPayment;
    private String descriptionPending;
    private String summaryBooking;


}
