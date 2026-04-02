package com.marea.Proyecto.Services;

import com.marea.Proyecto.Model.DetallePedido;
import com.marea.Proyecto.Model.Pedido;
import com.marea.Proyecto.Model.Producto;
import com.marea.Proyecto.Repository.DetallePedidoRepository;
import com.marea.Proyecto.Repository.PedidoRepository;
import com.marea.Proyecto.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public DetallePedido crearDetalle(Integer idPedido, Integer idProducto, Integer cantidad, Double precioUnitario) {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        DetallePedido detalle = new DetallePedido();
        detalle.setPedido(pedido);
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(precioUnitario);

       
        detalle.setSubtotal(precioUnitario * cantidad);

        DetallePedido guardado = detallePedidoRepository.save(detalle);

        return guardado;
    }

    @Transactional(readOnly = true)
    public List<DetallePedido> obtenerDetallesPorPedido(Integer idPedido) {
        return detallePedidoRepository.findByPedido_IdPedido(idPedido);
    }

    @Transactional(readOnly = true)
    public List<DetallePedido> obtenerDetallesConProducto(Integer idPedido) {
        return detallePedidoRepository.findDetallesConProductoPorPedido(idPedido);
    }

    @Transactional(readOnly = true)
    public Double calcularTotal(Integer idPedido) {
        Double total = detallePedidoRepository.calcularTotalPedido(idPedido);
        return total != null ? total : 0.0;
    }
}
