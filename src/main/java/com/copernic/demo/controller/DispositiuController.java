package com.copernic.demo.controller;

//-----------------------//
//@Author Ricard Sierra--//
//-------DAM2T-----------//
//-----------------------//


// DeviceController.java

import com.copernic.demo.dao.DispositiuDAO;
import com.copernic.demo.domain.Dispositiu;
import com.copernic.demo.domain.Ticket;
import com.copernic.demo.services.DispositiuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DispositiuController {

    @Autowired
    private DispositiuService dispositiuService;

    @Autowired
    DispositiuDAO dispositiuDAO;





    @GetMapping("/dispositius")
    public String listDevices(Model model) {
        List<Dispositiu> dispositius = dispositiuService.getAllDevices();
        model.addAttribute("dispositius", dispositius);
        return "dispositivos";
    }

    @GetMapping("/nuevoDispositivo")
    public String mostrarFormulario(Dispositiu dispositiu, Model model) {
        model.addAttribute("dispositiu", dispositiu);
        return "dispositivosForm";
}

    @PostMapping("/nuevoDispositivo")
    public String sumbitForm(Dispositiu dispositiu, Model model) {
        dispositiuService.saveDevice(dispositiu);
        model.addAttribute("dispositiu", dispositiu);
        return "redirect:/dispositius";
    }

    @GetMapping("/editarDispositivo/{id}")
    public String update(Dispositiu dispositiu,Model model) {
        dispositiu = dispositiuService.getDeviceById(dispositiu.getId());
        model.addAttribute("dispositiu", dispositiu);
        return "dispositivosForm";
    }


    @GetMapping("/borrarDispositivo/{id}")
    public String delete(@PathVariable Long id, Model model) {
        Dispositiu dispositiu = dispositiuService.getDeviceById(id);
        model.addAttribute("dispositiu", dispositiu);
        return "dispositiuDeleteConfirmation";
    }
    @GetMapping("/borrarDisOk/{id}")
    public String delete(Dispositiu dispositiu) {
        dispositiuService.deleteDevice(dispositiu.getId());
        return "redirect:/dispositius";
    }



}

