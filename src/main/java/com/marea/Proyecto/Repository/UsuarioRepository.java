package com.marea.Proyecto.Repository;

import com.marea.Proyecto.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    List<Usuario> findByRol(Usuario.Rol rol);
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
}
