package com.fernando.oliveira.booking.builder;

import com.fernando.oliveira.booking.domain.dto.PdfRequestDto;
import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.mother.BookingMother;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PdfRequestDtoBuilderTest {

    @InjectMocks
    private PdfRequestDtoBuilder requestContractDtoBuilder;

    @Test
    void givenBookingPendingWhenBuilderRequestThenReturnContractName(){
        Booking booking = BookingMother.getBookingSaved01();

        PdfRequestDto result = requestContractDtoBuilder.getRequestContractDto(booking);

        assertEquals("contrato_ana_2020-12-15", result.getContractName());

    }

    @Test
    void shouldReturnTravelerDetails(){
        Booking booking = BookingMother.getBookingSaved01();

        PdfRequestDto result = requestContractDtoBuilder.getRequestContractDto(booking);

        assertEquals("Ana Maria", result.getTravelerName());
        assertEquals("500.428.067-39", result.getTravelerDocument());
        assertEquals("ana_maria@gmail.com", result.getTravelerEmail());
        assertEquals("(11) 98888-1111", result.getTravelerPhone());

    }

    @Test
    void givenBookingPendingWhenBuilderRequestThenReturnRentDetails(){
        Booking booking = BookingMother.getBookingSaved02();

        PdfRequestDto result = requestContractDtoBuilder.getRequestContractDto(booking);

        assertEquals("início: 01/01/2021 após 10:00", result.getDescriptionCheckIn());
        assertEquals("término: 15/01/2021 até 18:00", result.getDescriptionCheckOut());
    }

    @Test
    void givenBookingPaidWhenBuilderRequestThenReturnPaymentDetails(){
        Booking booking = BookingMother.getBookingSaved06();

        PdfRequestDto result = requestContractDtoBuilder.getRequestContractDto(booking);

        assertEquals("O locatário efetuou o pagamento no valor de R$ 4.000,00", result.getDescriptionPayment());
        assertEquals("Com isso totalizando, o locatário pagou pela importância de R$ 4.000,00, o qual já está incluso a\n" +
                "taxa de limpeza.", result.getSummaryBooking());

    }

    @Test
    void givenBookingPendingWhenBuilderRequestThenReturnPaymentDetails(){
        Booking booking = BookingMother.getBookingSaved02();

        PdfRequestDto result = requestContractDtoBuilder.getRequestContractDto(booking);

        assertEquals("O locatário efetuou o pagamento no valor de R$ 500,00 a título de sinal. O restante de R$ 1.000,00 será pago até o dia 08/01/2021", result.getDescriptionPayment());
        assertEquals("Com isso totalizando, o locatário pagará pela importância de R$ 1.500,00, o qual já está incluso a taxa de limpeza.", result.getSummaryBooking());

    }

    @Test
    @Disabled
    void givenBookingPaidFromSiteWhenBuilderRequestThenReturnPaymentDetails(){
        Booking booking = BookingMother.getBookingSaved04();

        PdfRequestDto result = requestContractDtoBuilder.getRequestContractDto(booking);

        assertEquals("O locatário efetuou o pagamento no valor de R$ 2.500,00 através do site vrbo.com (antigo aluguetemporada.com.br)", result.getDescriptionPayment());
        assertEquals("Com isso totalizando, o locatário pagou pela importância de R$ 2.500,00, o qual já está incluso a\n" +
                "taxa de limpeza.", result.getSummaryBooking());

    }

}
