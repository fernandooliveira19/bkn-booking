package com.fernando.oliveira.booking.service.impl;

import com.fernando.oliveira.booking.builder.PdfRequestDtoBuilder;
import com.fernando.oliveira.booking.domain.dto.PdfRequestDto;
import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.service.AuthorizationAccessService;
import com.fernando.oliveira.booking.service.BookingService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class AuthorizationAccessServiceImpl extends PdfServiceImpl implements AuthorizationAccessService {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PdfRequestDtoBuilder pdfRequestDtoBuilder;

    @Override
    public ByteArrayInputStream createAuthorizationAccess(Booking booking) {
        PdfRequestDto requestDto = pdfRequestDtoBuilder.getRequestAuthorizationAccessDto(booking);

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(getTitle("AUTORIZAÇÃO DE LOCAÇÃO POR TEMPORADA"));

            document.add(getSubtitle("Rua Rio de Janeiro, 50 - Apto 617/618 - Edifício Bandeirantes", Element.ALIGN_CENTER));

            getIdentificationParts(requestDto, document);


            getRent(requestDto, document);

            document.add(getPeopleTable());

            document.add(getEmptyLine());
            document.add(getEmptyLine());

            getFooter(document);

            document.close();

        } catch (IOException io) {
            System.err.println(io.getCause());

        } catch (DocumentException de) {
            System.err.println(de.getCause());
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    private void getIdentificationParts(PdfRequestDto requestContract, Document document) throws DocumentException {
        document.add(getEmptyLine());
        document.add(getDefaultParagraph("Eu, Fernando Augusto Machado de Oliveira, proprietário do apartamento 617-618, inscrito pelo"));
        document.add(getDefaultParagraph("CPF: 296.830.188-82, autorizo o sr(a) responsável pela locação:"));

        document.add(getBoldParagraph("Nome:"));
        document.add(getDefaultParagraph(requestContract.getTravelerName()));
        document.add(getBoldParagraph("Telefone:"));
        document.add(getDefaultParagraph(requestContract.getTravelerPhone()));
        if(!StringUtils.isBlank(requestContract.getTravelerDocument())){
            document.add(getBoldParagraph("CPF:"));
            document.add(getDefaultParagraph(requestContract.getTravelerDocument()));
        }
        document.add(getEmptyLine());
        document.add(getDefaultParagraph("e os ocupantes descritos abaixo a utilizar o apartamento no período de:"));

        document.add(getEmptyLine());
    }

    private void getRent(PdfRequestDto requestContract, Document document) throws DocumentException {
        document.add(getDefaultParagraph(requestContract.getDescriptionCheckIn()));
        document.add(getDefaultParagraph(requestContract.getDescriptionCheckOut()));
        document.add(getEmptyLine());
        document.add(getDefaultParagraph("A permanência do locatário no imóvel fora do período citado pode ensejar multas e indenização, nos termos da lei."));
        document.add(getEmptyLine());
    }

    private Element getPeopleTable() {
        try {
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            table.setWidths(new int[] { 1, 4, 1, 2 });

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("  ", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Nome", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Idade", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Documento", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            for(int i = 1; i < 9; i++) {

                PdfPCell cell;

                cell = new PdfPCell(new Phrase( String.valueOf(i)));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("                         "));

                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("                         "));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("                           "));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

            }

            return table;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
