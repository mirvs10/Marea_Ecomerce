package com.marea.Proyecto.Controller;

import com.marea.Proyecto.Security.CustomUserDetails;
import com.marea.Proyecto.Services.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
public class CarritoRestController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping("")
    public ResponseEntity<?> obtenerCarrito(Authentication authentication) {
        Integer userId = ((CustomUserDetails) authentication.getPrincipal()).getUsuario().getIdUsuario();
        return ResponseEntity.ok(carritoService.obtenerCarrito(userId));
    }

    @PostMapping("/agregar/{idProducto}")
    public ResponseEntity<?> agregar(
            @PathVariable Integer idProducto,
            @RequestParam(defaultValue = "1") int cantidad,
            Authentication authentication
    ) {
        Integer userId = ((CustomUserDetails) authentication.getPrincipal()).getUsuario().getIdUsuario();
        carritoService.agregarProducto(userId, idProducto, cantidad);
        return ResponseEntity.ok("Producto agregado al carrito");
    }

    @PutMapping("/actualizar/{idProducto}")
    public ResponseEntity<?> actualizar(
            @PathVariable Integer idProducto,
            @RequestParam int cantidad,
            Authentication authentication
    ) {
        Integer userId = ((CustomUserDetails) authentication.getPrincipal()).getUsuario().getIdUsuario();
        carritoService.actualizarCantidad(userId, idProducto, cantidad);
        return ResponseEntity.ok("Cantidad actualizada");
    }

    @DeleteMapping("/eliminar/{idProducto}")
    public ResponseEntity<?> eliminar(
            @PathVariable Integer idProducto,
            Authentication authentication
    ) {
        Integer userId = ((CustomUserDetails) authentication.getPrincipal()).getUsuario().getIdUsuario();
        carritoService.eliminarProducto(userId, idProducto);
        return ResponseEntity.ok("Producto eliminado");
    }

    @DeleteMapping("/vaciar")
    public ResponseEntity<?> vaciar(Authentication authentication) {
        Integer userId = ((CustomUserDetails) authentication.getPrincipal()).getUsuario().getIdUsuario();
        carritoService.vaciarCarrito(userId);
        return ResponseEntity.ok("Carrito vaciado");
    }
}
