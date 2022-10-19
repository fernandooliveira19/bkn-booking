package com.fernando.oliveira.booking.domain.dto;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.request.SearchBookingRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class BookingSpec implements Specification<Booking> {

    private final SearchBookingRequest example;

    public BookingSpec(SearchBookingRequest example){
        this.example = example;
    }


    @Override
    public Predicate toPredicate(Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if(example.getBookingStatus() != null){
            predicates.add(criteriaBuilder.equal(root.get("bookingStatus"), example.getBookingStatus()));
        }

        if(example.getPaymentStatus() != null){
            predicates.add(criteriaBuilder.equal(root.get("paymentStatus"), example.getPaymentStatus()));
        }
        if(example.getContractType() != null){
            predicates.add(criteriaBuilder.equal(root.get("contractType"), example.getContractType()));
        }

        query.orderBy(criteriaBuilder.asc(root.get("checkIn")));

        return andTogether(predicates, criteriaBuilder);
    }
    private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder criteriaBuilder){

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
