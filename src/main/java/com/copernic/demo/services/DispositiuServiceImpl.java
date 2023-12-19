package com.copernic.demo.services;

//-----------------------//
//@Author Ricard Sierra--//
//-------DAM2T-----------//
//-----------------------//


import com.copernic.demo.dao.DispositiuDAO;
import com.copernic.demo.domain.Dispositiu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DispositiuServiceImpl implements DispositiuService {

    @Autowired
    private DispositiuDAO dispositiuDAO;

    @Override
    public List<Dispositiu> getAllDevices() {
        return dispositiuDAO.findAll();
    }

    @Override
    public Optional<Dispositiu> getDeviceById(Long id) {
        return dispositiuDAO.findById(id);
    }

    @Override
    public Dispositiu saveDevice(Dispositiu device) {
        return dispositiuDAO.save(device);
    }

    @Override
    public void deleteDevice(Long id) {
        dispositiuDAO.deleteById(id);
    }
}
