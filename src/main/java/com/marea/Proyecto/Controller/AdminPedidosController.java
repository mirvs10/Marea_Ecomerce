package com.marea.Proyecto.Controller;

import com.marea.Proyecto.Model.Pedido;
import com.marea.Proyecto.Services.PedidoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/pedidos")
@PreAuthorize("hasRole('ADMIN')")
public class AdminPedidosController {

    private final PedidoService pedidoService;

    public AdminPedidosController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/cambiar-estado/{idPedido}/{nuevoEstado}")
    public String cambiarEstado(@PathVariable Integer idPedido,
                                @PathVariable String nuevoEstado) {

        try {
            Pedido.Estado estado = Pedido.Estado.valueOf(nuevoEstado.toUpperCase());
            pedidoService.cambiarEstado(idPedido, estado);

            return "redirect:/admin/dashboard?success=Estado del pedido actualizado";

        } catch (IllegalArgumentException e) {
            return "redirect:/admin/dashboard?error=Estado inválido";

        } catch (Exception e) {
            return "redirect:/admin/dashboard?error=" + e.getMessage();
        }
    }

    @GetMapping("/detalle/{idPedido}")
    public String verDetalle(@PathVariable Integer idPedido, Model model) {

        try {
            Pedido pedido = pedidoService.obtenerPedidoConDetalles(idPedido);
            model.addAttribute("pedido", pedido);

            return "admin-pedido-detalle";

        } catch (Exception e) {
            return "redirect:/admin/dashboard?error=Pedido no encontrado";
        }
    }
}
