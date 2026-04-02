package com.marea.Proyecto.Config;

import com.marea.Proyecto.Model.Usuario;
import com.marea.Proyecto.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!usuarioRepository.existsByCorreo("admin@marea.com")) {
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setCorreo("admin@marea.com");
            admin.setPassword(passwordEncoder.encode("admin123"));  
            admin.setTelefono("999999999");
            admin.setRol(Usuario.Rol.ADMIN);
            admin.setFechaRegistro(LocalDateTime.now());

            usuarioRepository.save(admin);
        }

        if (!usuarioRepository.existsByCorreo("cliente@marea.com")) {
            Usuario cliente = new Usuario();
            cliente.setNombre("Cliente Prueba");
            cliente.setCorreo("cliente@marea.com");
            cliente.setPassword(passwordEncoder.encode("cliente123")); 
            cliente.setTelefono("988888888");
            cliente.setRol(Usuario.Rol.CLIENTE);
            cliente.setFechaRegistro(LocalDateTime.now());

            usuarioRepository.save(cliente);
        }
    }
}