package com.fernando.oliveira.booking.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentTypeEnum;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="launch" )
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Launch {

    @Id
    @Column(name="ID", nullable=false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="BOOKING_ID")
    private Booking booking;

    @Column(name="AMOUNT", nullable=false)
    private BigDecimal amount;

    @Column(name="PAYMENT_DATE", nullable=true)
    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

    @Column(name="SCHEDULE_DATE", nullable=false)
    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate scheduleDate;

    @Column(name="PAYMENT_TYPE", nullable=false)
    private PaymentTypeEnum paymentType;

    @Column(name="PAYMENT_STATUS", nullable=false)
    private PaymentStatusEnum paymentStatus;


}
