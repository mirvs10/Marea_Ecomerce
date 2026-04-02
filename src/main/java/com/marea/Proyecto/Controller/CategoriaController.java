package com.marea.Proyecto.Controller;

import com.marea.Proyecto.dto.CategoriaDTO;
import com.marea.Proyecto.Services.CategoriaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categorias")
@PreAuthorize("hasRole('ADMIN')")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute CategoriaDTO dto) {
        categoriaService.crearCategoria(dto);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Integer id,
                             @ModelAttribute CategoriaDTO dto) {

        categoriaService.actualizarCategoria(id, dto);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        categoriaService.eliminarCategoria(id);
        return "redirect:/admin/dashboard";
    }
}
