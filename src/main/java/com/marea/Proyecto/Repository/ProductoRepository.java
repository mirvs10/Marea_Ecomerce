package com.marea.Proyecto.Repository;

import com.marea.Proyecto.Model.Producto;
import com.marea.Proyecto.dto.ProductoConCategoriaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    Optional<Producto> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
    @Query("""
        SELECT new com.marea.Proyecto.dto.ProductoConCategoriaDTO(
            p.idProducto, p.nombre, p.descripcion, p.precio, p.imagenUrl, p.disponible,
            c.idCategoria, c.nombre, c.descripcion
        )
        FROM Producto p
        INNER JOIN p.categoria c
    """)
    List<ProductoConCategoriaDTO> obtenerProductosConCategoria();
    @Query("""
        SELECT new com.marea.Proyecto.dto.ProductoConCategoriaDTO(
            p.idProducto, p.nombre, p.descripcion, p.precio, p.imagenUrl, p.disponible,
            c.idCategoria, c.nombre, c.descripcion
        )
        FROM Producto p
        INNER JOIN p.categoria c
        WHERE c.idCategoria = :idCategoria
    """)
    List<ProductoConCategoriaDTO> obtenerProductosPorCategoria(
            @Param("idCategoria") Integer idCategoria);
    @Query("""
        SELECT new com.marea.Proyecto.dto.ProductoConCategoriaDTO(
            p.idProducto, p.nombre, p.descripcion, p.precio, p.imagenUrl, p.disponible,
            c.idCategoria, c.nombre, c.descripcion
        )
        FROM Producto p
        INNER JOIN p.categoria c
        WHERE p.disponible = true
    """)
    List<ProductoConCategoriaDTO> obtenerProductosDisponiblesConCategoria();
    @Query("""
        SELECT new com.marea.Proyecto.dto.ProductoConCategoriaDTO(
            p.idProducto, p.nombre, p.descripcion, p.precio, p.imagenUrl, p.disponible,
            c.idCategoria, c.nombre, c.descripcion
        )
        FROM Producto p
        INNER JOIN p.categoria c
        WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :termino, '%'))
        AND (:idCategoria IS NULL OR c.idCategoria = :idCategoria)
    """)
    List<ProductoConCategoriaDTO> buscarProductos(
            @Param("termino") String termino,
            @Param("idCategoria") Integer idCategoria);
}
