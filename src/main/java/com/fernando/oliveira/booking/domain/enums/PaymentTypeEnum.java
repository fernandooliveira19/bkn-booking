package com.fernando.oliveira.booking.domain.enums;

public enum PaymentTypeEnum {

    PIX("PIX"),
    TRANSFER("Transferência"),
    LOCAL("Local"),
    SITE("Site"),
    DEPOSIT("Depósito");

    private String description;

    PaymentTypeEnum(String description){
        this.description = description;
    }


}
