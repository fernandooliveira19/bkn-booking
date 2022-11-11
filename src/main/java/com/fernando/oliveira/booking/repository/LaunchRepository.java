package com.fernando.oliveira.booking.repository;

import com.fernando.oliveira.booking.domain.entity.Launch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaunchRepository extends JpaRepository<Launch, Long> {

    @Query(value = "select obj from launch obj where obj.paymentStatus in ('PENDING', 'TO_RECEIVE') order by obj.scheduleDate")
    List<Launch> findNextLaunches();
}
