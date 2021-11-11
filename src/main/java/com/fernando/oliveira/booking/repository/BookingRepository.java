package com.fernando.oliveira.booking.repository;

import com.fernando.oliveira.booking.domain.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

 }
