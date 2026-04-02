package com.marea.Proyecto.Services;

import com.marea.Proyecto.Model.Pedido;
import com.marea.Proyecto.Model.Usuario;
import com.marea.Proyecto.Model.DireccionEntrega;
import com.marea.Proyecto.Repository.PedidoRepository;
import com.marea.Proyecto.Repository.UsuarioRepository;
import com.marea.Proyecto.Repository.DireccionEntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private DireccionEntregaRepository direccionRepository;

    @Transactional
    public Pedido crearPedido(Integer idUsuario, Integer idDireccion, Double total) {
        
        try {
            Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            DireccionEntrega direccion = null;
            if (idDireccion != null) {
                direccion = direccionRepository.findById(idDireccion).orElse(null);
            }
            
            Pedido pedido = new Pedido();
            pedido.setUsuario(usuario);
            pedido.setDireccion(direccion);
            pedido.setFechaPedido(LocalDateTime.now());  
            pedido.setEstado(Pedido.Estado.PENDIENTE);
            pedido.setTotal(total);
            
            Pedido guardado = pedidoRepository.save(pedido);
            
            return guardado;
            
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Pedido> obtenerPedidosPorUsuario(Integer idUsuario) {
        return pedidoRepository.findByUsuarioIdUsuarioOrderByFechaPedidoDesc(idUsuario);
    }

    @Transactional(readOnly = true)
    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoRepository.findAllPedidosConUsuario();
    }

    @Transactional(readOnly = true)
    public Pedido obtenerPedidoConDetalles(Integer idPedido) {
        return pedidoRepository.findPedidoConDetalles(idPedido);
    }

    @Transactional
    public Pedido cambiarEstado(Integer idPedido, Pedido.Estado nuevoEstado) {
        
        Pedido pedido = pedidoRepository.findById(idPedido)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setEstado(nuevoEstado);
        Pedido actualizado = pedidoRepository.save(pedido);
        
        return actualizado;
    }

    @Transactional(readOnly = true)
    public List<Pedido> obtenerPedidosPorEstado(Pedido.Estado estado) {
        return pedidoRepository.findByEstado(estado);
    }

    @Transactional(readOnly = true)
    public Double calcularVentasDelDia() {
        LocalDateTime inicioDia = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime finDia = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        
        Double total = pedidoRepository.calcularTotalVentasPorPeriodo(inicioDia, finDia);
        return total;
    }

    @Transactional
    public Pedido cancelarPedido(Integer idPedido) {
        return cambiarEstado(idPedido, Pedido.Estado.CANCELADO);
    }

    @Transactional
    public Pedido marcarComoEnviado(Integer idPedido) {
        return cambiarEstado(idPedido, Pedido.Estado.ENVIADO);
    }

    @Transactional
    public Pedido marcarComoEntregado(Integer idPedido) {
        return cambiarEstado(idPedido, Pedido.Estado.ENTREGADO);
    }
}