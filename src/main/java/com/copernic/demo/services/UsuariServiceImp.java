package com.copernic.demo.services;

import com.copernic.demo.dao.RolDAO;
import com.copernic.demo.dao.UsuariDAO;
import com.copernic.demo.domain.Rol;
import com.copernic.demo.domain.Usuari;

import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.copernic.demo.utils.EncriptadorContrasenya.encriptarContrasenya;


/**
 *
 * @author Albert Grau
 */

/*Anotació que permet al sistema que reconegui aquesta classe com una classe de servei
 *i en concret una classe de servei que utilitzara Spring Security, per això passem per
 *paràmetre el nom predeterminat "userDetailsService".
*/
@Service("userDetailsService")
@Slf4j
public class UsuariServiceImp implements UsuariService, UserDetailsService {

    @Autowired
    private UsuariDAO usuariDAO;

    @Autowired
    private RolDAO rolDAO;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuari usuari = usuariDAO.findByUsername(username);

        if (usuari == null) {
            throw new UsernameNotFoundException(username);
        }

        // Buscar el rol por ID almacenada en el usuario
        Optional<Rol> optionalRol = rolDAO.findById((usuari.getRol()).getIdRol());

        Rol rol = optionalRol.orElseThrow(() -> new IllegalStateException("Rol not found for user: " + username));

        if (rol == null) {
            throw new IllegalStateException("Rol not found for user: " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(rol.getNom()));

        return new User(usuari.getUsername(), usuari.getPassword(), authorities);
    }

    @Override
    public Usuari saveUsuari(Usuari user) {
        String contrasenyaEncriptada = encriptarContrasenya(user.getPassword());
        user.setPassword(contrasenyaEncriptada);
        return usuariDAO.save(user);
    }

    @Override
    public Usuari getUsuariByUsername(String username) {
        Usuari usuari = usuariDAO.findByUsername(username);
        return usuari;
    }

    @Override
    public List<Usuari> getBlockedUsers() {
        List<Usuari> usuaris = usuariDAO.findAll();
        List<Usuari> usuarisBloquejats = new ArrayList<>();
        for (Usuari usuari : usuaris) {
            if (usuari.getIntents() <= 0) {
                usuarisBloquejats.add(usuari);
            }
        }

        return usuarisBloquejats;
    }

    @Override
    public void desbloquejarUsuari(String username, Usuari user) {
        Usuari usuari = usuariDAO.findByUsername(username);
        usuari.setIntents(3);
        usuariDAO.save(usuari);
    }


    @Override
    public void deleteUsuari(String username) {
        Usuari usuari = usuariDAO.findByUsername(username);
        if (usuari != null) {
            usuariDAO.delete(usuari);
        }
    }

    @Override
    public List<Usuari> getAllUsuaris() {
        return usuariDAO.findAll();
    }
}
