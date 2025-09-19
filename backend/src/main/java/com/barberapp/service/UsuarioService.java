package com.barberapp.service;

import com.barberapp.dto.UsuarioDto;
import com.barberapp.entity.Usuario;
import com.barberapp.exception.ResourceNotFoundException;
import com.barberapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioDto> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioDto::new)
                .collect(Collectors.toList());
    }

    public UsuarioDto getUsuarioById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        return new UsuarioDto(usuario);
    }

    public UsuarioDto getUsuarioByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
        return new UsuarioDto(usuario);
    }

    public UsuarioDto createUsuario(UsuarioDto usuarioDto) {
        if (usuarioRepository.existsByEmail(usuarioDto.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + usuarioDto.getEmail());
        }
        if (usuarioRepository.existsByUsername(usuarioDto.getUsername())) {
            throw new IllegalArgumentException("Ya existe un usuario con el username: " + usuarioDto.getUsername());
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDto.getUsername());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApellido(usuarioDto.getApellido());
        usuario.setTelefono(usuarioDto.getTelefono());
        usuario.setTipo(usuarioDto.getTipo() != null ? usuarioDto.getTipo() : Usuario.TipoUsuario.CLIENTE);

        Usuario savedUsuario = usuarioRepository.save(usuario);
        return new UsuarioDto(savedUsuario);
    }

    public UsuarioDto updateUsuario(Long id, UsuarioDto usuarioDto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        if (!usuario.getEmail().equals(usuarioDto.getEmail()) &&
            usuarioRepository.existsByEmail(usuarioDto.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + usuarioDto.getEmail());
        }

        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApellido(usuarioDto.getApellido());
        usuario.setTelefono(usuarioDto.getTelefono());
        if (usuarioDto.getEmail() != null) {
            usuario.setEmail(usuarioDto.getEmail());
        }

        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return new UsuarioDto(updatedUsuario);
    }

    public void deleteUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    public List<UsuarioDto> getUsuariosByTipo(Usuario.TipoUsuario tipo) {
        return usuarioRepository.findActiveUsersByType(tipo).stream()
                .map(UsuarioDto::new)
                .collect(Collectors.toList());
    }
}