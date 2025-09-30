package com.celular.celular.controller;

import com.celular.celular.dto.request.CelularForm;
import com.celular.celular.dto.response.CelularDto;
import com.celular.celular.service.CelularService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/celulares")
public class CelularController {

    private final CelularService celularService;

    public CelularController(CelularService celularService) {
        this.celularService = celularService;
    }

    @GetMapping("/{celularName}")
    public ResponseEntity<CelularDto> getCelularByName(@PathVariable String celularName) {
        CelularDto celularDTO = celularService.getCelularByName(celularName);
        return ResponseEntity.ok(celularDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<Set<CelularDto>> getAllCelular() {
        Set<CelularDto> celularDtos = celularService.getAllCelular();
        return ResponseEntity.ok(celularDtos);
    }

    @PostMapping
    public ResponseEntity<CelularDto> createCelular(@RequestBody CelularForm celularForm) {
        CelularDto celularDtoCreated = celularService.createCelularDto(celularForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(celularDtoCreated);
    }

    @PutMapping("/{celularName}")
    public ResponseEntity<CelularDto> updateCelular (@PathVariable String celularName, @RequestBody CelularForm celularForm){
        CelularDto celularDtoUpdated = celularService.updateCelularDto(celularName, celularForm);
        return ResponseEntity.ok(celularDtoUpdated);
    }

    @DeleteMapping("/{celularName}")
    public ResponseEntity<Void> deleteCelular(@PathVariable String celularName){
        celularService.softDeleteCelularByName(celularName);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{celularName}")
    public ResponseEntity<CelularDto> patchCelular(@PathVariable String celularName, @RequestBody CelularForm celularForm){
        CelularDto celularDtoUpdated = celularService.patchCelularByName(celularName, celularForm);
        return ResponseEntity.ok(celularDtoUpdated);
    }
}
