package com.morpheus.backend.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.morpheus.backend.DTO.ImageCreateDTO;
import com.morpheus.backend.entity.Image;
import com.morpheus.backend.entity.Scan;

import com.morpheus.backend.repository.ImageRepository;
import com.morpheus.exceptions.DefaultException;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private StorageFileService storageFIleService;

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

    public List<Image> createdImageList(ImageCreateDTO image){
        List<MultipartFile> binaryImages = image.getImage();
        List<String> descriptions = image.getDesc(); 
        List<String> name = image.getName(); 

        for (int i = 0; i < descriptions.size(); i++) {
            Image imageSave = new Image();
            System.out.println(descriptions.get(i));
            System.out.println(binaryImages.get(i));
            String url = storageFIleService.uploadFile(binaryImages.get(i), name.get(i), "image_helper");
            imageSave.setName(descriptions.get(i));
            imageSave.setAddress(url);
            Scan scanSave = new Scan();
            scanSave.setId(image.getScanId());
            imageSave.setScan(scanSave);

            imageRepository.save(imageSave);
        }

        return null;
    }
}
