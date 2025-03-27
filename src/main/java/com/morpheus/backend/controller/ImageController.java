package com.morpheus.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.entity.Image;
import com.morpheus.backend.service.ImageService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;


    @GetMapping
    public List<Image> getAllImagesUrl() throws Exception {
        return imageService.getAllImages();
    }
}
