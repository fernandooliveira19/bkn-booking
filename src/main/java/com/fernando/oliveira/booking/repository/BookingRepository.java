package com.fernando.oliveira.booking.repository;

import com.fernando.oliveira.booking.domain.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

 @Query(value = "select b from booking b where :checkIn between b.checkIn and b.checkOut or (:checkOut between b.checkIn and b.checkOut) or (b.checkIn >= :checkIn and b.checkOut <= :checkOut)")
 List<Booking> findBookingsByDate(
         @Param("checkIn") LocalDateTime checkIn,
         @Param("checkOut")LocalDateTime checkOut);

 }
