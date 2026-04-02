package com.marea.Proyecto.Repository;

import com.marea.Proyecto.Model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

    List<DetallePedido> findByPedido_IdPedido(Integer idPedido);

    List<DetallePedido> findByProducto_IdProducto(Integer idProducto);

    @Query("SELECT d FROM DetallePedido d " +
           "JOIN FETCH d.producto p " +
           "WHERE d.pedido.idPedido = :idPedido")
    List<DetallePedido> findDetallesConProductoPorPedido(@Param("idPedido") Integer idPedido);

    @Query("SELECT SUM(d.precioUnitario * d.cantidad) FROM DetallePedido d " +
           "WHERE d.pedido.idPedido = :idPedido")
    Double calcularTotalPedido(@Param("idPedido") Integer idPedido);

    @Query("SELECT COUNT(d) FROM DetallePedido d WHERE d.pedido.idPedido = :idPedido")
    long contarProductosPorPedido(@Param("idPedido") Integer idPedido);
}
