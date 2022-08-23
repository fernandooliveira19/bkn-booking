package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.domain.entity.Booking;

import java.io.ByteArrayInputStream;

public interface AuthorizationAccessService {
    ByteArrayInputStream createAuthorizationAccess(Booking booking);
}
