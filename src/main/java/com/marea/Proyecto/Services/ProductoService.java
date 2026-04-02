package com.marea.Proyecto.Services;

import com.marea.Proyecto.dto.ProductoDTO;
import com.marea.Proyecto.dto.ProductoConCategoriaDTO;
import com.marea.Proyecto.Model.Producto;
import com.marea.Proyecto.Model.Categoria;
import com.marea.Proyecto.Repository.ProductoRepository;
import com.marea.Proyecto.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public ProductoDTO crearProducto(ProductoDTO dto) {

        if (productoRepository.existsByNombre(dto.getNombre())) {
            throw new RuntimeException("El producto ya existe");
        }

        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setImagenUrl(dto.getImagenUrl());
        producto.setDisponible(dto.getDisponible() != null ? dto.getDisponible() : true);
        producto.setCategoria(categoria);

        Producto guardado = productoRepository.save(producto);
        return convertirADTO(guardado);
    }

    @Transactional(readOnly = true)
    public List<ProductoDTO> obtenerTodos() {
        return productoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductoConCategoriaDTO> obtenerProductosConCategoria() {
        return productoRepository.obtenerProductosConCategoria();
    }

    @Transactional(readOnly = true)
    public List<ProductoConCategoriaDTO> obtenerProductosPorCategoria(Integer idCategoria) {
        return productoRepository.obtenerProductosPorCategoria(idCategoria);
    }

    @Transactional(readOnly = true)
    public List<ProductoConCategoriaDTO> obtenerProductosDisponibles() {
        return productoRepository.obtenerProductosDisponiblesConCategoria();
    }

    @Transactional(readOnly = true)
    public List<ProductoConCategoriaDTO> buscarProductos(String termino, Integer idCategoria) {
        return productoRepository.buscarProductos(termino, idCategoria);
    }

    @Transactional(readOnly = true)
    public ProductoDTO obtenerPorId(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return convertirADTO(producto);
    }

    @Transactional
    public ProductoDTO actualizarProducto(Integer id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (dto.getNombre() != null && !dto.getNombre().isEmpty()) {
            if (!dto.getNombre().equals(producto.getNombre()) &&
                    productoRepository.existsByNombre(dto.getNombre())) {
                throw new RuntimeException("El nombre ya está en uso");
            }
            producto.setNombre(dto.getNombre());
        }

        if (dto.getDescripcion() != null)
            producto.setDescripcion(dto.getDescripcion());

        if (dto.getPrecio() != null)
            producto.setPrecio(dto.getPrecio());

        if (dto.getImagenUrl() != null)
            producto.setImagenUrl(dto.getImagenUrl());

        if (dto.getDisponible() != null)
            producto.setDisponible(dto.getDisponible());

        if (dto.getIdCategoria() != null &&
                !dto.getIdCategoria().equals(producto.getCategoria().getIdCategoria())) {
            Categoria nueva = categoriaRepository.findById(dto.getIdCategoria())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            producto.setCategoria(nueva);
        }

        Producto actualizado = productoRepository.save(producto);
        return convertirADTO(actualizado);
    }

    @Transactional
    public void eliminarProducto(Integer id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }

    private ProductoDTO convertirADTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(producto.getIdProducto());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setImagenUrl(producto.getImagenUrl());
        dto.setDisponible(producto.getDisponible());
        dto.setIdCategoria(producto.getCategoria().getIdCategoria());
        dto.setNombreCategoria(producto.getCategoria().getNombre());
        return dto;
    }
}
