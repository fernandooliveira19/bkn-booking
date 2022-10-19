package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.request.ContractRequest;

import java.io.ByteArrayInputStream;

public interface ContractService {
    ByteArrayInputStream createContract(Booking booking);
}
