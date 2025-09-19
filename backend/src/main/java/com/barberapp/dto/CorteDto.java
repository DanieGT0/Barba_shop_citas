package com.barberapp.dto;

import com.barberapp.entity.Corte;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class CorteDto {
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String nombre;

    @Size(max = 500)
    private String descripcion;

    private BigDecimal precioEstimado;
    private Integer duracionMinutos;
    private String urlImagen;
    private Corte.TipoCorte tipo;

    public CorteDto() {}

    public CorteDto(Corte corte) {
        this.id = corte.getId();
        this.nombre = corte.getNombre();
        this.descripcion = corte.getDescripcion();
        this.precioEstimado = corte.getPrecioEstimado();
        this.duracionMinutos = corte.getDuracionMinutos();
        this.urlImagen = corte.getUrlImagen();
        this.tipo = corte.getTipo();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecioEstimado() { return precioEstimado; }
    public void setPrecioEstimado(BigDecimal precioEstimado) { this.precioEstimado = precioEstimado; }

    public Integer getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(Integer duracionMinutos) { this.duracionMinutos = duracionMinutos; }

    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }

    public Corte.TipoCorte getTipo() { return tipo; }
    public void setTipo(Corte.TipoCorte tipo) { this.tipo = tipo; }
}