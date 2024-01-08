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
    private Long id;

    @NotEmpty
    private String nom;

    @NotEmpty
    private String Tipus;


    private String Descripcio;


    private String Estat;

    @ManyToOne
    @JoinColumn(name = "consulta_id")
    private Consulta consulta;


}