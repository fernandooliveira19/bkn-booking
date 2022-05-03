package com.fernando.oliveira.booking.utils;

import com.fernando.oliveira.booking.domain.entity.Booking;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Component
public class ContractUtils {

    private static final String PREFIX_CONTRACT_NAME= "contrato_";

    public static String getContractName(Booking booking) {
        String firstName = Arrays.stream(booking.getTravelerName().split(" "))
                .findFirst()
                .map(e -> FormatterUtils.removeAccent(e))
                .map(e -> e.toLowerCase())
                .get();

        String localDateFormat = getContractDateFormat(booking.getCheckIn());

        return PREFIX_CONTRACT_NAME + firstName +"_"+localDateFormat+".pdf";
    }

    public static String getContractDateFormat(LocalDateTime date) {
        String year = String.valueOf(date.getYear());
        String month = String.valueOf(date.getMonthValue());
        String day = String.valueOf(date.getDayOfMonth());
        StringBuilder value = new StringBuilder();
        return value.append(year).append("-").append(month).append("-").append(day).toString();
    }

    public static String getDescriptionCheckIn(LocalDateTime checkIn) {
        String localDate = FormatterUtils.getLocalDateFormat(checkIn);
        String date = StringUtils.substring(localDate, 0,10);
        String hour = StringUtils.substring(localDate, 11,16);

        return "início: " + date + " após " + hour;


    }

    private static final String PREFIX_CONTRACT = "contrato";
    private static final String SEPARATOR = "_";


//    public static String getContractName(RequestContract requestContract) {
//
//        return PREFIX_CONTRACT + SEPARATOR + getSimpleCheckinFormat(requestContract.getCheckIn()) + SEPARATOR + getFirstTravelerName(requestContract.getTravelerName());
//    }

    public static String getSimpleCheckinFormat(String checkIn) {
        String hours = checkIn.substring(checkIn.indexOf("T"));
        return checkIn.replace(hours, "").trim();
    }

    public static String getFirstTravelerName(String travelerName) {
        Optional<String> name = Arrays.stream(travelerName.split(" "))
                .map(e -> e.toLowerCase())
                .map(e -> removeAccent(e))
                .findFirst();
        return name.get();
    }
    public static HttpHeaders getHttpHeaders(String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename="+ fileName +".pdf");
        return headers;
    }
    public static String removeAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static String formatDateToBrPattern(String value) {
        LocalDateTime date = LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_TIME);
        return date.toString();
    }

    public static String getDescriptionCheckOut(LocalDateTime checkOut) {

        String localDate = FormatterUtils.getLocalDateFormat(checkOut);
        String date = StringUtils.substring(localDate, 0,10);
        String hour = StringUtils.substring(localDate, 11,16);

        return "término: " + date + " até " + hour;
    }
}
