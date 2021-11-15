package com.fernando.oliveira.booking.domain.response;

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
public class DetailBookingResponse {

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private String travelerName;

    private BookingStatusEnum bookingStatus;

    private PaymentStatusEnum paymentStatus;

    private Integer rating;

    private BigDecimal totalAmount;

    private BigDecimal pendingAmount;

    private Integer adults;

    private Integer children;

    private List<DetailLaunchResponse> launchs;

}
