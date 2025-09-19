package com.barberapp.dto;

import com.barberapp.entity.Cita;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CitaDto {
    private Long id;

    @NotNull
    private Long clienteId;

    @NotNull
    private Long barberoId;

    @NotNull
    private Long corteId;

    @NotNull
    private LocalDateTime fechaHora;

    private Cita.EstadoCita estado;
    private BigDecimal precioFinal;
    private String notas;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    // For detailed responses
    private UsuarioDto cliente;
    private BarberoDto barbero;
    private CorteDto corte;

    public CitaDto() {}

    public CitaDto(Cita cita) {
        this.id = cita.getId();
        this.clienteId = cita.getCliente().getId();
        this.barberoId = cita.getBarbero().getId();
        this.corteId = cita.getCorte().getId();
        this.fechaHora = cita.getFechaHora();
        this.estado = cita.getEstado();
        this.precioFinal = cita.getPrecioFinal();
        this.notas = cita.getNotas();
        this.fechaCreacion = cita.getFechaCreacion();
        this.fechaActualizacion = cita.getFechaActualizacion();
    }

    public CitaDto(Cita cita, boolean includeDetails) {
        this(cita);
        if (includeDetails) {
            this.cliente = new UsuarioDto(cita.getCliente());
            this.barbero = new BarberoDto(cita.getBarbero());
            this.corte = new CorteDto(cita.getCorte());
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getBarberoId() { return barberoId; }
    public void setBarberoId(Long barberoId) { this.barberoId = barberoId; }

    public Long getCorteId() { return corteId; }
    public void setCorteId(Long corteId) { this.corteId = corteId; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public Cita.EstadoCita getEstado() { return estado; }
    public void setEstado(Cita.EstadoCita estado) { this.estado = estado; }

    public BigDecimal getPrecioFinal() { return precioFinal; }
    public void setPrecioFinal(BigDecimal precioFinal) { this.precioFinal = precioFinal; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    public UsuarioDto getCliente() { return cliente; }
    public void setCliente(UsuarioDto cliente) { this.cliente = cliente; }

    public BarberoDto getBarbero() { return barbero; }
    public void setBarbero(BarberoDto barbero) { this.barbero = barbero; }

    public CorteDto getCorte() { return corte; }
    public void setCorte(CorteDto corte) { this.corte = corte; }
}