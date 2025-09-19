package com.barberapp.service;

import com.barberapp.dto.BarberoDto;
import com.barberapp.entity.Barbero;
import com.barberapp.entity.Usuario;
import com.barberapp.exception.ResourceNotFoundException;
import com.barberapp.repository.BarberoRepository;
import com.barberapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BarberoService {

    @Autowired
    private BarberoRepository barberoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<BarberoDto> getAllBarberos() {
        return barberoRepository.findAll().stream()
                .map(BarberoDto::new)
                .collect(Collectors.toList());
    }

    public List<BarberoDto> getBarberosDisponibles() {
        return barberoRepository.findByDisponible(true).stream()
                .map(BarberoDto::new)
                .collect(Collectors.toList());
    }

    public BarberoDto getBarberoById(Long id) {
        Barbero barbero = barberoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Barbero no encontrado con ID: " + id));
        return new BarberoDto(barbero);
    }

    public BarberoDto getBarberoByUsuarioId(Long usuarioId) {
        Barbero barbero = barberoRepository.findByUsuarioId(usuarioId);
        if (barbero == null) {
            throw new ResourceNotFoundException("Barbero no encontrado para el usuario ID: " + usuarioId);
        }
        return new BarberoDto(barbero);
    }

    public BarberoDto createBarbero(BarberoDto barberoDto, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId));

        if (usuario.getTipo() != Usuario.TipoUsuario.BARBERO) {
            usuario.setTipo(Usuario.TipoUsuario.BARBERO);
            usuarioRepository.save(usuario);
        }

        Barbero barbero = new Barbero();
        barbero.setUsuario(usuario);
        barbero.setDescripcion(barberoDto.getDescripcion());
        barbero.setAnosExperiencia(barberoDto.getAnosExperiencia());
        barbero.setPrecioBase(barberoDto.getPrecioBase());
        barbero.setHorarioInicio(barberoDto.getHorarioInicio());
        barbero.setHorarioFin(barberoDto.getHorarioFin());
        barbero.setUrlFotoPerfil(barberoDto.getUrlFotoPerfil());

        Barbero savedBarbero = barberoRepository.save(barbero);
        return new BarberoDto(savedBarbero);
    }

    public BarberoDto updateBarbero(Long id, BarberoDto barberoDto) {
        Barbero barbero = barberoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Barbero no encontrado con ID: " + id));

        barbero.setDescripcion(barberoDto.getDescripcion());
        barbero.setAnosExperiencia(barberoDto.getAnosExperiencia());
        barbero.setPrecioBase(barberoDto.getPrecioBase());
        barbero.setHorarioInicio(barberoDto.getHorarioInicio());
        barbero.setHorarioFin(barberoDto.getHorarioFin());
        barbero.setUrlFotoPerfil(barberoDto.getUrlFotoPerfil());
        barbero.setDisponible(barberoDto.getDisponible());

        Barbero updatedBarbero = barberoRepository.save(barbero);
        return new BarberoDto(updatedBarbero);
    }

    public void deleteBarbero(Long id) {
        Barbero barbero = barberoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Barbero no encontrado con ID: " + id));
        barbero.setDisponible(false);
        barberoRepository.save(barbero);
    }

    public List<BarberoDto> getBarberosByHorario(LocalTime horarioInicio, LocalTime horarioFin) {
        return barberoRepository.findAvailableBarbersBySchedule(horarioInicio, horarioFin).stream()
                .map(BarberoDto::new)
                .collect(Collectors.toList());
    }

    public List<BarberoDto> getBarberosByEspecialidad(Long corteId) {
        return barberoRepository.findByEspecialidadAndDisponible(corteId).stream()
                .map(BarberoDto::new)
                .collect(Collectors.toList());
    }
}