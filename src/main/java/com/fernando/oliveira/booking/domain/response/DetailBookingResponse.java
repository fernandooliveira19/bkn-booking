package com.fernando.oliveira.booking.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "Detalhe de reserva")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetailBookingResponse {

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private Long travelerId;

    private BookingStatusEnum bookingStatus;

    private PaymentStatusEnum paymentStatus;

    private Integer rating;

    private BigDecimal totalAmount;

    private BigDecimal amountPending;

    private Integer adults;

    private Integer children;

    private List<DetailLaunchResponse> launchs;

}
