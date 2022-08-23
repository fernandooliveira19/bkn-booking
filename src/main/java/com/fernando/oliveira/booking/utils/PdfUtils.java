package com.fernando.oliveira.booking.utils;

import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.enums.ContractTypeEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Component
public class PdfUtils {

    private static final String PREFIX_CONTRACT_NAME= "contrato_";
    private static final String PREFIX_AUTHORIZATION_ACCESS_NAME= "autorizacao_";

    public static String getContractName(Booking booking) {
        String firstName = Arrays.stream(booking.getTravelerName().split(" "))
                .findFirst()
                .map(e -> FormatterUtils.removeAccent(e))
                .map(e -> e.toLowerCase())
                .get();

        String localDateFormat = getContractDateFormat(booking.getCheckIn());

        return PREFIX_CONTRACT_NAME + firstName +"_"+localDateFormat;
    }

    public static String getContractDateFormat(LocalDateTime date) {
        String year = String.valueOf(date.getYear());
        String month = String.valueOf(date.getMonthValue());
        String day = String.valueOf(date.getDayOfMonth());
        StringBuilder value = new StringBuilder();
        return value.append(year).append("-").append(month).append("-").append(day).toString();
    }

    public static String getDescriptionCheckIn(LocalDateTime checkIn) {
        String localDate = FormatterUtils.getLocalDateTimeFormat(checkIn);
        String date = StringUtils.substring(localDate, 0,10);
        String hour = StringUtils.substring(localDate, 11,16);

        return "início: " + date + " após " + hour;


    }

    private static final String PREFIX_CONTRACT = "contrato";
    private static final String SEPARATOR = "_";


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

        String localDate = FormatterUtils.getLocalDateTimeFormat(checkOut);
        String date = StringUtils.substring(localDate, 0,10);
        String hour = StringUtils.substring(localDate, 11,16);

        return "término: " + date + " até " + hour;
    }

    public static String getDescriptionPayment(Booking booking) {
        StringBuilder descriptionPayment = new StringBuilder();
        if(booking.getPaymentStatus().equals(PaymentStatusEnum.PAID)) {
            if (booking.getContractType().equals(ContractTypeEnum.SITE)) {

                descriptionPayment
                        .append("O locatário efetuou o pagamento no valor de: ")
                        .append(FormatterUtils.formatCurrencyValue(booking.getAmountTotal()))
                        .append(" através do site vrbo.com (antigo aluguetemporada.com.br)");

            } else {
                descriptionPayment
                        .append("O locatário efetuou o pagamento no valor de: ")
                        .append(FormatterUtils.formatCurrencyValue(booking.getAmountTotal()));

            }
        }else if(booking.getPaymentStatus().equals(PaymentStatusEnum.PENDING)){
            Launch lastLaunch = getLastPendingLaunch(booking);

            descriptionPayment
                    .append("O locatário efetuou o pagamento no valor de: ")
                    .append(FormatterUtils.formatCurrencyValue(booking.getAmountPaid()))
                    .append(" a título de sinal. O restante de ")
                    .append(FormatterUtils.formatCurrencyValue(booking.getAmountPending()))
                    .append(" será pago até o dia ")
                    .append(FormatterUtils.getLocalDateFormat(lastLaunch.getScheduleDate()));


        }
        return descriptionPayment.toString();
    }

    public static String getSummary(Booking booking) {

        if(booking.getPaymentStatus().equals(PaymentStatusEnum.PAID)){
            return "Com isso totalizando, o locatário pagou pela importância de: "+
                    FormatterUtils.formatCurrencyValue(booking.getAmountTotal())+ ", o qual já está incluso a\n" +
                    "taxa de limpeza.";
        }
        if(booking.getPaymentStatus().equals(PaymentStatusEnum.PENDING)){
            StringBuilder summaryPending = new StringBuilder();
            summaryPending.append("Com isso totalizando, o locatário pagará pela importância de: ")
                    .append(FormatterUtils.formatCurrencyValue(booking.getAmountTotal()))
                    .append(", o qual já está incluso a taxa de limpeza.");
            return summaryPending.toString();
        }
        return "";
    }

    public static Launch getLastPendingLaunch(Booking booking){
        return booking.getLaunchs().stream()
                .filter(e -> e.getPaymentStatus().equals(PaymentStatusEnum.PENDING))
                .max(Comparator.comparing(Launch::getScheduleDate))
                .get();

    }

    public static String getAuthorizationAccessName(Booking booking) {
        String firstName = Arrays.stream(booking.getTravelerName().split(" "))
                .findFirst()
                .map(e -> FormatterUtils.removeAccent(e))
                .map(e -> e.toLowerCase())
                .get();

        String localDateFormat = getContractDateFormat(booking.getCheckIn());

        return PREFIX_AUTHORIZATION_ACCESS_NAME + firstName +"_"+localDateFormat;
    }
}
