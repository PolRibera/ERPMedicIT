package com.copernic.demo.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

import lombok.Data;

//-----------------------//
//@Author Ricard Sierra--//
//-------DAM2T-----------//
//-----------------------//


@Data
@Entity
@Table(name = "dispositiu")
public class Dispositiu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDispositiu;

    @NotEmpty
    private String Nom;

    @NotEmpty
    private String Tipus;

    @NotEmpty
    private String Descripcio;

    @NotEmpty
    private String Estat;


}