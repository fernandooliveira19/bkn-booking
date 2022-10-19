package com.fernando.oliveira.booking.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity(name="traveler")
@Table(name="traveler" )
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Traveler {

    @Id
    @Column(name="ID", nullable=false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "traveler")
    private List<Booking> bookings;

    @Column(name="NAME", nullable=false)
    private String name;

    @Column(name="EMAIL")
    private String email;

    @Column(name="DOCUMENT")
    private String document;

    @Column(name="STATUS", nullable = false)
    private String status;

    @Column(name="PREFIX_PHONE", nullable = false)
    private Integer prefixPhone;

    @Column(name="NUMBER_PHONE", nullable = false)
    private String numberPhone;

    @Column(name = "INSERT_DATE", nullable = false, updatable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime insertDate;

    @Column(name = "LAST_UPDATE_DATE")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lastUpdateDate;
}
