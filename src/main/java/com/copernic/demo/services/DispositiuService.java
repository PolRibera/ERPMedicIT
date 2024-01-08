package com.copernic.demo.services;

import com.copernic.demo.domain.Dispositiu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DispositiuService {
    List<Dispositiu> getAllDevices();

    List<Dispositiu> findByConsultaId(Long consulta_Id);
    Dispositiu getDeviceById(Long id);
    Dispositiu saveDevice(Dispositiu device);
    void deleteDevice(Long id);
}
