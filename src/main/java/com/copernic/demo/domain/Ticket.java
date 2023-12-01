package com.copernic.demo.domain;



import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "ticket")
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @OneToMany(mappedBy = "ticket")
    private List<Mensaje> mensajes;


}
