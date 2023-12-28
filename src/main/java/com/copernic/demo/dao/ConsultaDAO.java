package com.copernic.demo.dao;
import com.copernic.demo.domain.Consulta;
import com.copernic.demo.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConsultaDAO extends JpaRepository<Consulta, Long> {
    @Query("SELECT c FROM Consulta c WHERE c.tickets > 0")
    List<Consulta> findConsultesWithIncidencies();

}
