package com.barberapp.dto;

import com.barberapp.entity.Barbero;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class BarberoDto {
    private Long id;
    private UsuarioDto usuario;
    private String descripcion;
    private Integer anosExperiencia;
    private BigDecimal precioBase;
    private LocalTime horarioInicio;
    private LocalTime horarioFin;
    private String urlFotoPerfil;
    private Boolean disponible;
    private List<CorteDto> especialidades;

    public BarberoDto() {}

    public BarberoDto(Barbero barbero) {
        this.id = barbero.getId();
        this.usuario = new UsuarioDto(barbero.getUsuario());
        this.descripcion = barbero.getDescripcion();
        this.anosExperiencia = barbero.getAnosExperiencia();
        this.precioBase = barbero.getPrecioBase();
        this.horarioInicio = barbero.getHorarioInicio();
        this.horarioFin = barbero.getHorarioFin();
        this.urlFotoPerfil = barbero.getUrlFotoPerfil();
        this.disponible = barbero.getDisponible();

        if (barbero.getEspecialidades() != null) {
            this.especialidades = barbero.getEspecialidades().stream()
                .map(CorteDto::new)
                .collect(Collectors.toList());
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UsuarioDto getUsuario() { return usuario; }
    public void setUsuario(UsuarioDto usuario) { this.usuario = usuario; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getAnosExperiencia() { return anosExperiencia; }
    public void setAnosExperiencia(Integer anosExperiencia) { this.anosExperiencia = anosExperiencia; }

    public BigDecimal getPrecioBase() { return precioBase; }
    public void setPrecioBase(BigDecimal precioBase) { this.precioBase = precioBase; }

    public LocalTime getHorarioInicio() { return horarioInicio; }
    public void setHorarioInicio(LocalTime horarioInicio) { this.horarioInicio = horarioInicio; }

    public LocalTime getHorarioFin() { return horarioFin; }
    public void setHorarioFin(LocalTime horarioFin) { this.horarioFin = horarioFin; }

    public String getUrlFotoPerfil() { return urlFotoPerfil; }
    public void setUrlFotoPerfil(String urlFotoPerfil) { this.urlFotoPerfil = urlFotoPerfil; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }

    public List<CorteDto> getEspecialidades() { return especialidades; }
    public void setEspecialidades(List<CorteDto> especialidades) { this.especialidades = especialidades; }
}