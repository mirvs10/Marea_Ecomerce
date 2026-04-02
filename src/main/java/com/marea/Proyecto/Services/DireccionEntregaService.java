package com.marea.Proyecto.Services;

import com.marea.Proyecto.Model.DireccionEntrega;
import com.marea.Proyecto.Model.Usuario;
import com.marea.Proyecto.Repository.DireccionEntregaRepository;
import com.marea.Proyecto.Repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DireccionEntregaService {

    @Autowired
    private DireccionEntregaRepository direccionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public DireccionEntrega crearDireccion(Integer idUsuario, DireccionEntrega direccion) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        direccion.setUsuario(usuario);
        DireccionEntrega guardada = direccionRepository.save(direccion);

        return guardada;
    }

    @Transactional(readOnly = true)
    public List<DireccionEntrega> obtenerDireccionesPorUsuario(Integer idUsuario) {
        return direccionRepository.findByUsuario_IdUsuario(idUsuario);
    }

    @Transactional
    public DireccionEntrega actualizarDireccion(Integer idDireccion, DireccionEntrega nuevaData) {

        DireccionEntrega direccion = direccionRepository.findById(idDireccion)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));

        if (nuevaData.getDireccion() != null) direccion.setDireccion(nuevaData.getDireccion());
        if (nuevaData.getCiudad() != null) direccion.setCiudad(nuevaData.getCiudad());
        if (nuevaData.getDistrito() != null) direccion.setDistrito(nuevaData.getDistrito());
        if (nuevaData.getReferencia() != null) direccion.setReferencia(nuevaData.getReferencia());

        return direccionRepository.save(direccion);
    }

    @Transactional
    public void eliminarDireccion(Integer idDireccion) {
        direccionRepository.deleteById(idDireccion);
    }

    @Transactional(readOnly = true)
    public DireccionEntrega obtenerPorId(Integer idDireccion) {
        return direccionRepository.findById(idDireccion)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));
    }
}
