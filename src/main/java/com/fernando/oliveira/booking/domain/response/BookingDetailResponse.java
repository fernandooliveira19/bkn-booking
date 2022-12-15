package com.fernando.oliveira.booking.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.ContractTypeEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "Detalhe de reserva")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
public class BookingDetailResponse {

    private Long id;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private Long travelerId;

    private BookingStatusEnum bookingStatus;

    private PaymentStatusEnum paymentStatus;

    private Integer rating;

    private BigDecimal amountTotal;

    private BigDecimal amountPending;

    private BigDecimal amountPaid;

    private Integer adults;

    private Integer children;

    private String observation;

    private String travelerName;

    private ContractTypeEnum contractType;

    private List<LaunchDetailResponse> launches;

    private BigDecimal websiteServiceFee;

}
