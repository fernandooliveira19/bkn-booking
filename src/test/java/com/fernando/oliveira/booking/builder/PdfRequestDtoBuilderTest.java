package com.fernando.oliveira.booking.builder;

import com.fernando.oliveira.booking.domain.dto.PdfRequestDto;
import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.entity.Launch;
import com.fernando.oliveira.booking.domain.enums.ContractTypeEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentStatusEnum;
import com.fernando.oliveira.booking.domain.enums.PaymentTypeEnum;
import com.fernando.oliveira.booking.mother.BookingMother;
import com.fernando.oliveira.booking.mother.LaunchMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PdfRequestDtoBuilderTest {

    @InjectMocks
    private PdfRequestDtoBuilder requestContractDtoBuilder;

    @Test
    void givenBookingPendingWhenBuilderRequestThenReturnContractName(){
        Booking booking = BookingMother.getFirstBookingSaved();
        booking.setLaunchs(Arrays.asList(
                LaunchMother.getFirstLaunchFromFirstBooking(),
                LaunchMother.getSecondLaunchFromFirstBooking(),
                LaunchMother.getThirdLaunchFromFirstBooking()));

        PdfRequestDto result = requestContractDtoBuilder.getRequestContractDto(booking);

        assertEquals("contrato_ana_2021-10-15", result.getContractName());

    }

    @Test
    void givenBookingPendingWhenBuilderRequestThenReturnTravelerDetails(){
        Booking booking = BookingMother.getFirstBookingSaved();
        booking.setLaunchs(Arrays.asList(
                LaunchMother.getFirstLaunchFromFirstBooking(),
                LaunchMother.getSecondLaunchFromFirstBooking(),
                LaunchMother.getThirdLaunchFromFirstBooking()));

        PdfRequestDto result = requestContractDtoBuilder.getRequestContractDto(booking);

        assertEquals("Ana Maria", result.getTravelerName());
        assertEquals("500.428.067-39", result.getTravelerDocument());
        assertEquals("ana_maria@gmail.com", result.getTravelerEmail());
        assertEquals("(11) 98888-1111", result.getTravelerPhone());

    }

    @Test
    void givenBookingPendingWhenBuilderRequestThenReturnRentDetails(){
        Booking booking = BookingMother.getFirstBookingSaved();
        booking.setLaunchs(Arrays.asList(
                LaunchMother.getFirstLaunchFromFirstBooking(),
                LaunchMother.getSecondLaunchFromFirstBooking(),
                LaunchMother.getThirdLaunchFromFirstBooking()));

        PdfRequestDto result = requestContractDtoBuilder.getRequestContractDto(booking);
        LocalDateTime.of(2021, Month.OCTOBER, 15,12,30,0);
        assertEquals("início: 15/10/2021 após 12:30", result.getDescriptionCheckIn());
        assertEquals("término: 20/10/2021 até 18:30", result.getDescriptionCheckOut());
    }

    @Test
    void givenBookingPaidWhenBuilderRequestThenReturnPaymentDetails(){
        Booking booking = BookingMother.getFirstBookingSaved();
        booking.setPaymentStatus(PaymentStatusEnum.PAID);
        booking.setAmountPaid(BigDecimal.valueOf(1500.0));
        booking.setAmountPending(BigDecimal.valueOf(0.0));
        booking.setContractType(ContractTypeEnum.DIRECT);

        Launch launch01 = LaunchMother.getLaunchSaved(booking,
                BigDecimal.valueOf(1000.0),
                PaymentTypeEnum.PIX,
                PaymentStatusEnum.PAID,
                LocalDate.of(2021,10,01),
                LocalDate.of(2021, 10, 01)
        );
        Launch launch02 = LaunchMother.getLaunchSaved(booking,
                BigDecimal.valueOf(500.0),
                PaymentTypeEnum.PIX,
                PaymentStatusEnum.PAID,
                LocalDate.of(2021,10,15),
                LocalDate.of(2021, 10, 15)
        );
        booking.setLaunchs(Arrays.asList(launch01,launch02));

        PdfRequestDto result = requestContractDtoBuilder.getRequestContractDto(booking);

        assertEquals("O locatário efetuou o pagamento no valor de R$ 1.500,00", result.getDescriptionPayment());
        assertEquals("Com isso totalizando, o locatário pagou pela importância de R$ 1.500,00, o qual já está incluso a\n" +
                "taxa de limpeza.", result.getSummaryBooking());

    }

    @Test
    void givenBookingPendingWhenBuilderRequestThenReturnPaymentDetails(){
        Booking booking = BookingMother.getFirstBookingSaved();
        booking.setPaymentStatus(PaymentStatusEnum.PENDING);
        booking.setAmountPaid(BigDecimal.valueOf(1000.0));
        booking.setAmountPending(BigDecimal.valueOf(500.0));

        Launch launch01 = LaunchMother.getLaunchSaved(booking,
                BigDecimal.valueOf(1000.0),
                PaymentTypeEnum.PIX,
                PaymentStatusEnum.PAID,
                LocalDate.of(2021,10,01),
                LocalDate.of(2021, 10, 01)
        );
        Launch launch02 = LaunchMother.getLaunchSaved(booking,
                BigDecimal.valueOf(500.0),
                PaymentTypeEnum.PIX,
                PaymentStatusEnum.PENDING,
                LocalDate.of(2021,10,15),
                null
        );
        booking.setLaunchs(Arrays.asList(launch01,launch02));

        PdfRequestDto result = requestContractDtoBuilder.getRequestContractDto(booking);

        assertEquals("O locatário efetuou o pagamento no valor de R$ 1.000,00 a título de sinal. O restante de R$ 500,00 será pago até o dia 15/10/2021", result.getDescriptionPayment());
        assertEquals("Com isso totalizando, o locatário pagará pela importância de R$ 1.500,00, o qual já está incluso a taxa de limpeza.", result.getSummaryBooking());

    }

    @Test
    void givenBookingPaidFromSiteWhenBuilderRequestThenReturnPaymentDetails(){
        Booking booking = BookingMother.getFirstBookingSaved();
        booking.setPaymentStatus(PaymentStatusEnum.PAID);
        booking.setAmountPaid(BigDecimal.valueOf(1500.0));
        booking.setAmountPending(BigDecimal.valueOf(0.0));
        booking.setContractType(ContractTypeEnum.SITE);

        Launch launch01 = LaunchMother.getLaunchSaved(booking,
                BigDecimal.valueOf(1500.0),
                PaymentTypeEnum.SITE,
                PaymentStatusEnum.PAID,
                LocalDate.of(2021,10,01),
                LocalDate.of(2021, 10, 01)
        );

        booking.setLaunchs(Arrays.asList(launch01));

        PdfRequestDto result = requestContractDtoBuilder.getRequestContractDto(booking);

        assertEquals("O locatário efetuou o pagamento no valor de R$ 1.500,00 através do site vrbo.com (antigo aluguetemporada.com.br)", result.getDescriptionPayment());
        assertEquals("Com isso totalizando, o locatário pagou pela importância de R$ 1.500,00, o qual já está incluso a\n" +
                "taxa de limpeza.", result.getSummaryBooking());

    }

}
