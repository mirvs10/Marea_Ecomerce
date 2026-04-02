package com.marea.Proyecto.Controller;

import com.marea.Proyecto.dto.CategoriaDTO;
import com.marea.Proyecto.dto.ProductoConCategoriaDTO;
import com.marea.Proyecto.Services.CategoriaService;
import com.marea.Proyecto.Services.ProductoService;
import com.marea.Proyecto.Security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/carta")
public class CartaController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public CartaController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String mostrarCarta(Model model,
                               @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails != null)
            model.addAttribute("usuarioLogueado", userDetails.getUsuario());

        List<CategoriaDTO> categorias = categoriaService.obtenerTodas();
        List<ProductoConCategoriaDTO> productos = productoService.obtenerProductosConCategoria()
                .stream()
                .filter(ProductoConCategoriaDTO::getDisponible)
                .toList();

        model.addAttribute("categorias", categorias);
        model.addAttribute("productos", productos);
        model.addAttribute("categoriaSeleccionada", null);

        return "carta";
    }

    @GetMapping("/categoria/{idCategoria}")
    public String filtrarPorCategoria(@PathVariable Integer idCategoria,
                                      Model model,
                                      @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails != null)
            model.addAttribute("usuarioLogueado", userDetails.getUsuario());

        List<CategoriaDTO> categorias = categoriaService.obtenerTodas();
        List<ProductoConCategoriaDTO> productos = productoService.obtenerProductosPorCategoria(idCategoria)
                .stream()
                .filter(ProductoConCategoriaDTO::getDisponible)
                .toList();

        model.addAttribute("categorias", categorias);
        model.addAttribute("productos", productos);
        model.addAttribute("categoriaSeleccionada", categoriaService.obtenerPorId(idCategoria));

        return "carta";
    }

    @GetMapping("/buscar")
    public String buscarProductos(@RequestParam(required = false) String termino,
                                  @RequestParam(required = false) Integer idCategoria,
                                  Model model,
                                  @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails != null)
            model.addAttribute("usuarioLogueado", userDetails.getUsuario());

        List<CategoriaDTO> categorias = categoriaService.obtenerTodas();
        List<ProductoConCategoriaDTO> productos;

        if (termino != null && !termino.trim().isEmpty()) {
            productos = productoService.buscarProductos(termino.trim(), idCategoria);
        } else if (idCategoria != null) {
            productos = productoService.obtenerProductosPorCategoria(idCategoria);
        } else {
            productos = productoService.obtenerProductosConCategoria();
        }

        productos = productos.stream()
                .filter(ProductoConCategoriaDTO::getDisponible)
                .toList();

        model.addAttribute("categorias", categorias);
        model.addAttribute("productos", productos);
        model.addAttribute("terminoBusqueda", termino);
        model.addAttribute("categoriaSeleccionada",
                idCategoria != null ? categoriaService.obtenerPorId(idCategoria) : null);

        return "carta";
    }
}
