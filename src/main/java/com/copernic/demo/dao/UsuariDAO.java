package com.copernic.demo.dao;

import com.copernic.demo.domain.Ticket;
import com.copernic.demo.domain.Usuari;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariDAO extends JpaRepository<Usuari,Long>{

    /*Mètode que retornarà l'usuari que passem per paràmetre.
     *El nom d'aquest mètode ha de ser findByUsername, ja que és el que reconeix Spring Boot
     *com a mètode de seguretat per recuperar l'usuari.
     */
    Usuari findByUsername(String username);

}
