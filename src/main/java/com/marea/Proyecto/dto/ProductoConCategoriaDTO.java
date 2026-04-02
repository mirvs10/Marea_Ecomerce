package com.marea.Proyecto.dto;

import java.math.BigDecimal;

public class ProductoConCategoriaDTO {

    private Integer idProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private BigDecimal precio;
    private String imagenUrl;
    private Boolean disponible;

    private Integer idCategoria;
    private String nombreCategoria;
    private String descripcionCategoria;

    public ProductoConCategoriaDTO(Integer idProducto, String nombreProducto, String descripcionProducto,
                                   BigDecimal precio, String imagenUrl, Boolean disponible,
                                   Integer idCategoria, String nombreCategoria, String descripcionCategoria) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.precio = precio;
        this.imagenUrl = imagenUrl;
        this.disponible = disponible;
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.descripcionCategoria = descripcionCategoria;
    }

    public ProductoConCategoriaDTO() {}

    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public String getDescripcionProducto() { return descripcionProducto; }
    public void setDescripcionProducto(String descripcionProducto) { this.descripcionProducto = descripcionProducto; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }

    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }

    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }

    public String getDescripcionCategoria() { return descripcionCategoria; }
    public void setDescripcionCategoria(String descripcionCategoria) { this.descripcionCategoria = descripcionCategoria; }
}
