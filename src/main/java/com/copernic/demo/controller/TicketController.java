package com.copernic.demo.controller;

import com.copernic.demo.dao.RolDAO;
import com.copernic.demo.domain.Mensaje;
import com.copernic.demo.domain.Rol;
import com.copernic.demo.domain.Ticket;
import com.copernic.demo.domain.Usuari;
import com.copernic.demo.services.TicketService;
import com.copernic.demo.services.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    UsuariService usuariService;

    @Autowired
    RolDAO rolDAO;


    @GetMapping("/ticket")
    public String mostrarFormulario(Ticket ticket, Model model) {
        model.addAttribute("ticket", ticket);
        return "ticketForm";
    }

    @PostMapping("/ticket")
    public String sumbitForm(Ticket ticket, Model model) {
        ticketService.saveTicket(ticket);
        model.addAttribute("ticket", ticket);
        return "ticketResult";
    }

    @GetMapping("/tickets")
    public String listtickets(Model model) {
        List<Ticket> tickets = ticketService.getAllTickets();
        model.addAttribute("tickets", tickets);
        return "ticketList";
    }
    @GetMapping("/update/{id}")
    public String update(Ticket ticket,Model model) {
        ticket = ticketService.getTicketById(ticket.getId());
        model.addAttribute("ticket", ticket);
        return "ticketForm";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        Ticket ticket = ticketService.getTicketById(id);
        model.addAttribute("ticket", ticket);
        return "ticketDeleteConfirmation";
    }
    @GetMapping("/deleteOK/{id}")
    public String delete(Ticket ticket) {
        ticketService.deleteTicket(ticket.getId());
        return "redirect:/tickets";
    }

    @GetMapping("/ticket/{id}/messages")
    public String showTicketMessages(@PathVariable Long id, Model model) {
        Ticket ticket = ticketService.getTicketById(id);
        model.addAttribute("ticket", ticket);
        List<Mensaje> messages = ticketService.getMessages(id);
        model.addAttribute("messages", messages);
        return "ticketMessages";
    }
    @GetMapping("/inici")
    public String ShowDirectLogged(Model model ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal == "anonymousUser") {
            return "Inici";
        } else {
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
            model.addAttribute("username", username);
            return "IniciLogged";
        }
    }

    @GetMapping("/login")
    public String ShowLogin( ) {
        return "login";
    }

    @GetMapping("/register")
    public String ShowRegister(Usuari usuari, Model model) {
        List<Rol> rols = rolDAO.findAll();
        model.addAttribute("rols", rols);
        model.addAttribute("usuari", usuari);
        return "register";
    }

    @PostMapping("/register")
    public String RegisterPost(Usuari usuari, Model model) {
        model.addAttribute("usuari", usuari);
        usuariService.saveUsuari(usuari);
        return "redirect:/login";
    }


    @GetMapping("/mapa")
    public String ShowMapa( ) {
        return "Mapa";
    }


    @GetMapping("/")
    public String ShowNoDirect( ) {
        return "redirect:/inici";
    }

    @PostMapping("/ticket/{id}/messages")
    public String addMessageToTicket(@PathVariable Long id, @RequestParam String mensaje) {
        Ticket ticket = ticketService.getTicketById(id);

        Mensaje message = new Mensaje();
        message.setMensaje(mensaje);
        message.setTicket(ticket); // Asignar el ticket al mensaje

        ticketService.saveMensaje(message);
        return "redirect:/ticket/"+id+"/messages";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<Usuari> users = usuariService.getAllUsuaris();
        model.addAttribute("users", users);
        return "userList";
    }
}



