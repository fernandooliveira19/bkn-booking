package com.fernando.oliveira.booking.domain.enums;

import lombok.Getter;

@Getter
public enum ExceptionMessageEnum {

    INTERNAL_SERVER_ERROR("error.generic.internal.server-error"),
    BOOKING_NOT_FOUND("booking.not.found"),
    BOOKING_ALREADY_EXISTS("booking.already.exists"),
    BOOKING_MUST_HAVE_LAUNCHES("booking.must.have.launches"),
    BOOKING_SUM_LAUNCHES_AMOUNT_ERROR("booking.launches.amount.error"),
    BOOKING_OBSERVATION_REQUIRED("booking.observation.required"),
    BOOKING_CANCEL_LAUNCHES_PAID_ERROR("booking.cancel.launches.paid.error"),
    BOOKING_FINISH_BEFORE_CHECKOUT_ERROR("booking.finish.before.checkout.error"),
    BOOKING_FINISH_LAUNCHES_PENDING_ERROR("booking.finish.launches.pending.error"),
    TRAVELER_NOT_FOUND("traveler.not.found"),
    TRAVELER_LIST_NOT_FOUND("traveler.list.not.found"),
    TRAVELER_ALREADY_EXISTS("traveler.already.exists"),
    TOOLS_CHECK_IN_AFTER_CHECK_OUT_ERROR("tools.check-in.after.check-out.error");
    private  final String messageKey;

    ExceptionMessageEnum(String messageKey){
        this.messageKey = messageKey;
    }

    @Override
    public String toString(){
        return messageKey;
    }
}
