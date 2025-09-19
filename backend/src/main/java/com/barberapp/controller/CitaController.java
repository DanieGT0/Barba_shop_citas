package com.barberapp.controller;

import com.barberapp.dto.CitaDto;
import com.barberapp.service.CitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping
    public ResponseEntity<List<CitaDto>> getAllCitas() {
        List<CitaDto> citas = citaService.getAllCitas();
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaDto> getCitaById(@PathVariable Long id) {
        CitaDto cita = citaService.getCitaById(id);
        return ResponseEntity.ok(cita);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CitaDto>> getCitasByCliente(@PathVariable Long clienteId) {
        List<CitaDto> citas = citaService.getCitasByCliente(clienteId);
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/barbero/{barberoId}")
    public ResponseEntity<List<CitaDto>> getCitasByBarbero(@PathVariable Long barberoId) {
        List<CitaDto> citas = citaService.getCitasByBarbero(barberoId);
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/cliente/{clienteId}/proximas")
    public ResponseEntity<List<CitaDto>> getUpcomingCitasByCliente(@PathVariable Long clienteId) {
        List<CitaDto> citas = citaService.getUpcomingCitasByCliente(clienteId);
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/barbero/{barberoId}/proximas")
    public ResponseEntity<List<CitaDto>> getUpcomingCitasByBarbero(@PathVariable Long barberoId) {
        List<CitaDto> citas = citaService.getUpcomingCitasByBarbero(barberoId);
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/barbero/{barberoId}/rango")
    public ResponseEntity<List<CitaDto>> getCitasByBarberoAndDateRange(
            @PathVariable Long barberoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<CitaDto> citas = citaService.getCitasByBarberoAndDateRange(barberoId, fechaInicio, fechaFin);
        return ResponseEntity.ok(citas);
    }

    @PostMapping
    public ResponseEntity<CitaDto> createCita(@Valid @RequestBody CitaDto citaDto) {
        CitaDto newCita = citaService.createCita(citaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaDto> updateCita(@PathVariable Long id, @Valid @RequestBody CitaDto citaDto) {
        CitaDto updatedCita = citaService.updateCita(id, citaDto);
        return ResponseEntity.ok(updatedCita);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable Long id) {
        citaService.deleteCita(id);
        return ResponseEntity.noContent().build();
    }
}