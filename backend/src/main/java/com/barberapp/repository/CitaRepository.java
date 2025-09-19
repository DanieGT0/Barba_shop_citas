package com.barberapp.repository;

import com.barberapp.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByClienteId(Long clienteId);

    List<Cita> findByBarberoId(Long barberoId);

    List<Cita> findByEstado(Cita.EstadoCita estado);

    @Query("SELECT c FROM Cita c WHERE c.barbero.id = :barberoId AND " +
           "c.fechaHora BETWEEN :fechaInicio AND :fechaFin AND " +
           "c.estado NOT IN ('CANCELADA')")
    List<Cita> findByBarberoAndFechaBetween(
            @Param("barberoId") Long barberoId,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);

    @Query("SELECT c FROM Cita c WHERE c.cliente.id = :clienteId AND " +
           "c.fechaHora >= :fechaDesde ORDER BY c.fechaHora ASC")
    List<Cita> findUpcomingCitasByCliente(
            @Param("clienteId") Long clienteId,
            @Param("fechaDesde") LocalDateTime fechaDesde);

    @Query("SELECT c FROM Cita c WHERE c.barbero.id = :barberoId AND " +
           "c.fechaHora >= :fechaDesde ORDER BY c.fechaHora ASC")
    List<Cita> findUpcomingCitasByBarbero(
            @Param("barberoId") Long barberoId,
            @Param("fechaDesde") LocalDateTime fechaDesde);

    @Query("SELECT COUNT(c) > 0 FROM Cita c WHERE c.barbero.id = :barberoId AND " +
           "c.fechaHora BETWEEN :inicioSlot AND :finSlot AND " +
           "c.estado NOT IN ('CANCELADA')")
    boolean existsConflictingAppointment(
            @Param("barberoId") Long barberoId,
            @Param("inicioSlot") LocalDateTime inicioSlot,
            @Param("finSlot") LocalDateTime finSlot);
}