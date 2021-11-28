package com.fernando.oliveira.booking.domain.enums;

public enum StatusEnum {
	ACTIVE("A","Ativo"),
	INACTIVE("I","Inativo");
	
	private String code;
	private String description;
	
	StatusEnum(String code, String description) {
	
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
    public static StatusEnum toEnum(String code) {
		
		if(code == null){
			return null;
		}
		
		for(StatusEnum statusEnum : StatusEnum.values()) {
			if(statusEnum.getCode().equals(code)) {
				return statusEnum;
			}
		}
		
		throw new IllegalArgumentException("código inexistente: "+ code);
		
	}
}
