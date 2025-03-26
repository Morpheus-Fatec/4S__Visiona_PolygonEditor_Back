package com.morpheus.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morpheus.backend.entity.Scan;
import com.morpheus.backend.repository.ScanRepository;

@Service
public class ScanService {
    @Autowired
    public ScanRepository scanRepository;

    public String createScan(){
        Scan scan = new Scan();
        
        Long idScan = scan.getId();

        return "Scan "+ idScan + " criado com sucesso";
    }
}
