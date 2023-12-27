package com.copernic.demo.dao;
import com.copernic.demo.domain.Consulta;
import com.copernic.demo.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaDAO extends JpaRepository<Consulta, Long> {


}
