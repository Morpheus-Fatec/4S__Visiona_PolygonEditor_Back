package com.morpheus.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.service.ScanService;

@RestController
@RequestMapping("/scan")
public class ScanController {
    private ScanService scanService;

    @PostMapping
    public String createScan(){
        return scanService.createScan();
    }
}
