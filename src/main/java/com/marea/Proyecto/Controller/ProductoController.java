package com.marea.Proyecto.Controller;

import com.marea.Proyecto.dto.ProductoDTO;
import com.marea.Proyecto.dto.ProductoConCategoriaDTO;
import com.marea.Proyecto.Services.ProductoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute ProductoDTO dto) {
        productoService.crearProducto(dto);
        return "redirect:/admin/dashboard";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Integer id,
                             @ModelAttribute ProductoDTO dto) {

        productoService.actualizarProducto(id, dto);
        return "redirect:/admin/dashboard";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {

        productoService.eliminarProducto(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/por-categoria/{idCategoria}")
    @ResponseBody
    public List<ProductoConCategoriaDTO> obtenerPorCategoria(@PathVariable Integer idCategoria) {
        return productoService.obtenerProductosPorCategoria(idCategoria);
    }
}
