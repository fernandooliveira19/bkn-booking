package com.fernando.oliveira.booking.domain.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeLaunchDto {
    private BigDecimal amountTotal;
    private Integer directOverdueQuantity;
    private BigDecimal directOverdueAmount;
    private Integer directToReceiveQuantity;
    private BigDecimal directToReceiveAmount;
    private BigDecimal directOverdueSubTotal;
    private Integer siteQuantity;
    private BigDecimal siteAmount;
}
