package com.copernic.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

import lombok.Data;

/**
 *
 * @author Pol Ribera
 */

@Data
@Entity
@Table(name = "usuaris")
public class Usuari implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUsuari;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;


    // Otros campos...
    @ManyToOne
    @JoinColumn(name = "rol_id") // Nombre de la columna que almacena la ID del rol
    private Rol rol;

    @Column(name = "intents")
    private int intents;



    // Campo para almacenar la ID del rol

    // Otros campos y métodos...
}