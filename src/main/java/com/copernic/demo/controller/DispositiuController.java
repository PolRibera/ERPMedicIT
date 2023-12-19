package com.copernic.demo.controller;

//-----------------------//
//@Author Ricard Sierra--//
//-------DAM2T-----------//
//-----------------------//


// DeviceController.java

import com.copernic.demo.dao.DispositiuDAO;
import com.copernic.demo.domain.Dispositiu;
import com.copernic.demo.services.DispositiuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/devices")
public class DispositiuController {



    @Autowired
    private   DispositiuService dispositiuService;

    @Autowired
    DispositiuDAO dispositiuDAO;

    @GetMapping
    public List<Dispositiu> getAllDevices() {
        return dispositiuService.getAllDevices();
    }

    @GetMapping("/dispositivos")
    public ResponseEntity<Dispositiu> getDeviceById(@PathVariable Long id) {
        return dispositiuService.getDeviceById(id)
                .map(device -> new ResponseEntity<>(device, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Dispositiu> createDevice(@RequestBody Dispositiu device) {
        Dispositiu savedDevice = dispositiuService.saveDevice(device);
        return new ResponseEntity<>(savedDevice, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dispositiu> updateDevice(@PathVariable Long id, @RequestBody Dispositiu device) {
        if (!dispositiuService.getDeviceById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        device.setIdDispositiu(id);
        Dispositiu updatedDevice = dispositiuService.saveDevice(device);
        return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        if (!dispositiuService.getDeviceById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dispositiuService.deleteDevice(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
