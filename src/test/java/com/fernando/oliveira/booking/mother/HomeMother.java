package com.fernando.oliveira.booking.mother;

import com.fernando.oliveira.booking.domain.response.ReservedDateResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HomeMother {

    public static List<ReservedDateResponse> getReservedDatesResponse() {

        ReservedDateResponse day01 = ReservedDateResponse
                .builder().year(2022).month(10).day(15).build();
        ReservedDateResponse day02 = ReservedDateResponse
                .builder().year(2022).month(10).day(16).build();
        ReservedDateResponse day03 = ReservedDateResponse
                .builder().year(2022).month(10).day(17).build();
        ReservedDateResponse day04 = ReservedDateResponse
                .builder().year(2022).month(10).day(18).build();
        ReservedDateResponse day05 = ReservedDateResponse
                .builder().year(2022).month(10).day(19).build();
        ReservedDateResponse day06 = ReservedDateResponse
                .builder().year(2022).month(10).day(20).build();

        return Arrays.asList(day01, day02, day03, day04, day05, day06);
    }
}
