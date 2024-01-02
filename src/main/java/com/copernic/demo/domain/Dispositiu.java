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
public class Dispositiu {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_dispositiu;

    @NotEmpty
    private String nom;

    @NotEmpty
    private String Tipus;

    @NotEmpty
    private String Descripcio;

    @NotEmpty
    private String Estat;


}