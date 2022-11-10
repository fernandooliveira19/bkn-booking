package com.fernando.oliveira.booking.domain.enums;

public enum PaymentStatusEnum {

    CANCELED("Cancelado"),
    PAID("Pago"),
    PENDING("Pendente"),
    TO_RECEIVE("A receber");

    private String description;

    PaymentStatusEnum(String description){
        this.description = description;
    }
}
