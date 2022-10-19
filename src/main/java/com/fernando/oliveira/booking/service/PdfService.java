package com.fernando.oliveira.booking.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;

import java.io.IOException;

public interface PdfService {

    Element getTitle(String title);

    Element getEmptyLine();

    Element getSubtitle(String subtitle, int alignment);

    void getFooter(Document document) throws DocumentException, IOException;
}
