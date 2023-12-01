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

    // Getter and Setter for 'mensaje' field

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}