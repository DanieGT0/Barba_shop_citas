package com.barberapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cortes")
public class Corte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String nombre;

    @Size(max = 500)
    private String descripcion;

    @Column(name = "precio_estimado", precision = 10, scale = 2)
    private BigDecimal precioEstimado;

    @Column(name = "duracion_minutos")
    private Integer duracionMinutos;

    @Column(name = "url_imagen")
    private String urlImagen;

    @Enumerated(EnumType.STRING)
    private TipoCorte tipo;

    @OneToMany(mappedBy = "corte", cascade = CascadeType.ALL)
    private List<Cita> citas;

    @ManyToMany(mappedBy = "especialidades")
    private List<Barbero> barberos;

    public enum TipoCorte {
        CLASICO, MODERNO, BARBA, BIGOTE, COMPLETO
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

    public TipoCorte getTipo() { return tipo; }
    public void setTipo(TipoCorte tipo) { this.tipo = tipo; }

    public List<Cita> getCitas() { return citas; }
    public void setCitas(List<Cita> citas) { this.citas = citas; }

    public List<Barbero> getBarberos() { return barberos; }
    public void setBarberos(List<Barbero> barberos) { this.barberos = barberos; }
}