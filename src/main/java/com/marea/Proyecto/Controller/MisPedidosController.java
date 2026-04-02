package com.marea.Proyecto.Controller;

import com.marea.Proyecto.Model.Pedido;
import com.marea.Proyecto.Model.Usuario;
import com.marea.Proyecto.Security.CustomUserDetails;
import com.marea.Proyecto.Services.PedidoService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mis-pedidos")
public class MisPedidosController {

    private final PedidoService pedidoService;

    public MisPedidosController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public String mostrarMisPedidos(Model model,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
            return "redirect:/login";
        }

        Usuario usuario = userDetails.getUsuario();
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorUsuario(usuario.getIdUsuario());

        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("pedidos", pedidos);

        return "mis-pedidos";
    }

    @GetMapping("/{idPedido}")
    public String verDetallePedido(@PathVariable Integer idPedido,
                                   Model model,
                                   @AuthenticationPrincipal CustomUserDetails userDetails) {

        Usuario usuario = userDetails.getUsuario();
        Pedido pedido = pedidoService.obtenerPedidoConDetalles(idPedido);

        if (!pedido.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            return "redirect:/mis-pedidos?error=No tienes permiso";
        }

        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("pedido", pedido);

        return "detalle-pedido";
    }

    @PostMapping("/{idPedido}/cancelar")
    public String cancelarPedido(@PathVariable Integer idPedido,
                                 @AuthenticationPrincipal CustomUserDetails userDetails) {

        Usuario usuario = userDetails.getUsuario();
        Pedido pedido = pedidoService.obtenerPedidoConDetalles(idPedido);

        if (!pedido.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            return "redirect:/mis-pedidos?error=No permitido";
        }

        if (!Pedido.Estado.PENDIENTE.equals(pedido.getEstado())) {
            return "redirect:/mis-pedidos?error=Solo pendientes";
        }

        pedidoService.cancelarPedido(idPedido);

        return "redirect:/mis-pedidos?success=Cancelado";
    }

    @GetMapping("/estado/{estado}")
    public String filtrarPorEstado(@PathVariable String estado,
                                   Model model,
                                   @AuthenticationPrincipal CustomUserDetails userDetails) {

        Usuario usuario = userDetails.getUsuario();

        Pedido.Estado est = Pedido.Estado.valueOf(estado.toUpperCase());

        List<Pedido> todos = pedidoService.obtenerPedidosPorUsuario(usuario.getIdUsuario());
        List<Pedido> filtrados = todos.stream()
                .filter(p -> p.getEstado().equals(est))
                .toList();

        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("pedidos", filtrados);
        model.addAttribute("estadoActual", est);

        return "mis-pedidos";
    }
}
