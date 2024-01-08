package com.copernic.demo.dao;
import com.copernic.demo.domain.Consulta;
import com.copernic.demo.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketDAO extends JpaRepository<Ticket, Long> {
    List<Ticket> findByConsulta(Consulta consulta);

    @Query("SELECT c FROM Ticket c WHERE c.estado = 'activo'")
    List<Ticket> findbyEstadoOpen();

    @Query("SELECT c FROM Ticket c WHERE c.estado = 'activo_urgencia'")
    List<Ticket> findbyEstadoOpenUrge();

    @Query("SELECT c FROM Ticket c WHERE c.estado = 'cerrado'")
    List<Ticket> findbyEstadoClosed();
}
