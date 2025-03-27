package com.morpheus.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morpheus.backend.DTO.ScanTestDTO;
import com.morpheus.backend.entity.Scan;
import com.morpheus.backend.repository.ScanRepository;

@Service
public class ScanService {
    @Autowired
    private ScanRepository scanRepository;

    public Scan createScan(ScanTestDTO scanDTO) {
        Scan scan = new Scan();
        
        scanRepository.save(scan);
        
        return scan;
    }
}
