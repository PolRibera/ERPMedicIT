package com.copernic.demo.dao;

import com.copernic.demo.domain.Rol;
import com.copernic.demo.domain.Usuari;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolDAO extends JpaRepository<Rol, Long> {
    Rol findByNom(String nom);
}
