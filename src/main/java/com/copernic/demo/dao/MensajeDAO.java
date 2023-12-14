package com.copernic.demo.dao;
import com.copernic.demo.domain.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeDAO extends JpaRepository<Mensaje, Long> {
    List<Mensaje> findByTicket_Id(long clienteId);

    void deleteByTicketId(Long ticketId);
}
