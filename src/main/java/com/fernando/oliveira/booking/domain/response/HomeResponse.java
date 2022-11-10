package com.fernando.oliveira.booking.domain.response;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeResponse implements Serializable {

    private List<ReservedDateResponse> datesSelected;

    private BigDecimal launchAmountTotal;
    private Integer launchOverdueQuantity;
    private BigDecimal launchOverdueAmount;
    private Integer launchToReceiveQuantity;
    private BigDecimal launchToReceiveAmount;
    private BigDecimal launchToOverdueSubTotal;
    private Integer launchToReceiveSiteQuantity;
    private BigDecimal launchToReceiveSiteAmount;
    private Integer bookingReserved;
    private Integer bookingPreReserved;
    private Integer bookingPaid;
    private Integer bookingPending;
    private Integer bookingDirect;
    private Integer bookingSite;
    private Integer bookingTotal;


}
