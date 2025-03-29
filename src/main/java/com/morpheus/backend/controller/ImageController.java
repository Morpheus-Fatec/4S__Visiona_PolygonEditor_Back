package com.morpheus.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.ImageCreateDTO;
import com.morpheus.backend.entity.Image;
import com.morpheus.backend.service.ImageService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;


    @GetMapping
    public List<Image> getAllImagesUrl() throws Exception {
        return imageService.getAllImages();
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Image> saveImage(@ModelAttribute ImageCreateDTO image) {
        /*String profileImageUrl = userService.saveUser(userRequest);
        String fileName = profileImageUrl.substring(profileImageUrl.lastIndexOf("/") + 1);
        String newProfileImageUrl = "https://demeterteste.s3.us-east-2.amazonaws.com/testeA/demeterteste/" + fileName;
    
        return ResponseEntity.ok(newProfileImageUrl);*/
        List<Image> result = imageService.createdImageList(image);
        return null;
    }
}
