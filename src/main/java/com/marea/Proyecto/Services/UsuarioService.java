package com.marea.Proyecto.Services;

import com.marea.Proyecto.Model.Usuario;
import com.marea.Proyecto.Repository.UsuarioRepository;
import com.marea.Proyecto.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; 

    @Transactional
    public UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByCorreo(usuarioDTO.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setTelefono(usuarioDTO.getTelefono());
        
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        
        usuario.setRol(usuarioDTO.getRol() != null ? usuarioDTO.getRol() : Usuario.Rol.CLIENTE);
        usuario.setFechaRegistro(LocalDateTime.now());

        Usuario guardado = usuarioRepository.save(usuario);
        return convertirADTO(guardado);
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerTodos() {
        return obtenerTodosLosUsuarios();
    }

    @Transactional(readOnly = true)
    public UsuarioDTO obtenerPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirADTO(usuario);
    }

    @Transactional
    public UsuarioDTO actualizarUsuario(Integer id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioDTO.getNombre() != null && !usuarioDTO.getNombre().isEmpty()) {
            usuario.setNombre(usuarioDTO.getNombre());
        }

        if (usuarioDTO.getCorreo() != null && !usuarioDTO.getCorreo().isEmpty()) {
            if (!usuario.getCorreo().equals(usuarioDTO.getCorreo()) &&
                usuarioRepository.existsByCorreo(usuarioDTO.getCorreo())) {
                throw new RuntimeException("El correo ya está en uso");
            }
            usuario.setCorreo(usuarioDTO.getCorreo());
        }

        if (usuarioDTO.getTelefono() != null) {
            usuario.setTelefono(usuarioDTO.getTelefono());
        }

        if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        }

        if (usuarioDTO.getRol() != null) {
            usuario.setRol(usuarioDTO.getRol());
        }

        Usuario actualizado = usuarioRepository.save(usuario);
        return convertirADTO(actualizado);
    }

    @Transactional
    public void eliminarUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }


    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setCorreo(usuario.getCorreo());
        dto.setTelefono(usuario.getTelefono());
        dto.setRol(usuario.getRol());
        dto.setFechaRegistro(usuario.getFechaRegistro());
        return dto;
    }
}