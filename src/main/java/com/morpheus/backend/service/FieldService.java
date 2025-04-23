package com.morpheus.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.morpheus.backend.DTO.ClassificationDTO;
import com.morpheus.backend.DTO.CreateFieldDTO;
import com.morpheus.backend.DTO.FarmDTO;
import com.morpheus.backend.DTO.FieldDTO;
import com.morpheus.backend.DTO.FieldUpdatesDTO;
import com.morpheus.backend.DTO.PaginatedFieldResponse;
import com.morpheus.backend.DTO.Download.DownloadManual.FeatureManualDto;
import com.morpheus.backend.DTO.Download.DownloadManual.FieldPropertiesManualDto;
import com.morpheus.backend.DTO.Download.DownloadManual.ManualDTO;
import com.morpheus.backend.DTO.Download.DownloadSaida.CrsDto;
import com.morpheus.backend.DTO.Download.DownloadSaida.FeaturesDto;
import com.morpheus.backend.DTO.Download.DownloadSaida.FieldPropertiesDto;
import com.morpheus.backend.DTO.Download.DownloadSaida.GeometryDto;
import com.morpheus.backend.DTO.Download.DownloadSaida.SaidaDTO;
import com.morpheus.backend.DTO.GeoJsonView.FeatureCollectionDTO;
import com.morpheus.backend.DTO.GeoJsonView.FeatureCollectionSimpleDTO;
import com.morpheus.backend.DTO.GeoJsonView.FeatureSimpleDTO;
import com.morpheus.backend.DTO.GeoJsonView.FieldFeatureDTO;
import com.morpheus.backend.DTO.GeoJsonView.GeometryDTO;
import com.morpheus.backend.DTO.GeoJsonView.ImageViewDTO;
import com.morpheus.backend.DTO.GeoJsonView.PropertiesDTO;
import com.morpheus.backend.DTO.GeoJsonView.classification.ClassificationColletion;
import com.morpheus.backend.DTO.GeoJsonView.classification.ClassificationFeature;
import com.morpheus.backend.DTO.GeoJsonView.classification.ClassificationProperties;
import com.morpheus.backend.entity.Culture;
import com.morpheus.backend.entity.Farm;
import com.morpheus.backend.entity.Field;
import com.morpheus.backend.entity.Image;
import com.morpheus.backend.entity.Scan;
import com.morpheus.backend.entity.Soil;
import com.morpheus.backend.entity.Status;
import com.morpheus.backend.entity.classifications.ClassificationAutomatic;
import com.morpheus.backend.repository.CultureRepository;
import com.morpheus.backend.repository.FarmRepository;
import com.morpheus.backend.repository.FieldRepository;
import com.morpheus.backend.repository.ImageRepository;
import com.morpheus.backend.repository.SoilRepository;
import com.morpheus.backend.utilities.ConverterToMultipolygon;
import com.morpheus.backend.repository.classification.ClassificationAutomaticRepository;
import com.morpheus.exceptions.DefaultException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FieldService {

    @Autowired
    ConverterToMultipolygon converterToMultipolygon;

    @Autowired
    FarmService farmService;

    @Autowired
    SoilService soilService;
    
    @Autowired
    CultureService cultureService;

    @Autowired
    private FieldRepository fieldRepository;
    
    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private CultureRepository cultureRepository;

    @Autowired
    private SoilRepository soilRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ClassificationAutomaticRepository classificationAutomaticRepository;

    public Field createField(CreateFieldDTO fieldDTO, Scan scan){
        try {
            if (fieldDTO.getNameFarm().isEmpty()){
                throw new DefaultException("Farm não pode ser nulo.");
            }

            Farm farmField = new Farm();
            farmField.setFarmName(fieldDTO.getNameFarm());
            Farm farm = farmRepository.save(farmField);

            Culture culture = cultureRepository.findByName(fieldDTO.getCulture())
            .orElseGet(() -> {
                Culture newCulture = new Culture();
                newCulture.setName(fieldDTO.getCulture());
                return cultureRepository.save(newCulture);
            });

            Soil soil = soilRepository.findByName(fieldDTO.getSoil())
            .orElseGet(() -> {
                Soil newSoil = new Soil();
                newSoil.setName(fieldDTO.getSoil());
                return soilRepository.save(newSoil);
            });
    
            Field field = new Field();
            field.setFarm(farm);
            field.setHarvest(fieldDTO.getHarvest());
            field.setCulture(culture);
            field.setSoil(soil);
            field.setName(fieldDTO.getNameField());
            field.setArea(fieldDTO.getArea());
            field.setProductivity(fieldDTO.getProductivity());
            field.setStatus(Status.fromPortuguese("Pendente"));
            field.setCoordinates(fieldDTO.convertStringToMultiPolygon());
            field.setScanning(scan);
            fieldRepository.save(field);
    
            return field;

    
        } catch (Exception e) {
            throw new DefaultException("Erro ao criar o talhão: " + e.getMessage());
        }
    }

    public PaginatedFieldResponse<FeatureSimpleDTO> getAllFeatureCollectionSimpleDTO(
        String name, String soil, String status, String culture, String harvest, String farmName, int page, int itens) {
        PageRequest pageable = PageRequest.of(page -1,itens,Sort.by(Sort.Direction.ASC, "id_talhao"));
        Page<FieldDTO> results = fieldRepository.getAllFeatureSimpleDTO(name, soil, status, culture, harvest, farmName, pageable);
    
        List<FeatureSimpleDTO> featureSimpleDTOList = results.stream().map(obj -> {
            // Criando o DTO da Fazenda
            FarmDTO farmDTO = new FarmDTO();
            farmDTO.setFarmName((String) obj.getFarm().getFarmName());
            farmDTO.setFarmCity((String) obj.getFarm().getFarmCity());
            farmDTO.setFarmState((String) obj.getFarm().getFarmState());
    
            // Criando o DTO das propriedades
            PropertiesDTO properties = new PropertiesDTO();
            properties.setId((Long) ((Number) obj.getId()));
            properties.setName((String) obj.getName());
            properties.setFarm(farmDTO);
            properties.setCulture((String) obj.getCulture());
            properties.setArea((BigDecimal) obj.getArea());
            properties.setSoil((String) obj.getSoil());
            properties.setHarvest((String) obj.getHarvest());
            Status statusProp = Status.valueOf(((String) obj.getStatus()).toUpperCase()); 
            properties.setStatus(statusProp.getPortugueseValue());
    
            // Criando o DTO da geometria
            GeometryDTO geometry = new GeometryDTO();
            try {
                geometry.convertToGeoJson((String) obj.getCoordinates().toString());
            } catch (JsonProcessingException e) {

                e.printStackTrace();
            }
            
            // Criando o DTO da Feature
            FeatureSimpleDTO dto = new FeatureSimpleDTO();
            dto.setProperties(properties);
            dto.setGeometry(geometry);
    
            return dto;
            }).collect(Collectors.toList());
    
            FeatureCollectionSimpleDTO featureCollection = new FeatureCollectionSimpleDTO();
            featureCollection.setFeatures(featureSimpleDTOList);
    
            return new PaginatedFieldResponse<FeatureSimpleDTO>(
                featureSimpleDTOList,
                results.getTotalPages(),
                results.getTotalElements()
            );
        }
    
    public FeatureCollectionDTO getCompleteFieldById(Long idField) {
        FieldDTO field = fieldRepository.getFieldById(idField).orElseThrow(() -> new DefaultException("Talhão não encontrado."));
        Long scanID = field.getScanningId();
        List<ClassificationDTO> classifications = classificationAutomaticRepository.getClassificationAutomaticByFieldId(field.getId());
        List<Image> images = imageRepository.getImagesByScanId(scanID);

        FarmDTO farmDTO = field.getFarm();

        PropertiesDTO properties = new PropertiesDTO();
        properties.setId(field.getId());
        properties.setName(field.getName());
        properties.setArea(field.getArea());
        properties.setCulture(field.getCulture());
        properties.setHarvest(field.getHarvest());
        Status statusProp = Status.valueOf(((String) field.getStatus()).toUpperCase()); 
        properties.setStatus(statusProp.getPortugueseValue());
        properties.setSoil(field.getSoil());
        properties.setFarm(farmDTO);

        GeometryDTO geometry = new GeometryDTO();
        try{
            geometry.convertToGeoJson(field.getCoordinates());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        
        List<ClassificationFeature> classificationDTOs = classifications.stream().map(classification -> {
            ClassificationFeature classificationDTO = new ClassificationFeature();
            ClassificationProperties classificationProperties = new ClassificationProperties(classification.getId(), classification.getArea(), classification.getClassEntity());
            GeometryDTO classificationGeometry = new GeometryDTO();
            try {
                classificationGeometry.convertToGeoJson(classification.getCoordinates());
            } catch (JsonProcessingException e) {

                e.printStackTrace();
            }
            classificationDTO.setProperties(classificationProperties);
            classificationDTO.setGeometry(classificationGeometry);
            return classificationDTO;
        }).collect(Collectors.toList());


        List<ImageViewDTO> imageDTOs = images.stream().map(image -> {
            ImageViewDTO imageDTO = new ImageViewDTO();
            imageDTO.setName(image.getName());
            imageDTO.setLink(image.getAddress());
            return imageDTO;
        }).collect(Collectors.toList());

        FieldFeatureDTO fieldFeatureDTO = new FieldFeatureDTO();
        fieldFeatureDTO.setProperties(properties);
        fieldFeatureDTO.setGeometry(geometry);
        fieldFeatureDTO.setImages(imageDTOs);

        ClassificationColletion classificationCollection = new ClassificationColletion();
        classificationCollection.setFeatures(classificationDTOs);
        fieldFeatureDTO.setClassification(classificationCollection);
        

        // Criando e retornando o FeatureCollectionDTO corretamente
        FeatureCollectionDTO featureCollection = new FeatureCollectionDTO();
        featureCollection.setFeatures(fieldFeatureDTO);
        
        return featureCollection;
    }

    public FieldUpdatesDTO updateField(Long id, FieldUpdatesDTO dto) {
        Field field = fieldRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Talhão não encontrado!"));

        validate(dto);
    
        field.setName(dto.getName());
        field.setHarvest(dto.getHarvest());
        field.setProductivity(dto.getProductivity());
    
        if (dto.getFarm() != null) {
            Farm farm = dto.getFarm();
            FarmDTO farmDTO = new FarmDTO(farm.getFarmName(), farm.getFarmCity(), farm.getFarmState());
            farmService.updateFarm(farm.getId(), farmDTO);
        }
        
        if (dto.getSoil() != null) {
            soilService.updateSoil(dto.getSoil().getId(), dto.getSoil().getName());
        }
        
        if (dto.getCulture() != null) {
            cultureService.updateCulture(dto.getCulture().getId(), dto.getCulture().getName());
        }
    
        Field updatedField = fieldRepository.save(field);

        return mapToDto(updatedField);
    }
    

    private void validate(FieldUpdatesDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new DefaultException("O nome do Talhão é obrigatório.");
        }
        if (dto.getHarvest() == null || dto.getHarvest().trim().isEmpty()) {
            throw new DefaultException("A safra é obrigatória.");
        }
        if (dto.getProductivity() == null) {
            throw new DefaultException("A produtividade é obrigatória.");
        }
    }
    
    private FieldUpdatesDTO mapToDto(Field field) {
        FieldUpdatesDTO dto = new FieldUpdatesDTO();
        dto.setId(field.getId());
        dto.setName(field.getName());
        dto.setArea(field.getArea());
        dto.setHarvest(field.getHarvest());
        dto.setStatus(field.getStatus().toString());
        dto.setProductivity(field.getProductivity());
    
        Farm farm = new Farm();
        farm.setId(field.getFarm().getId());
        farm.setFarmName(field.getFarm().getFarmName());
        farm.setFarmCity(field.getFarm().getFarmCity());
        farm.setFarmState(field.getFarm().getFarmState());
        dto.setFarm(farm);

        Culture culture = new Culture();
        culture.setId(field.getCulture().getId());
        culture.setName(field.getCulture().getName());
        dto.setCulture(culture);

        Soil soil = new Soil();
        soil.setId(field.getSoil().getId());
        soil.setName(field.getSoil().getName());
        dto.setSoil(soil);
    
        return dto;
    }

    public SaidaDTO gerarGeoJsonPorId(Long fieldId) {
    Field field = fieldRepository.findById(fieldId)
        .orElseThrow(() -> new EntityNotFoundException("Talhão não encontrado"));

    if (field.getStatus() != Status.APPROVED) {
        throw new DefaultException("O talhão precisa estar aprovado para gerar o GeoJSON");
    }

    CrsDto crs = new CrsDto();

    FieldPropertiesDto propertiesDto = new FieldPropertiesDto(
        field.getName(), 
        field.getArea(), 
        field.getSoil().getName(),
        field.getCulture().getName(),
        field.getHarvest(),
        field.getFarm().getFarmName()
    );

    List<List<List<List<Double>>>> multipolygon = converterToMultipolygon.converterToMultiPolygon(field.getCoordinates());


    GeometryDto geometryDto = new GeometryDto(multipolygon);;
    FeaturesDto featureDto = new FeaturesDto(propertiesDto, geometryDto);

    return new SaidaDTO(crs, featureDto);
    }

    public ManualDTO gerarGeoJsonPorIdManual(Long fielId){
        ClassificationAutomatic classificationAutomatic = classificationAutomaticRepository.findById(fielId)
        .orElseThrow(() -> new EntityNotFoundException("Talhão não encontrado"));

        Field field = classificationAutomatic.getClassificationControl().getField();

        if (field.getStatus() != Status.APPROVED) {
            throw new DefaultException("O talhão precisa estar aprovado para gerar o GeoJSON");
        }

        CrsDto crs = new CrsDto();

        FieldPropertiesManualDto propertiesManualDto = new FieldPropertiesManualDto(
            classificationAutomatic.getClassEntity().getName(),
            classificationAutomatic.getArea(),
            classificationAutomatic.getClassificationControl().getField().getName());

        List<List<List<List<Double>>>> multipolygon = converterToMultipolygon.converterToMultiPolygon(classificationAutomatic.getCoordenadas());

        GeometryDto geometryDto = new GeometryDto(multipolygon);;
        FeatureManualDto featureManualDto = new FeatureManualDto(propertiesManualDto, geometryDto);
    
        return new ManualDTO(crs, featureManualDto);

    }

}