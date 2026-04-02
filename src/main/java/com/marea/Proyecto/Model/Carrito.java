package com.marea.Proyecto.Model;

import com.marea.Proyecto.dto.ProductoDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Carrito {

    private List<ItemCarrito> items = new ArrayList<>();

    public List<ItemCarrito> getItems() {
        return items;
    }

    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }

    public void agregarItem(ProductoDTO producto, int cantidad) {

        for (ItemCarrito item : items) {
            if (item.getProducto().getIdProducto().equals(producto.getIdProducto())) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }

        items.add(new ItemCarrito(producto, cantidad));
    }
 
    public void eliminarItem(Integer idProducto) {
        items.removeIf(item -> item.getProducto().getIdProducto().equals(idProducto));
    }
 
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemCarrito item : items) {
            total = total.add(item.getSubtotal());
        }
        return total;
    }

    public int getTotalCantidad() {
        return items.stream()
                .mapToInt(ItemCarrito::getCantidad)
                .sum();
    }

    // Limpiar
    public void limpiar() {
        items.clear();
    }
}
