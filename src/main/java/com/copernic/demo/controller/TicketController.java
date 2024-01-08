package com.copernic.demo.controller;

import com.copernic.demo.dao.ConsultaDAO;
import com.copernic.demo.dao.RolDAO;
import com.copernic.demo.domain.*;
import com.copernic.demo.services.DispositiuService;
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
    DispositiuService dispositiuService;

    @Autowired
    RolDAO rolDAO;

    @Autowired
    ConsultaDAO ConsultaDAO;


    @GetMapping("/ticket")
    public String mostrarFormulario(@RequestParam(name = "consulta", required = false) Long consulta_id, Ticket ticket, Model model) {
        model.addAttribute("ticket", ticket);
        if (consulta_id != null) {
            Consulta consulta = ConsultaDAO.findById(consulta_id).orElse(null);
            model.addAttribute("consultes", consulta);
        } else {
           List<Consulta> consultes = ConsultaDAO.findAll();
           model.addAttribute("consultes", consultes);
        }
        return "ticketForm";
    }
    @GetMapping("/dispositivosPorConsulta")
    @ResponseBody
    public List<Dispositiu> getDispositiusPorConsulta(@RequestParam("consultaId") Long consultaId) {
        return dispositiuService.findByConsultaId(consultaId);
    }
    @PostMapping("/ticket")
    public String sumbitForm(Ticket ticket, Model model) {
        ticket.setFecha(LocalDateTime.now());
        ticket.setUsuari(usuariService.getUsuariByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        ticketService.saveTicket(ticket);
        if (ticket.getDispositiu_id() != -1) {
            Dispositiu dispositiu = dispositiuService.getDeviceById(ticket.getDispositiu_id());
            dispositiu.setEstat("incidencia");
            dispositiuService.saveDevice(dispositiu);
        }
        if (ticket.getEstado().equals("cerrado")) {
            if (ticket.getDispositiu_id() != -1) {
                Dispositiu dispositiu = dispositiuService.getDeviceById(ticket.getDispositiu_id());
                dispositiu.setEstat("actiu");
                dispositiuService.saveDevice(dispositiu);
            }
        }
        model.addAttribute("ticket", ticket);
        return "ticketResult";
    }

    @GetMapping("/tickets")
    public String listTickets(@RequestParam(name = "state", required = false) String state, Model model) {
        List<Ticket> tickets;
        switch (state) {
            case "activo":
                tickets = ticketService.getIncidenciesOpenedUrge();
                tickets.addAll(ticketService.getIncidenciesOpened());
                break;
            case "activo_urgencia":
                tickets = ticketService.getIncidenciesOpenedUrge();
                break;
            case "cerrado":
                tickets = ticketService.getIncidenciesClosed();
                break;
            case "all":
                tickets = ticketService.getAllTickets();
                break;
            default:
                tickets = ticketService.getAllTickets();
                break;
        }
        model.addAttribute("tickets", tickets);
        return "ticketList";
    }

    @GetMapping("/update/{id}")
    public String update(Ticket ticket,Model model) {
        ticket = ticketService.getTicketById(ticket.getId());
        Consulta consulta = ticket.getConsulta();
        model.addAttribute("consultes", consulta);
        ticket.setDispositiu_id(ticket.getDispositiu_id());
        model.addAttribute("ticket", ticket);
        return "ticketForm";
    }

    @GetMapping("/consulta/{id}")
    public String consultesView(@PathVariable Long id,Model model) {
        Consulta consulta = ConsultaDAO.findById(id).orElse(null);
        List<Ticket> ticketsClosed = ticketService.getTicketsClosedByConsulta(id);
        List<Ticket> ticketsOpen = ticketService.getTicketsOpenByConsulta(id);
        List<Dispositiu> dispositius = dispositiuService.findByConsultaId(id);
        model.addAttribute("consulta", consulta);
        model.addAttribute("tickets", ticketsOpen);
        model.addAttribute("ticketsCerrados", ticketsClosed);
        model.addAttribute("dispositius", dispositius);
        return "consultaView";
    }
    @GetMapping("/newconsulta")
    public String mostrarFormularioConsultes(Consulta consulta, Model model) {
        model.addAttribute("consulta", consulta);
        return "consultaForm";
    }

    @PostMapping("/newconsulta")
    public String newConsulta(Consulta consulta, Model model) {
        ConsultaDAO.save(consulta);
        model.addAttribute("consulta", consulta);
        return "redirect:/consultes";
    }


    @GetMapping("/consultes")
    public String consultesList(Model model) {
        List<Consulta> consultes = ConsultaDAO.findAll();
        model.addAttribute("consultes", consultes);
        return "consultesList";
    }

    @GetMapping("/updateconsulta/{id}")
    public String UpdateConsulta(@PathVariable Long id, Model model) {
        Consulta consultaa = ConsultaDAO.findById(id).orElse(null);
        model.addAttribute("consulta", consultaa);
        return "consultaForm";
    }

    @GetMapping("/deleteconsulta/{id}")
    public String DeleteConsulta(@PathVariable Long id) {
        ConsultaDAO.delete(ConsultaDAO.getReferenceById(id));
        return "redirect:/consultes";
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
        return "redirect:/tickets?state=all";
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
    public String ShowMapa(Model model) {
        List<Consulta> getConsultesIncidenciesUrge = ticketService.getConsultesIncidenciesUrge();
        List<Integer> consultesIncidenciesId = new ArrayList<>(); // Inicializa la lista
        for (Consulta consulta : getConsultesIncidenciesUrge) {
            consultesIncidenciesId.add(Math.toIntExact(consulta.getIdconsulta()));
        }
        model.addAttribute("consultesIncidencies", consultesIncidenciesId);

        List<Consulta> getConsultesIncidenciesNormals = ticketService.getConsultesIncidenciesNormals();
        List<Integer> consultesIncidenciesId2 = new ArrayList<>(); // Inicializa la lista
        for (Consulta consulta : getConsultesIncidenciesNormals) {
            consultesIncidenciesId2.add(Math.toIntExact(consulta.getIdconsulta()));
        }
        model.addAttribute("consultesIncidencies2", consultesIncidenciesId2);
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

}






