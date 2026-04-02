package com.marea.Proyecto.Repository;

import com.marea.Proyecto.Model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Integer> {
    List<Transaccion> findByPedidoIdPedido(Integer idPedido);
    Optional<Transaccion> findTopByPedidoIdPedidoOrderByFechaPagoDesc(Integer idPedido);
    List<Transaccion> findByEstadoPago(Transaccion.EstadoPago estado);
    List<Transaccion> findByMetodoPagoIdPago(Integer idPago);
    List<Transaccion> findByFechaPagoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    @Query("SELECT t FROM Transaccion t " +
           "JOIN FETCH t.pedido p " +
           "ORDER BY t.fechaPago DESC")
    List<Transaccion> findAllTransaccionesConPedido();
    @Query("SELECT SUM(t.monto) FROM Transaccion t " +
           "WHERE t.fechaPago BETWEEN :fechaInicio AND :fechaFin " +
           "AND t.estadoPago = :estado")
    Double calcularTotalTransaccionesExitosas(
        @Param("fechaInicio") LocalDateTime fechaInicio,
        @Param("fechaFin") LocalDateTime fechaFin,
        @Param("estado") Transaccion.EstadoPago estado
    );
    long countByEstadoPago(Transaccion.EstadoPago estado);
}