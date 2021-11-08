package com.fernando.oliveira.booking.domain.enums;

public enum BookingStatusEnum {

    RESERVED("Reservado"),
    CANCELED("Cancelado"),
    PRE_RESERVED("Pre Reservado"),
    FINISHED("Finalizado");

    private String description;

    BookingStatusEnum(String description){
        this.description = description;
    }


}
