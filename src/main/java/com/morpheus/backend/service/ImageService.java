package com.morpheus.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morpheus.backend.DTO.ImageDTO;
import com.morpheus.backend.entity.Field;
import com.morpheus.backend.entity.Image;
import com.morpheus.backend.repository.FieldRepository;
import com.morpheus.backend.repository.ImageRepository;
import com.morpheus.exceptions.DefaultException;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private FieldRepository fieldRepository;

    public String createImage(ImageDTO imageDTO) {
        try {
            if (imageDTO.getImageUrl().isEmpty()) {
                throw new Exception();
            }

            Image image = new Image();
            image.setImageUrl(imageDTO.getImageUrl());
            imageRepository.save(image);

            return "URL: " + image.getImageUrl() + " inserida com sucesso.";
        } catch (Exception e) {
            throw new DefaultException("Não foi possível criar a imagem.");
        }
    }

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

    public Image getImageById(Long id) {
        try {
            Image image = imageRepository.findById(id);

            if(image == null) {
                throw new Exception();
            }

            return image;
        } catch (Exception e) {
            throw new DefaultException("Não foi possível encontrar a imagem.");
        }
    }

    public String updateImageUrl(Long imageId, ImageDTO imageUrl){
        try {
            Image image = imageRepository.findById(imageId);

            if(image == null) {
                throw new Exception();
            }

            image.setImageUrl(imageUrl.getImageUrl());
            imageRepository.save(image);

            return "URL: " + image.getImageUrl() + " atualizada com sucesso.";
        } catch (Exception e) {
            throw new DefaultException("Não foi possível atualizar a imagem.");
        }
    }

    public String updateField(Long imageId, ImageDTO imageDTO){
        try {
            Image image = imageRepository.findById(imageId);
            Field field = fieldRepository.getFieldById(imageDTO.getFieldId());

            if(image == null || field == null) {
                throw new Exception();
            }

            image.setFieldId(field);
            imageRepository.save(image);

            return "URL: " + image.getImageUrl() + " atualizada com sucesso.";
        } catch (Exception e) {
            throw new DefaultException("Não foi possível atualizar a imagem.");
        }
    }

    public String deleteImageUrl(Long imageId){
        String imageUrlDeleted = "";
        try {
            Image image = imageRepository.findById(imageId);

            if(image == null){
                throw new Exception();
            }

            imageUrlDeleted = image.getImageUrl();
            imageRepository.delete(image);

            return "Url: "+imageUrlDeleted+" deletada com sucesso";
        } catch (Exception e) {
            throw new DefaultException("Não foi possível deletar a URL");
        }
    }
}
