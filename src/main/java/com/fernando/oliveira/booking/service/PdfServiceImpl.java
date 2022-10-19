package com.fernando.oliveira.booking.service;

import com.itextpdf.text.*;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Slf4j
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

    public Element getSubtitle(String subtitle, int alignment) {
        Paragraph paragraph = new Paragraph(subtitle,
                new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(0, 0, 0)));
        paragraph.setAlignment(alignment);
        paragraph.add(EMPTY_LINE);
        paragraph.add(EMPTY_LINE);
        return paragraph;
    }

    public Element getDefaultParagraph(String text) {
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
        paragraph.add(text);
        return paragraph;
    }

    public Element getBoldParagraph(String text) {
        Paragraph paragraph = new Paragraph(text,
                new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(0, 0, 0)));
        paragraph.setAlignment(Element.ALIGN_LEFT);

        return paragraph;
    }

    public void getFooter(Document document) throws DocumentException, IOException {
        document.add(getDefaultParagraph("Guarujá, " + getFormatCurrentDate()));
        document.add(getEmptyLine());
        document.add(getSign());
        document.add(getDefaultParagraph("____________________________________________"));
        document.add(getDefaultParagraph("Locador: Fernando Augusto Machado de Oliveira"));

    }

    private String getFormatCurrentDate() {

        Date data = new Date();
        Locale local = new Locale("pt", "BR");
        DateFormat dateFormat = new SimpleDateFormat("dd '  de  ' MMMM '  de  ' yyyy", local);
        return dateFormat.format(data);
    }
    private Image getSign()  {

        try {
            BufferedImage buff = ImageIO.read(getClass().getClassLoader().getResourceAsStream("sign_small.jpg"));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(buff, "jpg", bos);
            return Image.getInstance(bos.toByteArray());

        } catch (BadElementException  | IOException ex) {
            log.error("BadElementException: Erro ao recuperar instancia do arquivo de assinatura", ex);
        }
        return null;
    }
}
