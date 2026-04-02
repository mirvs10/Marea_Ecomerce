package com.marea.Proyecto.dto;

import com.marea.Proyecto.Model.DireccionEntrega;

public class DireccionEntregaDTO {

    private Integer idDireccion;
    private String direccion;
    private String ciudad;
    private String distrito;
    private String referencia;
    private Integer idUsuario; 

    public DireccionEntregaDTO() {}

    public DireccionEntregaDTO(Integer idDireccion, String direccion, String ciudad,
                               String distrito, String referencia, Integer idUsuario) {
        this.idDireccion = idDireccion;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.distrito = distrito;
        this.referencia = referencia;
        this.idUsuario = idUsuario;
    }

    public DireccionEntregaDTO(DireccionEntrega direccionEntrega) {
        this.idDireccion = direccionEntrega.getIdDireccion();
        this.direccion = direccionEntrega.getDireccion();
        this.ciudad = direccionEntrega.getCiudad();
        this.distrito = direccionEntrega.getDistrito();
        this.referencia = direccionEntrega.getReferencia();

        this.idUsuario = (direccionEntrega.getUsuario() != null)
                ? direccionEntrega.getUsuario().getIdUsuario()
                : null;
    }

    public Integer getIdDireccion() { return idDireccion; }
    public void setIdDireccion(Integer idDireccion) { this.idDireccion = idDireccion; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getDistrito() { return distrito; }
    public void setDistrito(String distrito) { this.distrito = distrito; }

    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
}
