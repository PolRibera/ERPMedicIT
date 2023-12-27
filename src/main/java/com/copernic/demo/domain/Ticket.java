package com.copernic.demo.domain;



import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ticket")
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Usuari usuari;

    @ManyToOne
    @JoinColumn(name = "consulta_id")
    private Consulta consulta;

    @OneToMany(mappedBy = "ticket")
    private List<Mensaje> mensajes;


}
