package com.marea.Proyecto.Repository;

import com.marea.Proyecto.Model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByUsuarioIdUsuario(Integer idUsuario);
    
    List<Pedido> findByUsuarioIdUsuarioOrderByFechaPedidoDesc(Integer idUsuario);
    
    List<Pedido> findByEstado(Pedido.Estado estado);
    
    List<Pedido> findByUsuarioIdUsuarioAndEstado(Integer idUsuario, Pedido.Estado estado);
    
    List<Pedido> findByFechaPedidoBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    @Query("SELECT p FROM Pedido p WHERE p.fechaPedido >= :fechaDesde " +
           "ORDER BY p.fechaPedido DESC")
    List<Pedido> findPedidosRecientes(@Param("fechaDesde") LocalDateTime fechaDesde);
    
    @Query("SELECT p FROM Pedido p " +
           "JOIN FETCH p.usuario u " +
           "ORDER BY p.fechaPedido DESC")
    List<Pedido> findAllPedidosConUsuario();
    
    @Query("SELECT p FROM Pedido p " +
           "LEFT JOIN FETCH p.detalles " +
           "WHERE p.idPedido = :idPedido")
    Pedido findPedidoConDetalles(@Param("idPedido") Integer idPedido);
    
    @Query("SELECT SUM(p.total) FROM Pedido p " +
           "WHERE p.fechaPedido BETWEEN :fechaInicio AND :fechaFin " +
           "AND p.estado != 'CANCELADO'")
    Double calcularTotalVentasPorPeriodo(
        @Param("fechaInicio") LocalDateTime fechaInicio, 
        @Param("fechaFin") LocalDateTime fechaFin
    );
    
    long countByEstado(Pedido.Estado estado);
    
    long countByUsuarioIdUsuario(Integer idUsuario);
}