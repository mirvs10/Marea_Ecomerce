package com.marea.Proyecto.Services;

import com.marea.Proyecto.Model.MetodoPago;
import com.marea.Proyecto.Model.Usuario;
import com.marea.Proyecto.Repository.MetodoPagoRepository;
import com.marea.Proyecto.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MetodoPagoService {

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public MetodoPago crearMetodoPago(Integer idUsuario, String nombreMetodo) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        MetodoPago metodoPago = new MetodoPago();
        metodoPago.setNombreMetodo(nombreMetodo);
        metodoPago.setUsuario(usuario);

        return metodoPagoRepository.save(metodoPago);
    }

    @Transactional(readOnly = true)
    public List<MetodoPago> obtenerMetodosPorUsuario(Integer idUsuario) {
        return metodoPagoRepository.findByUsuario_IdUsuario(idUsuario);  
    }

    @Transactional(readOnly = true)
    public MetodoPago obtenerPorId(Integer idMetodo) {
        return metodoPagoRepository.findById(idMetodo)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
    }

    @Transactional
    public MetodoPago actualizarMetodoPago(Integer idMetodo, String nuevoNombre) {
        MetodoPago metodoPago = obtenerPorId(idMetodo);
        metodoPago.setNombreMetodo(nuevoNombre);
        return metodoPagoRepository.save(metodoPago);
    }

    @Transactional
    public void eliminarMetodoPago(Integer idMetodo) {
        metodoPagoRepository.deleteById(idMetodo);
    }
}