package com.copernic.demo.services;

import com.copernic.demo.domain.Ticket;
import com.copernic.demo.domain.Usuari;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsuariService extends UserDetailsService{

        public UserDetails loadUserByUsername(String username);

        List<Usuari> getAllUsuaris();

        Usuari saveUsuari(Usuari user);

        void deleteUsuer(String username);
}
