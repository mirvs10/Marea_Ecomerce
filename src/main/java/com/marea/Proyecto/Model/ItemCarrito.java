package com.marea.Proyecto.Model;

import com.marea.Proyecto.dto.ProductoDTO;
import java.math.BigDecimal;

public class ItemCarrito {

    private ProductoDTO producto;
    private int cantidad;

    public ItemCarrito(ProductoDTO producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getSubtotal() {
        return producto.getPrecio().multiply(BigDecimal.valueOf(cantidad));
    }
}
