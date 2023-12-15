package com.copernic.demo.utils;


import com.copernic.demo.domain.Mensaje;
import com.copernic.demo.domain.Ticket;
import com.copernic.demo.controller.TicketController;
import com.copernic.demo.domain.Ticket;
import com.copernic.demo.services.TicketService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.awt.*;
import java.util.List;
import java.util.Map;

@Component("ticketList")
public class TicketsToPdf extends AbstractPdfView {


    private TicketService ticketService;

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        @SuppressWarnings("unchecked")
        List<Ticket> tickets = (List<Ticket>) model.get("tickets");

        document.setPageSize(PageSize.LETTER.rotate());
        document.setMargins(-20, -20, 40, 20);
        document.open();

        PdfPTable tabletitle = new PdfPTable(1);
        PdfPCell cell = null;

        Font fuenteTitulo =  FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font fuenteColumnas =  FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font fuenteCelda =  FontFactory.getFont(FontFactory.COURIER, 10);

        cell = new PdfPCell(new  Phrase("Listado de Tickets ", fuenteTitulo));
        cell.setBorder(0);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingBottom(15f);

        tabletitle.addCell(cell);
        tabletitle.setSpacingAfter(20);

        PdfPTable tableTickets = new PdfPTable(2);
        tableTickets.setWidths(new float[] {0.8f, 2.5f});

        cell = new PdfPCell(new Phrase("ID", fuenteColumnas));
        cell.setBackgroundColor(Color.lightGray);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(10);
        tableTickets.addCell(cell);

        cell = new PdfPCell(new Phrase("Nom", fuenteColumnas));
        cell.setBackgroundColor(Color.lightGray);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(10);
        tableTickets.addCell(cell);

        /*cell = new PdfPCell(new Phrase("Numero de missatges enviats", fuenteColumnas));
        cell.setBackgroundColor(Color.lightGray);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(10);
        tableTickets.addCell(cell);*/

        tickets.forEach(ticket -> {
            tableTickets.addCell(ticket.getId().toString());
            tableTickets.addCell(ticket.getNom());
            /*assert ticket.getMensajes() != null;
            if (!ticket.getMensajes().isEmpty()) {
                for (Mensaje message : ticket.getMensajes()) {
                    tableTickets.addCell(message.getMensaje());
                }
            }*/
        });
        document.add(tabletitle);
        document.add(tableTickets);

    }
}
