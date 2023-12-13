package com.copernic.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Pol Ribera
 */

@Data
@Entity
@Table(name="rol")
public class Rol implements Serializable {

    private static final long serialVersionUID=1L;

    @Id //L'atribut idRol és la clau primària de la BBDD
    @GeneratedValue(strategy=GenerationType.IDENTITY) //Generació autonumèrica de l'id
    private long idRol;

    @NotEmpty//Validació perquè l'usuari afegeixi contingut al camp nom
    private String nom;

    @OneToMany(mappedBy = "rol") // Assuming 'rol' is the field in Usuari entity representing the relationship
    private List<Usuari> usuaris;


}
