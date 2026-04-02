package com.marea.Proyecto.dto;

import com.marea.Proyecto.Model.DetallePedido;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DetallePedidoDTO {

    private Integer idDetalle;

    @NotNull(message = "El ID del pedido es obligatorio")
    private Integer idPedido;

    @NotNull(message = "El ID del producto es obligatorio")
    private Integer idProducto;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double precioUnitario;

    private String nombreProducto;
    private String imagenProducto;
    private String categoriaProducto;

    private Double subtotal;

    public DetallePedidoDTO() {}

    public DetallePedidoDTO(DetallePedido detalle) {
        this.idDetalle = detalle.getIdDetalle();
        this.idPedido = detalle.getPedido() != null ? detalle.getPedido().getIdPedido() : null;
        this.idProducto = detalle.getProducto() != null ? detalle.getProducto().getIdProducto() : null;
        this.cantidad = detalle.getCantidad();
        this.precioUnitario = detalle.getPrecioUnitario();

        if (detalle.getProducto() != null) {
            this.nombreProducto = detalle.getProducto().getNombre();
            this.imagenProducto = detalle.getProducto().getImagenUrl();
            this.categoriaProducto = detalle.getProducto().getCategoria() != null
                    ? detalle.getProducto().getCategoria().getNombre()
                    : null;
        }

        this.subtotal = this.cantidad * this.precioUnitario;
    }

    public DetallePedidoDTO(Integer idPedido, Integer idProducto, Integer cantidad, Double precioUnitario) {
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
        if (this.precioUnitario != null) {
            this.subtotal = this.cantidad * this.precioUnitario;
        }
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        if (this.precioUnitario != null) {
            this.subtotal = cantidad * this.precioUnitario;
        }
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
        if (this.cantidad != null) {
            this.subtotal = this.cantidad * precioUnitario;
        }
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public String getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(String categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    public Double getSubtotal() {
        if (cantidad != null && precioUnitario != null) {
            return cantidad * precioUnitario;
        }
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "DetallePedidoDTO{" +
                "idDetalle=" + idDetalle +
                ", idPedido=" + idPedido +
                ", idProducto=" + idProducto +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + getSubtotal() +
                '}';
    }
}
