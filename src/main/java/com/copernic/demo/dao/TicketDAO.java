package com.copernic.demo.dao;
import com.copernic.demo.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketDAO extends JpaRepository<Ticket, Long> {

}
