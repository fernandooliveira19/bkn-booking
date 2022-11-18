package com.fernando.oliveira.booking.builder;

import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.fernando.oliveira.booking.domain.enums.ContractTypeEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.request.SearchBookingRequest;
import com.fernando.oliveira.booking.utils.FormatterUtils;
import org.springframework.stereotype.Component;

@Component
public class BookingBuilder {

    public SearchBookingRequest searchBookingRequestBuilder(String date, PaymentStatusEnum paymentStatus,
                                                            BookingStatusEnum bookingStatus,ContractTypeEnum contractType) {
        return SearchBookingRequest.builder()
                .date(FormatterUtils.getLocalDateFormat(date))
                .bookingStatus(bookingStatus)
                .paymentStatus(paymentStatus)
                .contractType(contractType)
                .build();
    }
}
