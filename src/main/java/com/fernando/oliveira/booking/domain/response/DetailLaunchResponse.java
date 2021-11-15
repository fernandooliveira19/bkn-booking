package com.fernando.oliveira.booking.domain.response;

import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class DetailLaunchResponse implements Serializable {

    private Long id;

    private BigDecimal amount;

    private LocalDate paymentDate;

    private LocalDate scheduleDate;

    private String paymentType;

    private String paymentStatus;
}
