package com.fernando.oliveira.booking.repository;

import com.fernando.oliveira.booking.domain.entity.Launch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaunchRepository extends JpaRepository<Launch, Long> {
}
