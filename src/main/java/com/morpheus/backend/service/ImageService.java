package com.morpheus.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morpheus.backend.entity.Image;
import com.morpheus.backend.repository.ImageRepository;
import com.morpheus.exceptions.DefaultException;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;


    public List<Image> getAllImages() {
        try {
            List<Image> images = imageRepository.findAll();

            if(images.size() == 0) {
                throw new Exception();
            }

            return imageRepository.findAll();
        } catch (Exception e) {
            throw new DefaultException("Não foi possível encontrar imagens.");
        }
    }
}
