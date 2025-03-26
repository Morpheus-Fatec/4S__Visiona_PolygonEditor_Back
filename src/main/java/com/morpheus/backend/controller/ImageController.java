package com.morpheus.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.ImageDTO;
import com.morpheus.backend.entity.Image;
import com.morpheus.backend.service.ImageService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping
    public String createImage(@RequestBody ImageDTO imageUrl) throws Exception {
        return imageService.createImage(imageUrl);
    }

    @GetMapping
    public List<Image> getAllImagesUrl() throws Exception {
        return imageService.getAllImages();
    }

    @GetMapping("/{id}")
    public Image getImageUrlById(@PathVariable Long id) throws Exception {
        return imageService.getImageById(id);
    }
    
    @DeleteMapping("/{id}")
    public String deleteImage(@PathVariable Long id) throws Exception {
        return imageService.deleteImageUrl(id);
    }
}
