package com.copernic.demo.dao;
import com.copernic.demo.domain.Consulta;
import com.copernic.demo.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConsultaDAO extends JpaRepository<Consulta, Long> {
    @Query("SELECT c FROM Consulta c, Ticket t WHERE t.consulta.idconsulta = c.idconsulta and t.estado = 'activo'")
    List<Consulta> findConsultesWithIncidenciesNormals();

    @Query("SELECT c FROM Consulta c, Ticket t WHERE t.consulta.idconsulta = c.idconsulta and t.estado = 'activo_urgencia'")
    List<Consulta> findConsultesWithIncidenciesUrge();

}
