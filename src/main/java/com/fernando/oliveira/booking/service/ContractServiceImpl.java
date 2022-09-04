package com.fernando.oliveira.booking.service;

import com.fernando.oliveira.booking.builder.PdfRequestDtoBuilder;
import com.fernando.oliveira.booking.domain.dto.PdfRequestDto;
import com.fernando.oliveira.booking.domain.entity.Booking;
import com.fernando.oliveira.booking.domain.enums.BookingStatusEnum;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ContractServiceImpl extends PdfServiceImpl implements ContractService {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PdfRequestDtoBuilder pdfRequestDtoBuilder;


    private static final String EMPTY_LINE = "\n";

    @Override
    public ByteArrayInputStream createContract(Booking booking) {

        PdfRequestDto requestDto = pdfRequestDtoBuilder.getRequestContractDto(booking);

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            if(booking.getBookingStatus().equals(BookingStatusEnum.RESERVED)) {
                document.add(getTitle("CONTRATO DE LOCAÇÃO POR TEMPORADA"));
            }else{
                document.add(getTitle("PRÉ-CONTRATO DE LOCAÇÃO POR TEMPORADA"));
            }

            getIdentificationParts(booking,requestDto, document);

            getApartmentDetails(document);

            getBuildingDetails(document);

            getRent(requestDto, document);

            getPaymentDetails(requestDto, document, booking);

            getCancelDetails(document);

            getFooter(document);

            document.close();

        } catch (IOException io) {
            System.err.println(io.getCause());

        } catch (DocumentException de) {
            System.err.println(de.getCause());
        }
        return new ByteArrayInputStream(out.toByteArray());
    }


    private void getIdentificationParts(Booking booking, PdfRequestDto requestContract, Document document) throws DocumentException {
        document.add(getSubtitle("I. IDENTIFICAÇÃO DAS PARTES", Element.ALIGN_LEFT));

        if(BookingStatusEnum.RESERVED.equals(booking.getBookingStatus())) {
            document.add(getDefaultParagraph("As pessoas abaixo identificadas firmam o presente contrato de locação por temporada, nos termos da legislação brasileira, em especial, de acordo com a Lei de Locação (Lei nº 8.245/91): "));
            document.add(getDefaultParagraph("Locador: Fernando Augusto Machado de Oliveira, CPF: 296.830.188-82"));
            document.add(getDefaultParagraph("Endereço eletrônico: f19@uol.com.br"));
        }
        document.add(getSubtitle("Responsável locação: ", Element.ALIGN_LEFT));
        document.add(getDefaultParagraph("Nome: " + requestContract.getTravelerName()));
        document.add(getDefaultParagraph("Email: " + requestContract.getTravelerEmail()));
        document.add(getDefaultParagraph("Telefone: " + requestContract.getTravelerPhone()));
        if(!requestContract.getTravelerDocument().isBlank()) {
            document.add(getDefaultParagraph("CPF: " + requestContract.getTravelerDocument()));
        }
        document.add(getEmptyLine());
    }

    private void getApartmentDetails(Document document) throws DocumentException {
        document.add(getSubtitle("II. DO IMÓVEL LOCADO", Element.ALIGN_LEFT));
        document.add(getDefaultParagraph("O imóvel locado está situado na rua Rio de Janeiro, 50 – apto 617/618 – Centro – Guarujá – SP. Trata-se de imóvel com as seguintes características:"));
        document.add(getDefaultParagraph("Possui ambiente com 2 quartos, sendo 1 com suíte, sala, cozinha, banheiro. O apartamento possui 1 cama de casal, 2 bicamas, 2 colchões de solteiro extras, mesa com 6 cadeiras, banheiro social com box."));
        document.add(getDefaultParagraph("Ainda possui Smart TV 49' na sala e duas 32' nos quartos, modem Wi-fi, lavadora de roupas, 3 ventiladores, armário para acomodação de bagagens, cômoda, panela de arroz, micro-ondas, cooktop de indução de quatro bocas"));
        document.add(getDefaultParagraph("Geladeira, liquidificador, sanduicheira grill, filtro de água e utensílios de cozinha, garrafa térmica"));
        document.add(getDefaultParagraph("Jogo de completo com 5 panelas Tognana de fundo triplo com tampas de vidro (2 frigideiras, 2 caçarolas, 1 panela), 1 fervedor Tramontina e 1 panela de pressão Brinox" ));
        document.add(getDefaultParagraph("O edifício possui portaria 24 horas, monitoramento por câmeras, WI-FI no hall de entrada, 3 elevadores, serviço de praia com cadeiras e 2 guarda-sóis (a solicitar na recepção)"));
        document.add(getEmptyLine());
        document.add(getSubtitle("Orientações para utilização do cooktop:", Element.ALIGN_LEFT));
        document.add(getItemList("Mantenha pressionado o botão de controle Lig./Desl. por 3 segundos até ouvir um \"bip\" para ligar o fogão."));
        document.add(getItemList("Somente utilize as panelas adequadas que estão disponíveis na área de aquecimento desejada"));
        document.add(getItemList("Verifique se o fundo da panela e a superfície da zona de cozinhar está limpa e seca"));
        document.add(getItemList("Selecione a area de aquecimento e ajuste a potencia"));
        document.add(getItemList("Deve-se utilizar os protetores de silicone abaixo das panelas"));
        document.add(getItemList("Não utilizar utensílios que venham a riscar as panelas, somente utilizar os utensílios de silicone disponíveis"));
        document.add(getItemList("Para desligar a area de aquecimento, selecione a área e dimiua a potencia"));
        document.add(getItemList("Ao final da utilização pressione o botão Lig./Desl, ou retire as panelas da area de aquecimento"));
        document.add(getEmptyLine());

        document.add(getSubtitle("Observações:", Element.ALIGN_LEFT));
        document.add(getItemList("É necessário levar roupa de cama, mesa e banho"));
        document.add(getItemList("É recomendável levar travesseiros"));
        document.add(getItemList("NÃO possui garagem, porém há vagas nas ruas próximas e estacionamentos"));
        document.add(getItemList("Para uso de guarda-sol e cadeiras de praia na faixa de areia, é preciso solicitá-los na recepção, informando o número do apartamento."));
        document.add(getEmptyLine());
    }

    private void getBuildingDetails(Document document) throws DocumentException {
        document.add(getSubtitle("III. DO REGULAMENTO INTERNO DO EDIFICIO", Element.ALIGN_LEFT));
        document.add(getItemList("O imóvel pode acomodar no máximo 8 pessoas. Sendo que crianças até 5 anos não contam."));
        document.add(getItemList("Banhistas devem entrar e sair pela porta lateral e usar o elevador reservado para banhistas, sem areia no corpo"));
        document.add(getItemList("Estacionamento é reservado para embarque e desembarque de bagagem e é limitado a 15 minutos"));
        document.add(getItemList("Animais devem ser levados no colo e ou com coleira. Não será tolerada sujeira produzida por animais em áreas comuns"));
        document.add(getItemList("Latidos, gritos, e abusos com aparelhos sonoros, que incomodem os demais condôminos, não serão tolerados em horário nenhum"));
        document.add(getItemList("É vetado pendurar roupas ou quaisquer outros objetos nas janelas"));
        document.add(getEmptyLine());
    }

    private void getRent(PdfRequestDto requestContract, Document document) throws DocumentException {
        document.add(getSubtitle("IV. DO PRAZO DE LOCAÇÃO", Element.ALIGN_LEFT));
        document.add(getDefaultParagraph("A locação tem:"));
        document.add(getDefaultParagraph(requestContract.getDescriptionCheckIn()));
        document.add(getDefaultParagraph(requestContract.getDescriptionCheckOut()));
        document.add(getDefaultParagraph("A permanência do locatário no imóvel pode ensejar multas e indenização, nos termos da lei."));
        document.add(getEmptyLine());
    }

    private void getPaymentDetails(PdfRequestDto requestContract, Document document, Booking booking) throws DocumentException {
        document.add(getSubtitle("V. DO PREÇO E DO PAGAMENTO", Element.ALIGN_LEFT));
        document.add(getRentDetails(requestContract, booking));
        document.add(getEmptyLine());
    }

    private void getCancelDetails(Document document) throws DocumentException {

        document.add(getSubtitle("VI. DO CANCELAMENTO DA LOCAÇÃO", Element.ALIGN_LEFT));
        document.add(getDefaultParagraph("O locatário pode cancelar a locação mediante comunicado por escrito através do e-mail do locador ao endereço contido na descrição das partes. O cancelamento implica nas seguintes penalidades que variam de acordo com a antecedência do aviso de cancelamento:"));
        document.add(getDefaultParagraph("Abaixo segue a tabela referente a devolução do valor depositado em caso de desistência por parte do locatário"));
        document.add(getCancelTable());
        document.add(getEmptyLine());
        document.add(getDefaultParagraph("Em caso de imprevistos, existe a possibilidade de trocar a data de locação, mediante acordo com o locatário e agenda disponível."));
        document.add(getEmptyLine());
    }




    private Element getItemList(String text) {
        Font zapfdingbats = new Font();
        Chunk bullet = new Chunk("\u2022", zapfdingbats);
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(bullet + " " + text);
        return paragraph;
    }



    private Element getCancelTable() throws DocumentException {

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(60);
        table.setWidths(new int[]{3, 3});

        table.addCell(getTitlePdfCell("ANTECEDÊNCIA"));
        table.addCell(getTitlePdfCell("DEVOLUÇÃO"));

        table.addCell(getPdfCell("Mais de 7 dias da locação"));
        table.addCell(getPdfCell("100 % do valor depositado"));
        table.addCell(getPdfCell("Entre 7 e 2 dias da locação"));
        table.addCell(getPdfCell("50 % do valor depositado"));
        table.addCell(getPdfCell("Menos de 2 dias da locação"));
        table.addCell(getPdfCell("0 % do valor depositado"));

        return table;


    }

    private PdfPCell getTitlePdfCell(String title) {
        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        PdfPCell hcell = new PdfPCell(new Phrase(title, headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return hcell;
    }

    private PdfPCell getPdfCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private Element getRentDetails(PdfRequestDto requestContract, Booking booking) {
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
        paragraph.add(requestContract.getDescriptionPayment());
        paragraph.add(EMPTY_LINE);
        paragraph.add(requestContract.getDescriptionPending());
        paragraph.add(EMPTY_LINE);
        paragraph.add(requestContract.getSummaryBooking());
        paragraph.add(EMPTY_LINE);

        if(BookingStatusEnum.PRE_RESERVED.equals(booking.getBookingStatus())){
            paragraph.add(EMPTY_LINE);
            paragraph.add(getSubtitle("Importante: ", Element.ALIGN_LEFT));
            paragraph.add("A reserva somente será efetivada quando o pagamento do valor total ou do sinal for efetuado.");
            paragraph.add(EMPTY_LINE);
        }
        return paragraph;
    }

}
