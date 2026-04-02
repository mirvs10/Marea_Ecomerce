package com.marea.Proyecto.Controller;

import com.marea.Proyecto.Model.Usuario;
import com.marea.Proyecto.dto.UsuarioDTO;
import com.marea.Proyecto.Services.CategoriaService;
import com.marea.Proyecto.Services.PedidoService;
import com.marea.Proyecto.Services.ProductoService;
import com.marea.Proyecto.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("usuarios", usuarioService.obtenerTodosLosUsuarios());
        model.addAttribute("categorias", categoriaService.obtenerTodas());
        model.addAttribute("productos", productoService.obtenerProductosConCategoria());
        model.addAttribute("pedidos", pedidoService.obtenerTodosLosPedidos());
        model.addAttribute("nuevoUsuario", new UsuarioDTO());

        return "dashboard";
    }

    @PostMapping("/guardar-nuevo")
    public String guardarNuevo(@ModelAttribute UsuarioDTO nuevoUsuario,
                               @RequestParam("confirmarPassword") String confirmarPassword,
                               @RequestParam("tipoUsuario") String tipoUsuario,
                               Model model) {

        if (!nuevoUsuario.getPassword().equals(confirmarPassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "dashboard";
        }

        nuevoUsuario.setRol(
                "admin".equalsIgnoreCase(tipoUsuario)
                        ? Usuario.Rol.ADMIN
                        : Usuario.Rol.CLIENTE
        );

        try {
            usuarioService.registrarUsuario(nuevoUsuario);
            return "redirect:/admin/dashboard";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "dashboard";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Integer id,
                             @ModelAttribute UsuarioDTO usuarioActualizado) {

        usuarioService.actualizarUsuario(id, usuarioActualizado);
        return "redirect:/admin/dashboard";
    }
}
