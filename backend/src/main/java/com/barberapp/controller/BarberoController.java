package com.barberapp.controller;

import com.barberapp.dto.BarberoDto;
import com.barberapp.service.BarberoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/barberos")
@CrossOrigin(origins = "*")
public class BarberoController {

    @Autowired
    private BarberoService barberoService;

    @GetMapping("/public")
    public ResponseEntity<List<BarberoDto>> getAllBarberos() {
        List<BarberoDto> barberos = barberoService.getAllBarberos();
        return ResponseEntity.ok(barberos);
    }

    @GetMapping("/public/disponibles")
    public ResponseEntity<List<BarberoDto>> getBarberosDisponibles() {
        List<BarberoDto> barberos = barberoService.getBarberosDisponibles();
        return ResponseEntity.ok(barberos);
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<BarberoDto> getBarberoById(@PathVariable Long id) {
        BarberoDto barbero = barberoService.getBarberoById(id);
        return ResponseEntity.ok(barbero);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<BarberoDto> getBarberoByUsuarioId(@PathVariable Long usuarioId) {
        BarberoDto barbero = barberoService.getBarberoByUsuarioId(usuarioId);
        return ResponseEntity.ok(barbero);
    }

    @GetMapping("/public/horario")
    public ResponseEntity<List<BarberoDto>> getBarberosByHorario(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horarioInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horarioFin) {
        List<BarberoDto> barberos = barberoService.getBarberosByHorario(horarioInicio, horarioFin);
        return ResponseEntity.ok(barberos);
    }

    @GetMapping("/public/especialidad/{corteId}")
    public ResponseEntity<List<BarberoDto>> getBarberosByEspecialidad(@PathVariable Long corteId) {
        List<BarberoDto> barberos = barberoService.getBarberosByEspecialidad(corteId);
        return ResponseEntity.ok(barberos);
    }

    @PostMapping("/manage")
    public ResponseEntity<BarberoDto> createBarbero(@Valid @RequestBody BarberoDto barberoDto, @RequestParam Long usuarioId) {
        BarberoDto newBarbero = barberoService.createBarbero(barberoDto, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBarbero);
    }

    @PutMapping("/manage/{id}")
    public ResponseEntity<BarberoDto> updateBarbero(@PathVariable Long id, @Valid @RequestBody BarberoDto barberoDto) {
        BarberoDto updatedBarbero = barberoService.updateBarbero(id, barberoDto);
        return ResponseEntity.ok(updatedBarbero);
    }

    @DeleteMapping("/manage/{id}")
    public ResponseEntity<Void> deleteBarbero(@PathVariable Long id) {
        barberoService.deleteBarbero(id);
        return ResponseEntity.noContent().build();
    }
}