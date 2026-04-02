package com.marea.Proyecto.Services;

import com.marea.Proyecto.Model.Carrito;
import com.marea.Proyecto.Model.ItemCarrito;
import com.marea.Proyecto.Model.Producto;
import com.marea.Proyecto.Repository.ProductoRepository;
import com.marea.Proyecto.dto.ProductoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.Optional;

@Service
public class CarritoService {

    private final ProductoRepository productoRepository;

    private final ConcurrentMap<Integer, Carrito> carritosPorUsuario = new ConcurrentHashMap<>();

    @Autowired
    public CarritoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    private ProductoDTO mapToDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(producto.getIdProducto());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setImagenUrl(producto.getImagenUrl());
        dto.setDisponible(producto.getDisponible());

        if (producto.getCategoria() != null) {
            dto.setIdCategoria(producto.getCategoria().getIdCategoria());
            dto.setNombreCategoria(producto.getCategoria().getNombre());
        }

        return dto;
    }
    public Carrito obtenerCarrito(Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId no puede ser null");
        }
        return carritosPorUsuario.computeIfAbsent(userId, id -> new Carrito());
    }

    public void agregarProducto(Integer userId, Integer idProducto, int cantidad) {
        if (userId == null) throw new IllegalArgumentException("userId no puede ser null");
        if (idProducto == null) throw new IllegalArgumentException("idProducto no puede ser null");
        if (cantidad <= 0) throw new IllegalArgumentException("cantidad debe ser mayor que 0");

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (!Boolean.TRUE.equals(producto.getDisponible())) {
            throw new RuntimeException("Producto no disponible");
        }

        ProductoDTO productoDTO = mapToDTO(producto);
        Carrito carrito = obtenerCarrito(userId);
        carrito.agregarItem(productoDTO, cantidad);

    }

    public void actualizarCantidad(Integer userId, Integer idProducto, int nuevaCantidad) {
        if (userId == null) throw new IllegalArgumentException("userId no puede ser null");
        if (idProducto == null) throw new IllegalArgumentException("idProducto no puede ser null");

        Carrito carrito = obtenerCarrito(userId);

        if (nuevaCantidad <= 0) {
            eliminarProducto(userId, idProducto);
            return;
        }

        boolean encontrado = false;
        for (ItemCarrito item : carrito.getItems()) {
            if (item.getProducto().getIdProducto().equals(idProducto)) {
                item.setCantidad(nuevaCantidad);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new RuntimeException("Producto no encontrado en el carrito");
        }
    }

    public void eliminarProducto(Integer userId, Integer idProducto) {
        if (userId == null) throw new IllegalArgumentException("userId no puede ser null");
        if (idProducto == null) throw new IllegalArgumentException("idProducto no puede ser null");

        Carrito carrito = obtenerCarrito(userId);
        carrito.eliminarItem(idProducto);
    }

    public void vaciarCarrito(Integer userId) {
        if (userId == null) throw new IllegalArgumentException("userId no puede ser null");
        carritosPorUsuario.remove(userId);
    }

    public BigDecimal obtenerTotal(Integer userId) {
        Carrito carrito = obtenerCarrito(userId);
        return carrito.getTotal();
    }

    public int obtenerCantidadItems(Integer userId) {
        Carrito carrito = obtenerCarrito(userId);
        return carrito.getTotalCantidad();
    }

    public boolean estaVacio(Integer userId) {
        Carrito carrito = obtenerCarrito(userId);
        return carrito.getItems().isEmpty();
    }

    public Optional<ItemCarrito> obtenerItem(Integer userId, Integer idProducto) {
        Carrito carrito = obtenerCarrito(userId);
        return carrito.getItems().stream()
                .filter(i -> i.getProducto().getIdProducto().equals(idProducto))
                .findFirst();
    }
}
