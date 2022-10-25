package com.fernando.oliveira.booking.domain.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservedDateResponse implements Serializable {
    private Integer year;
    private Integer month;
    private Integer day;

}
