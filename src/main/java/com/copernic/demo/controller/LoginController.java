package com.copernic.demo.controller;

import com.copernic.demo.dao.RolDAO;
import com.copernic.demo.domain.Usuari;
import com.copernic.demo.services.UsuariService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class LoginController {

    @Autowired
    private UsuariService usuariService;

    @Autowired
    RolDAO rolDAO;




    @GetMapping("/bloquejats")
    public String llistaUsuarisBloquejats(Model model){
        List<Usuari> listaUsers = usuariService.getBlockedUsers();
        model.addAttribute("users",listaUsers);
        return "blockedUserList";
    }

    @GetMapping("/desbloqueja/{username}")
    public String desbloquejarUsuari(@PathVariable String username, Usuari user){
        usuariService.desbloquejarUsuari(username, user);
        return "redirect:/inici";

    }
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<Usuari> users = usuariService.getAllUsuaris();
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/updateuser/{username}")
    public String updateUser(Usuari usuari,Model model) {
        usuari = usuariService.getUsuariByUsername(usuari.getUsername());
        model.addAttribute("usuari", usuari);
        return "updateuser";
    }

    @GetMapping("/deleteuser/{username}")
    public String deleteUser(@PathVariable String username, Model model) {
        Usuari usuari = usuariService.getUsuariByUsername(username);
        model.addAttribute("usuari", usuari);
        return "userDeleteConfirmation";
    }
    @GetMapping("/deleteuserOK/{username}")
    public String delete(Usuari user) {
        usuariService.deleteUsuari(user.getUsername());
        return "redirect:/users";
    }

}