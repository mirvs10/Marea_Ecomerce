package com.marea.Proyecto.Repository;

import com.marea.Proyecto.Model.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {

    List<MetodoPago> findByUsuario_IdUsuario(Integer idUsuario);

    boolean existsByUsuario_IdUsuario(Integer idUsuario);

    long countByUsuario_IdUsuario(Integer idUsuario);

    @Query("SELECT m FROM MetodoPago m WHERE m.usuario.idUsuario = :idUsuario ORDER BY m.idPago DESC")
    Optional<MetodoPago> findPrimerMetodoPorUsuario(@Param("idUsuario") Integer idUsuario);

    List<MetodoPago> findByNombreMetodo(String nombreMetodo);
}