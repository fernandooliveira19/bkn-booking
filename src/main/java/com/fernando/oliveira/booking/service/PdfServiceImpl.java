package com.fernando.oliveira.booking.service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;

public class PdfServiceImpl implements PdfService {

    private static final String EMPTY_LINE = "\n";

    public Element getTitle(String title) {
        Paragraph paragraph = new Paragraph(title,
                new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(0, 0, 0)));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(EMPTY_LINE);
        paragraph.add(EMPTY_LINE);
        return paragraph;
    }

    public Element getEmptyLine() {
        Paragraph paragraph = new Paragraph("");

        paragraph.add(EMPTY_LINE);

        return paragraph;
    }

    public Element getSubtitle(String subtitle) {
        Paragraph paragraph = new Paragraph(subtitle,
                new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(0, 0, 0)));
        paragraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(EMPTY_LINE);
        paragraph.add(EMPTY_LINE);
        return paragraph;
    }
}
