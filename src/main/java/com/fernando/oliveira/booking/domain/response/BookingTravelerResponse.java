package com.fernando.oliveira.booking.domain.response;

import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.ContractTypeEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingTravelerResponse {

    private Long bookingId;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private BookingStatusEnum bookingStatus;

    private BigDecimal amountTotal;

    private ContractTypeEnum contractType;

    private String observation;
}
