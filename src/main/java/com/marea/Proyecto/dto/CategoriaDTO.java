package com.marea.Proyecto.dto;

import com.marea.Proyecto.Model.Categoria;

public class CategoriaDTO {

    private Integer idCategoria;
    private String nombre;
    private String descripcion;
    private Integer cantidadProductos;

    public CategoriaDTO() {}

    public CategoriaDTO(Categoria categoria) {
        this.idCategoria = categoria.getIdCategoria();
        this.nombre = categoria.getNombre();
        this.descripcion = categoria.getDescripcion();
        this.cantidadProductos = categoria.getProductos() != null 
                                 ? categoria.getProductos().size() 
                                 : 0;
    }

    public CategoriaDTO(Integer idCategoria, String nombre, String descripcion) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getCantidadProductos() { return cantidadProductos; }
    public void setCantidadProductos(Integer cantidadProductos) { this.cantidadProductos = cantidadProductos; }
}
