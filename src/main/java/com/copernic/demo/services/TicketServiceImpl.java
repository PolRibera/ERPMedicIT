package com.copernic.demo.services;

import com.copernic.demo.dao.MensajeDAO;
import com.copernic.demo.dao.TicketDAO;
import com.copernic.demo.domain.Mensaje;
import com.copernic.demo.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private MensajeDAO mensajeDAO;

    @Override
    public Ticket saveTicket(Ticket ticket) {
        return ticketDAO.save(ticket);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketDAO.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Ticket getTicketById(Long id) {
        return ticketDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteTicket(Long ticketId) {
        Ticket ticket = ticketDAO.findById(ticketId).orElse(null);
        if (ticket != null) {
            // Eliminar los mensajes asociados al ticket
            mensajeDAO.deleteByTicketId(ticketId);
            // Luego eliminar el ticket
            ticketDAO.delete(ticket);
        }
    }

    @Override
    public Mensaje saveMensaje(Mensaje message) {
        return mensajeDAO.save(message);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Mensaje> getMessages(Long ticketId) {
        return mensajeDAO.findByTicket_Id(ticketId);
    }


}