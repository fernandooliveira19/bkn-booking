package com.fernando.oliveira.booking.domain.dto;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.request.SearchBookingRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

//        if(example.getDate() != null){
//            List<Predicate> conditionsList = new ArrayList<Predicate>();
//
//            Predicate onStart = criteriaBuilder.greaterThan(root.get("checkIn"), example.getDate());
//            Predicate onEnd = criteriaBuilder.lessThan(root.get("checkOut"),example.getDate());
//            predicates.add(onStart);
//            predicates.add(onEnd);
//
//        }


        return andTogether(predicates, criteriaBuilder);
    }
    private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder criteriaBuilder){

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
