package com.fernando.oliveira.booking.mother;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentTypeEnum;
import com.fernando.oliveira.booking.domain.request.CreateLaunchRequest;
import com.fernando.oliveira.booking.domain.request.LaunchRequest;
import com.fernando.oliveira.booking.domain.request.UpdateLaunchRequest;
import com.fernando.oliveira.booking.domain.response.LaunchDetailResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LaunchMother {

    private static final Long BOOKING_01_LAUNCH_01_ID= 100L;
    private static final BigDecimal BOOKING_01_LAUNCH_01_AMOUNT = BigDecimal.valueOf(500.0);
    private static final LocalDate BOOKING_01_LAUNCH_01_SCHEDULE_DATE = LocalDate.of(2020, Month.DECEMBER, 1);
    private static final PaymentTypeEnum BOOKING_01_LAUNCH_01_PAYMENT_TYPE = PaymentTypeEnum.PIX;
    private static final PaymentStatusEnum BOOKING_01_LAUNCH_01_PAYMENT_STATUS = PaymentStatusEnum.PAID;
    private static final LocalDate BOOKING_01_LAUNCH_01_PAYMENT_DATE = LocalDate.of(2020, Month.DECEMBER, 2);

    private static final Long BOOKING_01_LAUNCH_02_ID= 101L;
    private static final BigDecimal BOOKING_01_LAUNCH_02_AMOUNT = BigDecimal.valueOf(250.0);
    private static final LocalDate BOOKING_01_LAUNCH_02_SCHEDULE_DATE = LocalDate.of(2020, Month.DECEMBER, 5);
    private static final PaymentTypeEnum BOOKING_01_LAUNCH_02_PAYMENT_TYPE = PaymentTypeEnum.PIX;
    private static final PaymentStatusEnum BOOKING_01_LAUNCH_02_PAYMENT_STATUS = PaymentStatusEnum.PAID;
    private static final LocalDate BOOKING_01_LAUNCH_02_PAYMENT_DATE = LocalDate.of(2020, Month.DECEMBER, 6);

    private static final Long BOOKING_01_LAUNCH_03_ID= 102L;
    private static final BigDecimal BOOKING_01_LAUNCH_03_AMOUNT = BigDecimal.valueOf(250.0);
    private static final LocalDate BOOKING_01_LAUNCH_03_SCHEDULE_DATE = LocalDate.of(2020, Month.DECEMBER, 8);
    private static final PaymentTypeEnum BOOKING_01_LAUNCH_03_PAYMENT_TYPE = PaymentTypeEnum.PIX;
    private static final PaymentStatusEnum BOOKING_01_LAUNCH_03_PAYMENT_STATUS = PaymentStatusEnum.PAID;
    private static final LocalDate BOOKING_01_LAUNCH_03_PAYMENT_DATE = LocalDate.of(2020, Month.DECEMBER, 9);

    // booking 2

    private static final Long BOOKING_02_LAUNCH_01_ID= 103L;
    private static final BigDecimal BOOKING_02_LAUNCH_01_AMOUNT = BigDecimal.valueOf(300.0);
    private static final LocalDate BOOKING_02_LAUNCH_01_SCHEDULE_DATE = LocalDate.of(2021, Month.JANUARY, 1);
    private static final PaymentTypeEnum BOOKING_02_LAUNCH_01_PAYMENT_TYPE = PaymentTypeEnum.PIX;
    private static final PaymentStatusEnum BOOKING_02_LAUNCH_01_PAYMENT_STATUS = PaymentStatusEnum.PAID;
    private static final LocalDate BOOKING_02_LAUNCH_01_PAYMENT_DATE = LocalDate.of(2021, Month.JANUARY, 2);

    private static final Long BOOKING_02_LAUNCH_02_ID= 104L;
    private static final BigDecimal BOOKING_02_LAUNCH_02_AMOUNT = BigDecimal.valueOf(200.0);
    private static final LocalDate BOOKING_02_LAUNCH_02_SCHEDULE_DATE = LocalDate.of(2021, Month.JANUARY, 5);
    private static final PaymentTypeEnum BOOKING_02_LAUNCH_02_PAYMENT_TYPE = PaymentTypeEnum.PIX;
    private static final PaymentStatusEnum BOOKING_02_LAUNCH_02_PAYMENT_STATUS = PaymentStatusEnum.PAID;
    private static final LocalDate BOOKING_02_LAUNCH_02_PAYMENT_DATE = LocalDate.of(2021, Month.JANUARY, 6);

    private static final Long BOOKING_02_LAUNCH_03_ID= 105L;
    private static final BigDecimal BOOKING_02_LAUNCH_03_AMOUNT = BigDecimal.valueOf(1000.0);
    private static final LocalDate BOOKING_02_LAUNCH_03_SCHEDULE_DATE = LocalDate.of(2021, Month.JANUARY, 8);
    private static final PaymentTypeEnum BOOKING_02_LAUNCH_03_PAYMENT_TYPE = PaymentTypeEnum.PIX;
    private static final PaymentStatusEnum BOOKING_02_LAUNCH_03_PAYMENT_STATUS = PaymentStatusEnum.PENDING;
    private static final LocalDate BOOKING_02_LAUNCH_03_PAYMENT_DATE = null;


    private static final Long BOOKING_03_LAUNCH_01_ID= 106L;
    private static final BigDecimal BOOKING_03_LAUNCH_01_AMOUNT = BigDecimal.valueOf(1200.0);
    private static final LocalDate BOOKING_03_LAUNCH_01_SCHEDULE_DATE = LocalDate.of(2021, Month.JANUARY, 12);
    private static final PaymentTypeEnum BOOKING_03_LAUNCH_01_PAYMENT_TYPE = PaymentTypeEnum.PIX;
    private static final PaymentStatusEnum BOOKING_03_LAUNCH_01_PAYMENT_STATUS = PaymentStatusEnum.PENDING;
    private static final LocalDate BOOKING_03_LAUNCH_01_PAYMENT_DATE = null;

    private static final Long BOOKING_03_LAUNCH_02_ID= 107L;
    private static final BigDecimal BOOKING_03_LAUNCH_02_AMOUNT = BigDecimal.valueOf(800.0);
    private static final LocalDate BOOKING_03_LAUNCH_02_SCHEDULE_DATE = LocalDate.of(2021, Month.JANUARY, 16);
    private static final PaymentTypeEnum BOOKING_03_LAUNCH_02_PAYMENT_TYPE = PaymentTypeEnum.PIX;
    private static final PaymentStatusEnum BOOKING_03_LAUNCH_02_PAYMENT_STATUS = PaymentStatusEnum.PENDING;
    private static final LocalDate BOOKING_03_LAUNCH_02_PAYMENT_DATE = null;

    private static final Long BOOKING_04_LAUNCH_01_ID= 108L;
    private static final BigDecimal BOOKING_04_LAUNCH_01_AMOUNT = BigDecimal.valueOf(2500.0);
    private static final LocalDate BOOKING_04_LAUNCH_01_SCHEDULE_DATE = LocalDate.of(2021, Month.JANUARY, 25);
    private static final PaymentTypeEnum BOOKING_04_LAUNCH_01_PAYMENT_TYPE = PaymentTypeEnum.SITE;
    private static final PaymentStatusEnum BOOKING_04_LAUNCH_01_PAYMENT_STATUS = PaymentStatusEnum.PAID;
    private static final LocalDate BOOKING_04_LAUNCH_01_PAYMENT_DATE = LocalDate.of(2021, Month.JANUARY, 25);

    private static final Long BOOKING_05_LAUNCH_01_ID= 109L;
    private static final BigDecimal BOOKING_05_LAUNCH_01_AMOUNT = BigDecimal.valueOf(3000.0);
    private static final LocalDate BOOKING_05_LAUNCH_01_SCHEDULE_DATE = LocalDate.of(2021, Month.FEBRUARY, 15);
    private static final PaymentTypeEnum BOOKING_05_LAUNCH_01_PAYMENT_TYPE = PaymentTypeEnum.DEPOSIT;
    private static final PaymentStatusEnum BOOKING_05_LAUNCH_01_PAYMENT_STATUS = PaymentStatusEnum.CANCELED;
    private static final LocalDate BOOKING_05_LAUNCH_01_PAYMENT_DATE = null;


    public static Launch getLaunchToSave(BigDecimal amount,
                                              PaymentTypeEnum paymentType,
                                              PaymentStatusEnum paymentStatus,
                                              LocalDate scheduleDate,
                                              LocalDate paymentDate){
        return Launch.builder()
                .amount(amount)
                .paymentType(paymentType)
                .scheduleDate(scheduleDate)
                .paymentStatus(paymentStatus)
                .paymentDate(paymentDate)
                .build();
    }

    public static Launch getFirstLaunchFromFirstBooking(){
        return Launch.builder()
                .id(BOOKING_01_LAUNCH_01_ID)
                .amount(BOOKING_01_LAUNCH_01_AMOUNT)
                .scheduleDate(BOOKING_01_LAUNCH_01_SCHEDULE_DATE)
                .paymentStatus(BOOKING_01_LAUNCH_01_PAYMENT_STATUS)
                .paymentDate(BOOKING_01_LAUNCH_01_PAYMENT_DATE)
                .paymentType(BOOKING_01_LAUNCH_01_PAYMENT_TYPE)
                .build();

    }

    public static Launch getSecondLaunchFromFirstBooking(){
        return Launch.builder()
                .id(BOOKING_01_LAUNCH_02_ID)
                .amount(BOOKING_01_LAUNCH_02_AMOUNT)
                .scheduleDate(BOOKING_01_LAUNCH_02_SCHEDULE_DATE)
                .paymentStatus(BOOKING_01_LAUNCH_02_PAYMENT_STATUS)
                .paymentDate(BOOKING_01_LAUNCH_02_PAYMENT_DATE)
                .paymentType(BOOKING_01_LAUNCH_02_PAYMENT_TYPE)
                .build();

    }

    public static Launch getThirdLaunchFromFirstBooking(){
        return Launch.builder()
                .id(BOOKING_01_LAUNCH_03_ID)
                .amount(BOOKING_01_LAUNCH_03_AMOUNT)
                .scheduleDate(BOOKING_01_LAUNCH_03_SCHEDULE_DATE)
                .paymentStatus(BOOKING_01_LAUNCH_03_PAYMENT_STATUS)
                .paymentDate(BOOKING_01_LAUNCH_03_PAYMENT_DATE)
                .paymentType(BOOKING_01_LAUNCH_03_PAYMENT_TYPE)
                .build();

    }

    public static Launch getFirstLaunchFromSecondBooking(){
        return Launch.builder()
                .id(BOOKING_02_LAUNCH_01_ID)
                .amount(BOOKING_02_LAUNCH_01_AMOUNT)
                .scheduleDate(BOOKING_02_LAUNCH_01_SCHEDULE_DATE)
                .paymentStatus(BOOKING_02_LAUNCH_01_PAYMENT_STATUS)
                .paymentDate(BOOKING_02_LAUNCH_01_PAYMENT_DATE)
                .paymentType(BOOKING_02_LAUNCH_01_PAYMENT_TYPE)
                .build();

    }

    public static Launch getSecondLaunchFromSecondBooking(){
        return Launch.builder()
                .id(BOOKING_02_LAUNCH_02_ID)
                .amount(BOOKING_02_LAUNCH_02_AMOUNT)
                .scheduleDate(BOOKING_02_LAUNCH_02_SCHEDULE_DATE)
                .paymentStatus(BOOKING_02_LAUNCH_02_PAYMENT_STATUS)
                .paymentDate(BOOKING_02_LAUNCH_02_PAYMENT_DATE)
                .paymentType(BOOKING_02_LAUNCH_02_PAYMENT_TYPE)
                .build();

    }

    public static Launch getThirdLaunchFromSecondBooking(){
        return Launch.builder()
                .id(BOOKING_02_LAUNCH_03_ID)
                .amount(BOOKING_02_LAUNCH_03_AMOUNT)
                .scheduleDate(BOOKING_02_LAUNCH_03_SCHEDULE_DATE)
                .paymentStatus(BOOKING_02_LAUNCH_03_PAYMENT_STATUS)
                .paymentDate(BOOKING_02_LAUNCH_03_PAYMENT_DATE)
                .paymentType(BOOKING_02_LAUNCH_03_PAYMENT_TYPE)
                .build();

    }

    public static Launch getFirstLaunchFromThirdBooking(){
        return Launch.builder()
                .id(BOOKING_03_LAUNCH_01_ID)
                .amount(BOOKING_03_LAUNCH_01_AMOUNT)
                .scheduleDate(BOOKING_03_LAUNCH_01_SCHEDULE_DATE)
                .paymentStatus(BOOKING_03_LAUNCH_01_PAYMENT_STATUS)
                .paymentDate(BOOKING_03_LAUNCH_01_PAYMENT_DATE)
                .paymentType(BOOKING_03_LAUNCH_01_PAYMENT_TYPE)
                .build();

    }

    public static Launch getSecondLaunchFromThirdBooking(){
        return Launch.builder()
                .id(BOOKING_03_LAUNCH_02_ID)
                .amount(BOOKING_03_LAUNCH_02_AMOUNT)
                .scheduleDate(BOOKING_03_LAUNCH_02_SCHEDULE_DATE)
                .paymentStatus(BOOKING_03_LAUNCH_02_PAYMENT_STATUS)
                .paymentDate(BOOKING_03_LAUNCH_02_PAYMENT_DATE)
                .paymentType(BOOKING_03_LAUNCH_02_PAYMENT_TYPE)
                .build();

    }

    public static Launch getFirstLaunchFromForthBooking(){
        return Launch.builder()
                .id(BOOKING_04_LAUNCH_01_ID)
                .amount(BOOKING_04_LAUNCH_01_AMOUNT)
                .scheduleDate(BOOKING_04_LAUNCH_01_SCHEDULE_DATE)
                .paymentStatus(BOOKING_04_LAUNCH_01_PAYMENT_STATUS)
                .paymentDate(BOOKING_04_LAUNCH_01_PAYMENT_DATE)
                .paymentType(BOOKING_04_LAUNCH_01_PAYMENT_TYPE)
                .build();

    }

    public static Launch getFirstLaunchFromFifthBooking(){
        return Launch.builder()
                .id(BOOKING_05_LAUNCH_01_ID)
                .amount(BOOKING_05_LAUNCH_01_AMOUNT)
                .scheduleDate(BOOKING_05_LAUNCH_01_SCHEDULE_DATE)
                .paymentStatus(BOOKING_05_LAUNCH_01_PAYMENT_STATUS)
                .paymentDate(BOOKING_05_LAUNCH_01_PAYMENT_DATE)
                .paymentType(BOOKING_05_LAUNCH_01_PAYMENT_TYPE)
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

//    public static Launch getFirstLaunchFromSecondBooking(){
//        return Launch.builder()
//                .amount(BigDecimal.valueOf(2000.0))
//                .scheduleDate(LocalDate.of(2021, Month.FEBRUARY, 20))
//                .paymentStatus(PaymentStatusEnum.PAID)
//                .paymentDate(LocalDate.of(2021, Month.FEBRUARY, 20))
//                .paymentType(PaymentTypeEnum.PIX)
//                .build();
//
//    }
//
//    public static Launch getSecondLaunchFromSecondBooking(){
//        return Launch.builder()
//                .amount(BigDecimal.valueOf(200.0))
//                .scheduleDate(LocalDate.of(2021, Month.FEBRUARY, 27))
//                .paymentStatus(PaymentStatusEnum.PENDING)
//                .paymentDate(null)
//                .paymentType(PaymentTypeEnum.PIX)
//                .build();
//
//    }


    public static LaunchDetailResponse getLaunchDetailResponse(Launch launch) {
        return LaunchDetailResponse.builder()
                .id(launch.getId())
                .amount(launch.getAmount())
                .paymentStatus(launch.getPaymentStatus())
                .paymentType(launch.getPaymentType())
                .scheduleDate(launch.getScheduleDate())
                .paymentDate(launch.getPaymentDate())
                .build();
    }
}
