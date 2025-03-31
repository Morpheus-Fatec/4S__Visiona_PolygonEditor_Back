package com.morpheus.backend.DTO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageCreateDTO {
    private List<MultipartFile> image;
    private List<String> desc;
    private List<String> name;
    private Long scanId;
}