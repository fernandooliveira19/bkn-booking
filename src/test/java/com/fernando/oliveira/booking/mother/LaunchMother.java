package com.fernando.oliveira.booking.mother;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LaunchMother {

    private LaunchMother(){

    }

    public static Launch getLaunchToSave(BigDecimal amount,
                                              PaymentTypeEnum paymentType,
                                              PaymentStatusEnum paymentStatus,
                                              LocalDate scheculeDate,
                                              LocalDate paymentDate){
        return Launch.builder()
                .amount(amount)
                .paymentType(paymentType)
                .scheduleDate(scheculeDate)
                .paymentStatus(paymentStatus)
                .paymentDate(paymentDate)
                .build();
    }

    public static Launch getLaunchSaved(Booking booking,
                                        BigDecimal amount,
                                        PaymentTypeEnum paymentType,
                                        PaymentStatusEnum paymentStatus,
                                        LocalDate scheduleDate,
                                        LocalDate paymentDate){
        return Launch.builder()
                .id(101L)
                .booking(booking)
                .amount(amount)
                .paymentType(paymentType)
                .scheduleDate(scheduleDate)
                .paymentStatus(paymentStatus)
                .paymentDate(paymentDate)
                .build();
    }
}
