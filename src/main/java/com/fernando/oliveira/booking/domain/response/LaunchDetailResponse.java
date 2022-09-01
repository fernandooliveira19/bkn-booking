package com.fernando.oliveira.booking.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LaunchDetailResponse implements Serializable {

    private Long id;

    private BigDecimal amount;

    private LocalDate paymentDate;

    private LocalDate scheduleDate;

    private PaymentTypeEnum paymentType;

    private PaymentStatusEnum paymentStatus;

    private String travelerName;

    private LocalDateTime checkIn;
}
