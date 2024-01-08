package com.copernic.demo.dao;

import com.copernic.demo.domain.Consulta;
import com.copernic.demo.domain.Dispositiu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DispositiuDAO extends JpaRepository<Dispositiu, Long>{

    List<Dispositiu> findByConsulta(Consulta consulta);
}
