package com.fernando.oliveira.booking.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name="booking")
@Table(name="BOOKING" )
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @Column(name="ID", nullable=false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "booking")
    private List<Launch> launchs;

    @ManyToOne
    @JoinColumn(name="TRAVELER_ID", nullable=false)
    private Traveler traveler;

    @Column(name="CHECK_IN", nullable=false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime checkIn;

    @Column(name="CHECK_OUT", nullable=false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime checkOut;

    @Column(name="AMOUNT", nullable=false)
    private BigDecimal totalAmount;

    @Column(name="ADULTS")
    private Integer adults;

    @Column(name="CHILDREN")
    private Integer children;

    @Column(name="BOOKING_STATUS", nullable=false)
    @Enumerated(EnumType.STRING)
    private BookingStatusEnum bookingStatus;

    @Column(name="PAYMENT_STATUS", nullable=false)
    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum paymentStatus;

    @Column(name="INSERT_DATE", nullable=false, updatable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime insertDate;

    @Column(name="LAST_UPDATE")
    private LocalDateTime lastUpdate;

    @Column(name="RATING")
    private Integer rating;

    @Column(name="AMOUNT_PENDING")
    private BigDecimal amountPending;

    @Column(name="OBSERVATION")
    private String observation;
}
