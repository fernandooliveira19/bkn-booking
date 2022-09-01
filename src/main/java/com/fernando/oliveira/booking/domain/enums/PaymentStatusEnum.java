package com.fernando.oliveira.booking.domain.enums;

public enum PaymentStatusEnum {

    CANCELED("Cancelado"),
    PAID("Pago"),
    PENDING("Pendente");

    private String description;

    PaymentStatusEnum(String description){
        this.description = description;
    }
}
