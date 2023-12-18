package com.copernic.demo.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "mensaje")
@Data
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;


    @ManyToOne
    @JoinColumn(name = "username_id")
    private Usuari usuari;

    // Getter and Setter for 'mensaje' field

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}