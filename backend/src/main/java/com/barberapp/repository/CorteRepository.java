package com.barberapp.repository;

import com.barberapp.entity.Corte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CorteRepository extends JpaRepository<Corte, Long> {

    List<Corte> findByTipo(Corte.TipoCorte tipo);

    @Query("SELECT c FROM Corte c WHERE " +
           "(:precioMin IS NULL OR c.precioEstimado >= :precioMin) AND " +
           "(:precioMax IS NULL OR c.precioEstimado <= :precioMax)")
    List<Corte> findByPriceRange(
            @Param("precioMin") BigDecimal precioMin,
            @Param("precioMax") BigDecimal precioMax);

    @Query("SELECT c FROM Corte c WHERE " +
           "(:duracionMin IS NULL OR c.duracionMinutos >= :duracionMin) AND " +
           "(:duracionMax IS NULL OR c.duracionMinutos <= :duracionMax)")
    List<Corte> findByDurationRange(
            @Param("duracionMin") Integer duracionMin,
            @Param("duracionMax") Integer duracionMax);

    @Query("SELECT c FROM Corte c JOIN c.barberos b WHERE b.id = :barberoId")
    List<Corte> findByBarberoId(@Param("barberoId") Long barberoId);
}