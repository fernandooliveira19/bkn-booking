package com.fernando.oliveira.booking.repository;

import com.fernando.oliveira.booking.domain.entity.Traveler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelerRepository extends JpaRepository<Traveler, Long> {

    Optional<List<Traveler>> findByNameOrEmail(String name, String email);

    Optional<List<Traveler>> findByNameContainingIgnoreCaseOrderByNameAsc(String name);

    @Query("select t from traveler t where t.status = 'A' order by t.name")
    Optional<List<Traveler>> findActiveTravelers();

    Optional<List<Traveler>> findByName(String email);
}
