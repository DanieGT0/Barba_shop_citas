package com.barberapp.service;

import com.barberapp.dto.CitaDto;
import com.barberapp.entity.Barbero;
import com.barberapp.entity.Cita;
import com.barberapp.entity.Corte;
import com.barberapp.entity.Usuario;
import com.barberapp.exception.AppointmentConflictException;
import com.barberapp.exception.ResourceNotFoundException;
import com.barberapp.repository.BarberoRepository;
import com.barberapp.repository.CitaRepository;
import com.barberapp.repository.CorteRepository;
import com.barberapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BarberoRepository barberoRepository;

    @Autowired
    private CorteRepository corteRepository;

    public List<CitaDto> getAllCitas() {
        return citaRepository.findAll().stream()
                .map(cita -> new CitaDto(cita, true))
                .collect(Collectors.toList());
    }

    public CitaDto getCitaById(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));
        return new CitaDto(cita, true);
    }

    public List<CitaDto> getCitasByCliente(Long clienteId) {
        return citaRepository.findByClienteId(clienteId).stream()
                .map(cita -> new CitaDto(cita, true))
                .collect(Collectors.toList());
    }

    public List<CitaDto> getCitasByBarbero(Long barberoId) {
        return citaRepository.findByBarberoId(barberoId).stream()
                .map(cita -> new CitaDto(cita, true))
                .collect(Collectors.toList());
    }

    public List<CitaDto> getUpcomingCitasByCliente(Long clienteId) {
        return citaRepository.findUpcomingCitasByCliente(clienteId, LocalDateTime.now()).stream()
                .map(cita -> new CitaDto(cita, true))
                .collect(Collectors.toList());
    }

    public List<CitaDto> getUpcomingCitasByBarbero(Long barberoId) {
        return citaRepository.findUpcomingCitasByBarbero(barberoId, LocalDateTime.now()).stream()
                .map(cita -> new CitaDto(cita, true))
                .collect(Collectors.toList());
    }

    public CitaDto createCita(CitaDto citaDto) {
        Usuario cliente = usuarioRepository.findById(citaDto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + citaDto.getClienteId()));

        Barbero barbero = barberoRepository.findById(citaDto.getBarberoId())
                .orElseThrow(() -> new ResourceNotFoundException("Barbero no encontrado con ID: " + citaDto.getBarberoId()));

        Corte corte = corteRepository.findById(citaDto.getCorteId())
                .orElseThrow(() -> new ResourceNotFoundException("Corte no encontrado con ID: " + citaDto.getCorteId()));

        LocalDateTime fechaHora = citaDto.getFechaHora();
        LocalDateTime finCita = fechaHora.plusMinutes(corte.getDuracionMinutos());

        if (citaRepository.existsConflictingAppointment(barbero.getId(), fechaHora, finCita)) {
            throw new AppointmentConflictException("El barbero ya tiene una cita en ese horario");
        }

        if (!barbero.getDisponible()) {
            throw new IllegalArgumentException("El barbero no está disponible");
        }

        Cita cita = new Cita();
        cita.setCliente(cliente);
        cita.setBarbero(barbero);
        cita.setCorte(corte);
        cita.setFechaHora(fechaHora);
        cita.setNotas(citaDto.getNotas());
        cita.setPrecioFinal(corte.getPrecioEstimado());

        Cita savedCita = citaRepository.save(cita);
        return new CitaDto(savedCita, true);
    }

    public CitaDto updateCita(Long id, CitaDto citaDto) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));

        if (citaDto.getFechaHora() != null && !citaDto.getFechaHora().equals(cita.getFechaHora())) {
            LocalDateTime nuevaFecha = citaDto.getFechaHora();
            LocalDateTime finCita = nuevaFecha.plusMinutes(cita.getCorte().getDuracionMinutos());

            if (citaRepository.existsConflictingAppointment(cita.getBarbero().getId(), nuevaFecha, finCita)) {
                throw new AppointmentConflictException("El barbero ya tiene una cita en ese horario");
            }
            cita.setFechaHora(nuevaFecha);
        }

        if (citaDto.getEstado() != null) {
            cita.setEstado(citaDto.getEstado());
        }

        if (citaDto.getNotas() != null) {
            cita.setNotas(citaDto.getNotas());
        }

        if (citaDto.getPrecioFinal() != null) {
            cita.setPrecioFinal(citaDto.getPrecioFinal());
        }

        Cita updatedCita = citaRepository.save(cita);
        return new CitaDto(updatedCita, true);
    }

    public void deleteCita(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));
        cita.setEstado(Cita.EstadoCita.CANCELADA);
        citaRepository.save(cita);
    }

    public List<CitaDto> getCitasByBarberoAndDateRange(Long barberoId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return citaRepository.findByBarberoAndFechaBetween(barberoId, fechaInicio, fechaFin).stream()
                .map(cita -> new CitaDto(cita, true))
                .collect(Collectors.toList());
    }
}