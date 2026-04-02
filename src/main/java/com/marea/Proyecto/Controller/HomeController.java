package com.marea.Proyecto.Controller;

import com.marea.Proyecto.dto.UsuarioDTO;
import com.marea.Proyecto.Model.Usuario;
import com.marea.Proyecto.Services.UsuarioService;
import com.marea.Proyecto.Security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UsuarioService usuarioService;

    private void agregarUsuario(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails != null) {
            model.addAttribute("usuarioLogueado", userDetails.getUsuario());
        }
    }

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        agregarUsuario(model, user);
        return "index";
    }

    @GetMapping("/nosotros")
    public String nosotros(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        agregarUsuario(model, user);
        return "nosotros";
    }

    @GetMapping("/contacto")
    public String contacto(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        agregarUsuario(model, user);
        return "contacto";
    }

    @GetMapping("/menu")
    public String menu(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        agregarUsuario(model, user);
        return "index";
    }

    @GetMapping("/pedido")
    public String pedido(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        agregarUsuario(model, user);
        return "index";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; 
    }

    @GetMapping("/register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new UsuarioDTO());
        return "register";
    }

    @PostMapping("/register")
    public String procesarRegistro(
            @ModelAttribute UsuarioDTO usuarioDTO,
            @RequestParam("confirmarPassword") String confirmarPassword,
            Model model) {

        if (!usuarioDTO.getPassword().equals(confirmarPassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "register";
        }

        try {
            usuarioDTO.setRol(Usuario.Rol.CLIENTE);
            usuarioService.registrarUsuario(usuarioDTO);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
