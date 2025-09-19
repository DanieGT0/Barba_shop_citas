package com.barberapp.repository;

import com.barberapp.entity.Barbero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface BarberoRepository extends JpaRepository<Barbero, Long> {

    List<Barbero> findByDisponible(Boolean disponible);

    @Query("SELECT b FROM Barbero b WHERE b.disponible = true AND " +
           "(:horarioInicio IS NULL OR b.horarioInicio <= :horarioInicio) AND " +
           "(:horarioFin IS NULL OR b.horarioFin >= :horarioFin)")
    List<Barbero> findAvailableBarbersBySchedule(
            @Param("horarioInicio") LocalTime horarioInicio,
            @Param("horarioFin") LocalTime horarioFin);

    @Query("SELECT b FROM Barbero b JOIN b.especialidades c WHERE c.id = :corteId AND b.disponible = true")
    List<Barbero> findByEspecialidadAndDisponible(@Param("corteId") Long corteId);

    @Query("SELECT b FROM Barbero b WHERE b.usuario.id = :usuarioId")
    Barbero findByUsuarioId(@Param("usuarioId") Long usuarioId);
}