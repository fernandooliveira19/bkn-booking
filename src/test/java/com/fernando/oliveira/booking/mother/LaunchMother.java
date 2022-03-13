package com.fernando.oliveira.booking.mother;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentTypeEnum;
import com.fernando.oliveira.booking.domain.request.CreateLaunchRequest;
import com.fernando.oliveira.booking.domain.request.LaunchRequest;
import com.fernando.oliveira.booking.domain.request.UpdateLaunchRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

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

    public static Launch getFirstLaunchFromFirstBooking(){
        Booking booking = BookingMother.getFirstBookingSaved();
        return Launch.builder()
                .booking(booking)
                .amount(BigDecimal.valueOf(1000.0))
                .scheduleDate(LocalDate.of(2021, Month.OCTOBER, 10))
                .paymentStatus(PaymentStatusEnum.PAID)
                .paymentDate(LocalDate.of(2021, Month.OCTOBER, 10))
                .paymentType(PaymentTypeEnum.PIX)
                .build();

    }

    public static Launch getSecondLaunchFromFirstBooking(){
        Booking booking = BookingMother.getFirstBookingSaved();
        return Launch.builder()
                .booking(booking)
                .amount(BigDecimal.valueOf(300.0))
                .scheduleDate(LocalDate.of(2021, Month.OCTOBER, 10))
                .paymentStatus(PaymentStatusEnum.PENDING)
                .paymentDate(null)
                .paymentType(PaymentTypeEnum.PIX)
                .build();

    }

    public static Launch getThirdLaunchFromFirstBooking(){
        Booking booking = BookingMother.getFirstBookingSaved();
        return Launch.builder()
                .booking(booking)
                .amount(BigDecimal.valueOf(200.0))
                .scheduleDate(LocalDate.of(2021, Month.OCTOBER, 15))
                .paymentStatus(PaymentStatusEnum.PENDING)
                .paymentDate(null)
                .paymentType(PaymentTypeEnum.PIX)
                .build();

    }

    public static List<Launch> getLaunchsFromFirstBooking(){
        return Arrays.asList(getFirstLaunchFromFirstBooking(), getSecondLaunchFromFirstBooking(), getThirdLaunchFromFirstBooking());
    }

    public static Launch getLaunchSaved(Booking booking,
                                        BigDecimal amount,
                                        PaymentTypeEnum paymentType,
                                        PaymentStatusEnum paymentStatus,
                                        LocalDate scheduleDate,
                                        LocalDate paymentDate){
        return Launch.builder()
                .booking(booking)
                .amount(amount)
                .paymentType(paymentType)
                .scheduleDate(scheduleDate)
                .paymentStatus(paymentStatus)
                .paymentDate(paymentDate)
                .build();
    }

    public static CreateLaunchRequest getCreateLaunchRequest(BigDecimal amount,
                                                       String scheduleDate,
                                                       String paymentType,
                                                       String paymentStatus,
                                                       String paymentDate) {
        CreateLaunchRequest request = new CreateLaunchRequest();
        request.setAmount(amount);
        request.setPaymentType(paymentType);
        request.setScheduleDate(scheduleDate);
        request.setPaymentStatus(paymentStatus);
        request.setPaymentDate(paymentDate);

        return request;
    }

    public static UpdateLaunchRequest getUpdateLaunchRequest(Long id,
                                                             BigDecimal amount,
                                                             String scheduleDate,
                                                             String paymentType,
                                                             String paymentStatus,
                                                             String paymentDate) {
        UpdateLaunchRequest request = new UpdateLaunchRequest();
        request.setId(id);
        request.setAmount(amount);
        request.setPaymentType(paymentType);
        request.setScheduleDate(scheduleDate);
        request.setPaymentStatus(paymentStatus);
        request.setPaymentDate(paymentDate);

        return request;
    }
}
