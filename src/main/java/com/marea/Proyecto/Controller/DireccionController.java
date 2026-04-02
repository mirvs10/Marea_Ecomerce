package com.marea.Proyecto.Controller;

import com.marea.Proyecto.Model.DireccionEntrega;
import com.marea.Proyecto.Model.Usuario;
import com.marea.Proyecto.Security.CustomUserDetails;
import com.marea.Proyecto.Services.DireccionEntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mis-direcciones")
public class DireccionController {

    @Autowired
    private DireccionEntregaService direccionService;


    @GetMapping
    public String mostrarDirecciones(Model model, Authentication authentication) {

        Usuario usuario = ((CustomUserDetails) authentication.getPrincipal()).getUsuario();

        List<DireccionEntrega> direcciones =
                direccionService.obtenerDireccionesPorUsuario(usuario.getIdUsuario());

        model.addAttribute("usuario", usuario);
        model.addAttribute("direcciones", direcciones);

        return "mis-direcciones";
    }


    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model, Authentication authentication) {

        Usuario usuario = ((CustomUserDetails) authentication.getPrincipal()).getUsuario();

        model.addAttribute("usuario", usuario);
        model.addAttribute("direccion", new DireccionEntrega());

        return "nueva-direccion";
    }


    @PostMapping("/guardar")
    public String guardarDireccion(@ModelAttribute DireccionEntrega direccion,
                                   Authentication authentication) {

        Usuario usuario = ((CustomUserDetails) authentication.getPrincipal()).getUsuario();

        direccionService.crearDireccion(usuario.getIdUsuario(), direccion);

        return "redirect:/mis-direcciones?success=Dirección agregada exitosamente";
    }


    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id,
                                          Model model,
                                          Authentication authentication) {

        Usuario usuario = ((CustomUserDetails) authentication.getPrincipal()).getUsuario();

        DireccionEntrega direccion = direccionService.obtenerPorId(id);

        if (!direccion.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            return "redirect:/mis-direcciones?error=No tienes permiso";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("direccion", direccion);

        return "editar-direccion";
    }


    @PostMapping("/actualizar/{id}")
    public String actualizarDireccion(@PathVariable Integer id,
                                      @ModelAttribute DireccionEntrega direccion,
                                      Authentication authentication) {

        Usuario usuario = ((CustomUserDetails) authentication.getPrincipal()).getUsuario();

        DireccionEntrega existente = direccionService.obtenerPorId(id);

        if (!existente.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            return "redirect:/mis-direcciones?error=No tienes permiso";
        }

        direccionService.actualizarDireccion(id, direccion);

        return "redirect:/mis-direcciones?success=Dirección actualizada";
    }


    @PostMapping("/eliminar/{id}")
    public String eliminarDireccion(@PathVariable Integer id,
                                    Authentication authentication) {

        Usuario usuario = ((CustomUserDetails) authentication.getPrincipal()).getUsuario();

        DireccionEntrega direccion = direccionService.obtenerPorId(id);

        if (!direccion.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            return "redirect:/mis-direcciones?error=No tienes permiso";
        }

        direccionService.eliminarDireccion(id);

        return "redirect:/mis-direcciones?success=Dirección eliminada";
    }
}
