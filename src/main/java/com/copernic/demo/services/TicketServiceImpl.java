package com.copernic.demo.services;

import com.copernic.demo.dao.ConsultaDAO;
import com.copernic.demo.dao.MensajeDAO;
import com.copernic.demo.dao.TicketDAO;
import com.copernic.demo.domain.Consulta;
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

    @Autowired
    private ConsultaDAO consultaDAO;

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

    @Override
    @Transactional(readOnly = true)
    public List<Consulta> getConsultesIncidenciesNormals() {
        return consultaDAO.findConsultesWithIncidenciesNormals();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Consulta> getConsultesIncidenciesUrge() {
        return consultaDAO.findConsultesWithIncidenciesUrge();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> getTicketsClosedByConsulta(Long idconsulta) {
        Consulta c = consultaDAO.getReferenceById(idconsulta);
        List<Ticket> tickets = ticketDAO.findByConsulta(c);
        tickets.removeIf(t -> t.getEstado().equals("activo_urgencia") || t.getEstado().equals("activo"));
        return tickets;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> getTicketsOpenByConsulta(Long idconsulta) {
        Consulta c = consultaDAO.getReferenceById(idconsulta);
        List<Ticket> tickets = ticketDAO.findByConsulta(c);
        tickets.removeIf(t -> t.getEstado().equals("cerrado"));
        return tickets;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Ticket> getIncidenciesOpened() {
        return ticketDAO.findbyEstadoOpen();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> getIncidenciesOpenedUrge() {
        return ticketDAO.findbyEstadoOpenUrge();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> getIncidenciesClosed() {
        return ticketDAO.findbyEstadoClosed();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> getTicketsByConsulta(Long idconsulta) {
        Consulta c = consultaDAO.getReferenceById(idconsulta);
        return ticketDAO.findByConsulta(c);
    }


}