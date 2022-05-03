package com.fernando.oliveira.booking.service;

import com.itextpdf.text.Element;

public interface PdfService {

    Element getTitle(String title);

    Element getEmptyLine();

    Element getSubtitle(String subtitle);
}
