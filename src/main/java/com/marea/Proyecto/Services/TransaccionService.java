package com.marea.Proyecto.Services;

import com.marea.Proyecto.Model.Transaccion;
import com.marea.Proyecto.Model.Pedido;
import com.marea.Proyecto.Model.MetodoPago;
import com.marea.Proyecto.Repository.TransaccionRepository;
import com.marea.Proyecto.Repository.PedidoRepository;
import com.marea.Proyecto.Repository.MetodoPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Transactional
    public Transaccion crearTransaccion(Integer idPedido, Integer idMetodoPago, Double monto) {
        try {
            Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
            MetodoPago metodoPago = null;
            if (idMetodoPago != null) {
                metodoPago = metodoPagoRepository.findById(idMetodoPago).orElse(null);
            }

            Transaccion transaccion = new Transaccion();
            transaccion.setPedido(pedido);
            transaccion.setMetodoPago(metodoPago);
            transaccion.setMonto(monto);
            transaccion.setFechaPago(LocalDateTime.now());  
            transaccion.setEstadoPago(Transaccion.EstadoPago.PENDIENTE);  

            Transaccion guardada = transaccionRepository.save(transaccion);

            return guardada;

        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Transaccion> obtenerTransaccionesPorPedido(Integer idPedido) {
        return transaccionRepository.findByPedidoIdPedido(idPedido);  
    }

    @Transactional
    public Transaccion cambiarEstado(Integer idTransaccion, Transaccion.EstadoPago nuevoEstado) {

        Transaccion transaccion = transaccionRepository.findById(idTransaccion)
            .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        transaccion.setEstadoPago(nuevoEstado);  

        return transaccionRepository.save(transaccion);
    }

    @Transactional
    public Transaccion marcarComoExitosa(Integer idTransaccion) {
        return cambiarEstado(idTransaccion, Transaccion.EstadoPago.COMPLETADO);
    }

    @Transactional
    public Transaccion marcarComoFallida(Integer idTransaccion) {
        return cambiarEstado(idTransaccion, Transaccion.EstadoPago.FALLIDO);
    }

    @Transactional(readOnly = true)
    public List<Transaccion> obtenerTodasLasTransacciones() {
        return transaccionRepository.findAllTransaccionesConPedido();
    }
}