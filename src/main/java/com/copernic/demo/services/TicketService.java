/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.copernic.demo.services;
import com.copernic.demo.domain.Consulta;
import com.copernic.demo.domain.Ticket;
import com.copernic.demo.domain.Mensaje;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author polri
 */
@Service
public interface TicketService {
    Ticket saveTicket(Ticket ticket);
    List<Ticket> getAllTickets();


    Ticket getTicketById(Long id);

    void deleteTicket(Long id);
    Mensaje saveMensaje(Mensaje mensaje);
    List<Mensaje> getMessages(Long ticketId);

    List<Consulta> getConsultesIncidencies();
}
