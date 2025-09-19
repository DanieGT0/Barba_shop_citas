package com.barberapp.controller;

import com.barberapp.dto.CorteDto;
import com.barberapp.entity.Corte;
import com.barberapp.service.CorteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cortes")
@CrossOrigin(origins = "*")
public class CorteController {

    @Autowired
    private CorteService corteService;

    @GetMapping
    public ResponseEntity<List<CorteDto>> getAllCortes() {
        List<CorteDto> cortes = corteService.getAllCortes();
        return ResponseEntity.ok(cortes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorteDto> getCorteById(@PathVariable Long id) {
        CorteDto corte = corteService.getCorteById(id);
        return ResponseEntity.ok(corte);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<CorteDto>> getCortesByTipo(@PathVariable Corte.TipoCorte tipo) {
        List<CorteDto> cortes = corteService.getCortesByTipo(tipo);
        return ResponseEntity.ok(cortes);
    }

    @GetMapping("/precio")
    public ResponseEntity<List<CorteDto>> getCortesByPriceRange(
            @RequestParam(required = false) BigDecimal precioMin,
            @RequestParam(required = false) BigDecimal precioMax) {
        List<CorteDto> cortes = corteService.getCortesByPriceRange(precioMin, precioMax);
        return ResponseEntity.ok(cortes);
    }

    @GetMapping("/duracion")
    public ResponseEntity<List<CorteDto>> getCortesByDurationRange(
            @RequestParam(required = false) Integer duracionMin,
            @RequestParam(required = false) Integer duracionMax) {
        List<CorteDto> cortes = corteService.getCortesByDurationRange(duracionMin, duracionMax);
        return ResponseEntity.ok(cortes);
    }

    @GetMapping("/barbero/{barberoId}")
    public ResponseEntity<List<CorteDto>> getCortesByBarbero(@PathVariable Long barberoId) {
        List<CorteDto> cortes = corteService.getCortesByBarbero(barberoId);
        return ResponseEntity.ok(cortes);
    }

    @PostMapping
    public ResponseEntity<CorteDto> createCorte(@Valid @RequestBody CorteDto corteDto) {
        CorteDto newCorte = corteService.createCorte(corteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCorte);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorteDto> updateCorte(@PathVariable Long id, @Valid @RequestBody CorteDto corteDto) {
        CorteDto updatedCorte = corteService.updateCorte(id, corteDto);
        return ResponseEntity.ok(updatedCorte);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCorte(@PathVariable Long id) {
        corteService.deleteCorte(id);
        return ResponseEntity.noContent().build();
    }
}