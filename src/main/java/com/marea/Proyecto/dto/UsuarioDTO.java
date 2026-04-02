package com.marea.Proyecto.dto;

import com.marea.Proyecto.Model.Usuario;
import java.time.LocalDateTime;

public class UsuarioDTO {

    private Integer idUsuario;
    private String nombre;
    private String correo;
    private String password; 
    private String telefono;
    private Usuario.Rol rol;
    private LocalDateTime fechaRegistro;

    public UsuarioDTO() {}

    public UsuarioDTO(Integer idUsuario, String nombre, String correo, String password,
                      String telefono, Usuario.Rol rol, LocalDateTime fechaRegistro) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.telefono = telefono;
        this.rol = rol;
        this.fechaRegistro = fechaRegistro;
    }

    public UsuarioDTO(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.nombre = usuario.getNombre();
        this.correo = usuario.getCorreo();
        this.telefono = usuario.getTelefono();
        this.rol = usuario.getRol();
        this.fechaRegistro = usuario.getFechaRegistro();
        this.password = usuario.getPassword();
    }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Usuario.Rol getRol() { return rol; }
    public void setRol(Usuario.Rol rol) { this.rol = rol; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
