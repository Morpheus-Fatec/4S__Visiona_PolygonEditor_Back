package com.morpheus.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.morpheus.backend.DTO.FieldUpdatesDTO;
import com.morpheus.backend.DTO.PaginatedFieldResponse;
import com.morpheus.backend.DTO.Download.DownloadManual.ManualDTO;
import com.morpheus.backend.DTO.Download.DownloadSaida.SaidaDTO;
import com.morpheus.backend.DTO.GeoJsonView.FeatureCollectionDTO;
import com.morpheus.backend.DTO.GeoJsonView.FeatureSimpleDTO;
import com.morpheus.backend.DTO.GeoJsonView.manualClassification.ManualClassificationFeatureCollection;
import com.morpheus.backend.DTO.GeoJsonView.revisionClassification.RevisionClassificationCollectionOut;
import com.morpheus.backend.service.ClassificationService;
import com.morpheus.backend.entity.classifications.ClassificationControl;
import com.morpheus.backend.repository.classification.ClassificationControlRepository;
import com.morpheus.backend.service.FieldService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ContentDisposition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/field")
public class FieldController {
    @Autowired
    private FieldService fieldService;

    @Autowired
    private ClassificationService classificationService;

    @Autowired
    private ClassificationControlRepository classificationControlRepository;

    @GetMapping("/featureCollectionSimple")
    public ResponseEntity<PaginatedFieldResponse<FeatureSimpleDTO>> getAllFeatureCollectionSimpleDTO(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String soil,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String culture,
        @RequestParam(required = false) String harvest,
        @RequestParam(required = false) String farmName,
        @RequestParam (defaultValue = "1")int page, 
        @RequestParam(defaultValue = "20") int itens
    ) {

        PaginatedFieldResponse<FeatureSimpleDTO> fields = fieldService.getAllFeatureCollectionSimpleDTO(name, soil, status, culture,  harvest, farmName, page, itens);

        return ResponseEntity.ok(fields);
    }

    @GetMapping("/featureCollection/{id}")
    public ResponseEntity<FeatureCollectionDTO> getAllFeatureCollectionDTOByID(@PathVariable Long id) throws IllegalAccessError {
        FeatureCollectionDTO featureCollectionDTO = fieldService.getCompleteFieldById(id);

        return ResponseEntity.ok(featureCollectionDTO);
    }

    @GetMapping("/manualCollection/{id}")
    public ResponseEntity<ManualClassificationFeatureCollection> getManualClassificationByFieldId(@PathVariable Long id) throws IllegalAccessError {
        ManualClassificationFeatureCollection manualCollection = classificationService.getManualClassificationByFieldId(id);

        return ResponseEntity.ok(manualCollection);
    }

    @GetMapping("/revisionCollection/{id}")
    public ResponseEntity<RevisionClassificationCollectionOut> getRevisionClassificationByFieldId(@PathVariable Long id) throws IllegalAccessError {
        RevisionClassificationCollectionOut revisionCollection = classificationService.getRevisionClassificationByFieldId(id);

        return ResponseEntity.ok(revisionCollection);
    }

    @PutMapping("/{id}/avaliar")
    public ResponseEntity<Map<String, String>> updateField(
            @PathVariable Long id,
            @RequestBody FieldUpdatesDTO dto) {
        
        fieldService.updateField(id, dto);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Talhão atualizado com sucesso.");
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/downloadTalhao")
    public ResponseEntity<byte[]> downloadGeoJson(@PathVariable Long id) throws IOException {
        SaidaDTO automatic = fieldService.gerarGeoJsonPorId(id);
        ManualDTO manual = fieldService.gerarGeoJsonPorIdManual(id);

        ClassificationControl classificationControl = classificationControlRepository.findByFieldId(id);

        ObjectMapper mapper = new ObjectMapper();
        byte[] automaticoBytes = mapper.writeValueAsBytes(automatic);
        byte[] manualBytes = mapper.writeValueAsBytes(manual);

        LocalDateTime approvedDate = classificationControl.getDateTimeApproved();
        String farmName = automatic.getFeatures().getProperties().getFarm();
        String fieldName = automatic.getFeatures().getProperties().getName();
        String formatFarmName = farmName.replaceAll("\\s+", "_").toUpperCase();
        String formatFieldName = fieldName.replaceAll("\\s+", "_").toUpperCase();
        String formatDate = approvedDate.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
    

        String automaticFileName = formatFieldName + "_" + formatFarmName + "_" + formatDate + "_BM2_GEOJSON_SAIDA.geojson";
        String manualFileName = formatFieldName + "_" + formatFarmName + "_" + formatDate + "_MAPA_CLASSIF_MANUAL.geojson";
        String zipFileName = formatFieldName + "_" + formatFarmName + "_" + formatDate + "_MAPAS_TALHAO.zip";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream)) {
            ZipEntry entry1 = new ZipEntry(automaticFileName);
            zipOut.putNextEntry(entry1);
            zipOut.write(automaticoBytes);
            zipOut.closeEntry();

            ZipEntry entry2 = new ZipEntry(manualFileName);
            zipOut.putNextEntry(entry2);
            zipOut.write(manualBytes);
            zipOut.closeEntry();
        }

        byte[] zipBytes = byteArrayOutputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment()
            .filename(zipFileName)
            .build());

        return new ResponseEntity<>(zipBytes, headers, HttpStatus.OK);
    }


}
