package com.marea.Proyecto.Controller;

import com.marea.Proyecto.Model.*;
import com.marea.Proyecto.Security.CustomUserDetails;
import com.marea.Proyecto.Services.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    private final CarritoService carritoService;
    private final DireccionEntregaService direccionService;
    private final MetodoPagoService metodoPagoService;
    private final PedidoService pedidoService;
    private final DetallePedidoService detallePedidoService;
    private final TransaccionService transaccionService;
    private final EmailService emailService;

    public CheckoutController(CarritoService carritoService,
                              DireccionEntregaService direccionService,
                              MetodoPagoService metodoPagoService,
                              PedidoService pedidoService,
                              DetallePedidoService detallePedidoService,
                              TransaccionService transaccionService,
                              EmailService emailService) { 

        this.carritoService = carritoService;
        this.direccionService = direccionService;
        this.metodoPagoService = metodoPagoService;
        this.pedidoService = pedidoService;
        this.detallePedidoService = detallePedidoService;
        this.transaccionService = transaccionService;
        this.emailService = emailService; 
    }

    @GetMapping
    public String mostrarCheckout(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  Model model) {

        if (userDetails == null) return "redirect:/login";

        Usuario usuario = userDetails.getUsuario();
        Integer userId = usuario.getIdUsuario();

        if (carritoService.estaVacio(userId)) {
            return "redirect:/carta?error=Tu carrito está vacío";
        }

        Carrito carrito = carritoService.obtenerCarrito(userId);
        List<DireccionEntrega> direcciones = direccionService.obtenerDireccionesPorUsuario(userId);
        List<MetodoPago> metodosPago = metodoPagoService.obtenerMetodosPorUsuario(userId);

        model.addAttribute("usuario", usuario);
        model.addAttribute("carrito", carrito);
        model.addAttribute("direcciones", direcciones);
        model.addAttribute("metodosPago", metodosPago);

        return "checkout";
    }


    @PostMapping("/procesar")
        public String procesarPedido(@AuthenticationPrincipal CustomUserDetails userDetails,
                                    @RequestParam(required = false) Integer idDireccion,
                                    @RequestParam(required = false) Integer idMetodoPago,
                                    @RequestParam(required = false) String nuevaDireccion,
                                    @RequestParam(required = false) String nuevaCiudad,
                                    @RequestParam(required = false) String nuevoDistrito,
                                    @RequestParam(required = false) String nuevaReferencia,
                                    @RequestParam(required = false) String nuevoMetodoPago,
                                    Model model) {

            if (userDetails == null) return "redirect:/login";

            Usuario usuario = userDetails.getUsuario();
            Integer userId = usuario.getIdUsuario();

            if (carritoService.estaVacio(userId)) {
                return "redirect:/carta?error=Tu carrito está vacío";
            }

            try {
                Carrito carrito = carritoService.obtenerCarrito(userId);

                /** 1. DIRECCIÓN */
                Integer direccionFinal = idDireccion;

                if (nuevaDireccion != null && !nuevaDireccion.isBlank()) {
                    DireccionEntrega direccion = new DireccionEntrega();
                    direccion.setDireccion(nuevaDireccion);
                    direccion.setCiudad(nuevaCiudad);
                    direccion.setDistrito(nuevoDistrito);
                    direccion.setReferencia(nuevaReferencia);

                    direccionFinal = direccionService
                            .crearDireccion(userId, direccion)
                            .getIdDireccion();
                }

                Integer metodoPagoFinal = idMetodoPago;

                if (nuevoMetodoPago != null && !nuevoMetodoPago.isBlank()) {
                    metodoPagoFinal = metodoPagoService
                            .crearMetodoPago(userId, nuevoMetodoPago)
                            .getIdPago();
                }

                Double total = carrito.getTotal().doubleValue();
                Pedido pedido = pedidoService.crearPedido(userId, direccionFinal, total);

                for (ItemCarrito item : carrito.getItems()) {
                    detallePedidoService.crearDetalle(
                            pedido.getIdPedido(),
                            item.getProducto().getIdProducto(),
                            item.getCantidad(),
                            item.getProducto().getPrecio().doubleValue()
                    );
                }

                if (metodoPagoFinal != null) {
                    transaccionService.crearTransaccion(
                            pedido.getIdPedido(),
                            metodoPagoFinal,
                            total
                    );
                }

                        try {
                            String html = generarHtmlConfirmacionPedido(pedido, usuario, carrito);

                            emailService.enviarCorreoConfirmacion(
                                    usuario.getCorreo(),
                                    "Confirmación de Pedido - Marea Fit",
                                    html
                            );

                        } catch (Exception e) {
                            System.out.println("⚠ Error enviando correo: " + e.getMessage());
                        }

                        carritoService.vaciarCarrito(userId);

                        model.addAttribute("pedido", pedido);
                        model.addAttribute("total", total);

                        return "pedido-exitoso";

                    } catch (Exception e) {
                        e.printStackTrace();
                        return "redirect:/checkout?error=" + e.getMessage();
                    }
            }
            private String generarHtmlConfirmacionPedido(Pedido pedido, Usuario usuario, Carrito carrito) {

            StringBuilder html = new StringBuilder();

            html.append("<h2>Gracias por tu compra, ").append(usuario.getNombre()).append("!</h2>");
            html.append("<p>Tu pedido ha sido registrado correctamente.</p>");
            html.append("<h3>Detalle del Pedido:</h3>");
            html.append("<ul>");

            for (ItemCarrito item : carrito.getItems()) {
                html.append("<li>")
                    .append(item.getProducto().getNombre())
                    .append(" x ").append(item.getCantidad())
                    .append(" — S/ ").append(item.getSubtotal())
                    .append("</li>");
            }

            html.append("</ul>");
            html.append("<p><strong>Total pagado: S/ ")
                .append(carrito.getTotal())
                .append("</strong></p>");

            return html.toString();
        }



}
