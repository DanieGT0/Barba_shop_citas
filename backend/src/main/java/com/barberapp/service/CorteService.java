package com.barberapp.service;

import com.barberapp.dto.CorteDto;
import com.barberapp.entity.Corte;
import com.barberapp.exception.ResourceNotFoundException;
import com.barberapp.repository.CorteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CorteService {

    @Autowired
    private CorteRepository corteRepository;

    public List<CorteDto> getAllCortes() {
        return corteRepository.findAll().stream()
                .map(CorteDto::new)
                .collect(Collectors.toList());
    }

    public CorteDto getCorteById(Long id) {
        Corte corte = corteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Corte no encontrado con ID: " + id));
        return new CorteDto(corte);
    }

    public List<CorteDto> getCortesByTipo(Corte.TipoCorte tipo) {
        return corteRepository.findByTipo(tipo).stream()
                .map(CorteDto::new)
                .collect(Collectors.toList());
    }

    public List<CorteDto> getCortesByPriceRange(BigDecimal precioMin, BigDecimal precioMax) {
        return corteRepository.findByPriceRange(precioMin, precioMax).stream()
                .map(CorteDto::new)
                .collect(Collectors.toList());
    }

    public List<CorteDto> getCortesByDurationRange(Integer duracionMin, Integer duracionMax) {
        return corteRepository.findByDurationRange(duracionMin, duracionMax).stream()
                .map(CorteDto::new)
                .collect(Collectors.toList());
    }

    public List<CorteDto> getCortesByBarbero(Long barberoId) {
        return corteRepository.findByBarberoId(barberoId).stream()
                .map(CorteDto::new)
                .collect(Collectors.toList());
    }

    public CorteDto createCorte(CorteDto corteDto) {
        Corte corte = new Corte();
        corte.setNombre(corteDto.getNombre());
        corte.setDescripcion(corteDto.getDescripcion());
        corte.setPrecioEstimado(corteDto.getPrecioEstimado());
        corte.setDuracionMinutos(corteDto.getDuracionMinutos());
        corte.setUrlImagen(corteDto.getUrlImagen());
        corte.setTipo(corteDto.getTipo());

        Corte savedCorte = corteRepository.save(corte);
        return new CorteDto(savedCorte);
    }

    public CorteDto updateCorte(Long id, CorteDto corteDto) {
        Corte corte = corteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Corte no encontrado con ID: " + id));

        corte.setNombre(corteDto.getNombre());
        corte.setDescripcion(corteDto.getDescripcion());
        corte.setPrecioEstimado(corteDto.getPrecioEstimado());
        corte.setDuracionMinutos(corteDto.getDuracionMinutos());
        corte.setUrlImagen(corteDto.getUrlImagen());
        corte.setTipo(corteDto.getTipo());

        Corte updatedCorte = corteRepository.save(corte);
        return new CorteDto(updatedCorte);
    }

    public void deleteCorte(Long id) {
        if (!corteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Corte no encontrado con ID: " + id);
        }
        corteRepository.deleteById(id);
    }
}