package com.fernando.oliveira.booking.repository;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LaunchRepository extends JpaRepository<Launch, Long> {

    @Query(value = "select lns from launch lns " +
            "inner join lns.booking b " +
            "where b.bookingStatus in ('RESERVED', 'PRE_RESERVED', 'FINISHED') " +
            "and lns.paymentStatus = 'PENDING' ")
    List<Launch> findNextLaunchs();
}
