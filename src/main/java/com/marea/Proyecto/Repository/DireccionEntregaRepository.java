package com.marea.Proyecto.Repository;

import com.marea.Proyecto.Model.DireccionEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DireccionEntregaRepository extends JpaRepository<DireccionEntrega, Integer> {

    List<DireccionEntrega> findByUsuario_IdUsuario(Integer idUsuario);

    boolean existsByUsuario_IdUsuario(Integer idUsuario);

    long countByUsuario_IdUsuario(Integer idUsuario);

    List<DireccionEntrega> findByDistritoContainingIgnoreCase(String distrito);

    List<DireccionEntrega> findByCiudadContainingIgnoreCase(String ciudad);

    @Query("SELECT d FROM DireccionEntrega d " +
           "JOIN FETCH d.usuario u " +
           "WHERE u.idUsuario = :idUsuario")
    List<DireccionEntrega> findDireccionesConUsuario(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT d FROM DireccionEntrega d " +
           "WHERE d.usuario.idUsuario = :idUsuario " +
           "ORDER BY d.idDireccion ASC")
    Optional<DireccionEntrega> findPrimeraDireccionPorUsuario(@Param("idUsuario") Integer idUsuario);
}
