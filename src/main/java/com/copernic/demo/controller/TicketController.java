package com.copernic.demo.controller;

import com.copernic.demo.dao.ConsultaDAO;
import com.copernic.demo.dao.RolDAO;
import com.copernic.demo.domain.*;
import com.copernic.demo.services.TicketService;
import com.copernic.demo.services.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    UsuariService usuariService;

    @Autowired
    RolDAO rolDAO;

    @Autowired
    ConsultaDAO ConsultaDAO;


    @GetMapping("/ticket")
    public String mostrarFormulario(Ticket ticket, Model model) {
        model.addAttribute("ticket", ticket);
        List<Consulta> consultes = ConsultaDAO.findAll();
        model.addAttribute("consultes", consultes);
        return "ticketForm";
    }

    @PostMapping("/ticket")
    public String sumbitForm(Ticket ticket, Model model) {
        ticket.setFecha(LocalDateTime.now());
        ticket.setUsuari(usuariService.getUsuariByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
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

    @GetMapping("/consulta/{id}")
    public String consultesView(@PathVariable Long id,Model model) {
        Consulta consulta = ConsultaDAO.findById(id).orElse(null);
        model.addAttribute("consulta", consulta);
        return "consultaView";
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
            Rol rol = rolDAO.findByNom(usuariService.getUsuariByUsername(username).getRol().getNom());
            model.addAttribute("Rol",rol);
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
        usuari.setIntents(3);
        usuariService.saveUsuari(usuari);
        return "redirect:/login";

    }


    @GetMapping("/mapa")
    public String ShowMapa() {
        return "Mapa";
    }


    @GetMapping("/")
    public String ShowNoDirect( ) {
        return "redirect:/inici";
    }

    @PostMapping("/ticket/{id}/messages")
    public String addMessageToTicket(@PathVariable Long id, @RequestParam String mensaje, @RequestParam String username, Model model, @RequestParam String tipo) {
        Ticket ticket = ticketService.getTicketById(id);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usuariname = "";
        if (principal instanceof UserDetails) {
                usuariname = ((UserDetails) principal).getUsername();
        } else {
                usuariname = principal.toString();
        }
        model.addAttribute("username", usuariname);
        Mensaje message = new Mensaje();
        message.setMensaje(mensaje);
        LocalDateTime now = LocalDateTime.now();
        message.setFecha(now);
        message.setTicket(ticket);
        message.setTipo(tipo);
        Usuari usuari = usuariService.getUsuariByUsername(usuariname);
        message.setUsuari(usuari);// Asignar el ticket al mensaje

        ticketService.saveMensaje(message);
        return "redirect:/ticket/"+id+"/messages";
    }

    @GetMapping("/desp")
    public String mostrarPagina(@RequestParam("area") String area, Model model) {
        // Puedes hacer algo con el parámetro "area" si es necesario
        model.addAttribute("area", area);
        return "consultaView";
    }
}






