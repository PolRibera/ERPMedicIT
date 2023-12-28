package com.copernic.demo.dao;
import com.copernic.demo.domain.Consulta;
import com.copernic.demo.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketDAO extends JpaRepository<Ticket, Long> {
    List<Ticket> findByConsulta(Consulta consulta);
}
