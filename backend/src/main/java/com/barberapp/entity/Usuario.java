package com.barberapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String username;

    @Email
    @NotBlank
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(max = 50)
    private String nombre;

    @NotBlank
    @Size(max = 50)
    private String apellido;

    @Size(max = 15)
    private String telefono;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

    @Column(name = "oauth2_id")
    private String oauth2Id;

    @Column(name = "oauth2_provider")
    private String oauth2Provider;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "activo")
    private Boolean activo = true;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Cita> citasComoCliente;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Barbero barbero;

    public enum TipoUsuario {
        CLIENTE, BARBERO, ADMIN
    }

    public Usuario() {
        this.fechaRegistro = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public TipoUsuario getTipo() { return tipo; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }

    public String getOauth2Id() { return oauth2Id; }
    public void setOauth2Id(String oauth2Id) { this.oauth2Id = oauth2Id; }

    public String getOauth2Provider() { return oauth2Provider; }
    public void setOauth2Provider(String oauth2Provider) { this.oauth2Provider = oauth2Provider; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public List<Cita> getCitasComoCliente() { return citasComoCliente; }
    public void setCitasComoCliente(List<Cita> citasComoCliente) { this.citasComoCliente = citasComoCliente; }

    public Barbero getBarbero() { return barbero; }
    public void setBarbero(Barbero barbero) { this.barbero = barbero; }
}