package com.fernando.oliveira.booking.domain.enums;

public enum ContractTypeEnum {

        DIRECT("Direto"),
        SITE("Site");

        private String description;

    ContractTypeEnum(String description){
            this.description = description;
        }

}
