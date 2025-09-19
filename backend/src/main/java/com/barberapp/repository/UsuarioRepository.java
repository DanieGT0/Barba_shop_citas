package com.barberapp.repository;

import com.barberapp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByOauth2IdAndOauth2Provider(String oauth2Id, String oauth2Provider);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<Usuario> findByTipoAndActivo(Usuario.TipoUsuario tipo, Boolean activo);

    @Query("SELECT u FROM Usuario u WHERE u.tipo = :tipo AND u.activo = true")
    List<Usuario> findActiveUsersByType(@Param("tipo") Usuario.TipoUsuario tipo);
}