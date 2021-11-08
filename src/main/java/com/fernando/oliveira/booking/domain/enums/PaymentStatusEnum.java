package com.fernando.oliveira.booking.domain.enums;

public enum PaymentStatusEnum {

    PAID("Pago"),
    PENDING("Pendente");

    private String description;

    PaymentStatusEnum(String description){
        this.description = description;
    }
}
