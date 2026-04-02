package com.marea.Proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MetodoPagoDTO {
    
    private Integer idPago;
    
    @NotNull(message = "El ID de usuario es obligatorio")
    private Integer idUsuario;
    
    @NotBlank(message = "El nombre del método es obligatorio")
    private String nombreMetodo;
    
    private String nombreUsuario; 

    // Constructores
    public MetodoPagoDTO() {}

    // Getters y Setters
    public Integer getIdPago() { return idPago; }
    public void setIdPago(Integer idPago) { this.idPago = idPago; }
    
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    
    public String getNombreMetodo() { return nombreMetodo; }
    public void setNombreMetodo(String nombreMetodo) { this.nombreMetodo = nombreMetodo; }
    
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
}