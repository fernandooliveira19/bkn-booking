package com.fernando.oliveira.booking.repository;

import com.fernando.oliveira.booking.domain.entity.Traveler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelerRepository extends JpaRepository<Traveler, Long> {

    List<Traveler> findByNameOrEmail(String name, String email);

    List<Traveler> findByNameContainingIgnoreCaseOrderByNameAsc(String name);

    @Query("select t from traveler t where t.status = 'A' order by t.name")
    List<Traveler> findActiveTravelers();
}
