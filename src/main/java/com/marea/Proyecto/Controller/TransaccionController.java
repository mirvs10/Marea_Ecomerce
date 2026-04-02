package com.marea.Proyecto.Controller;

import com.marea.Proyecto.dto.TransaccionDTO;
import com.marea.Proyecto.Model.Transaccion;
import com.marea.Proyecto.Services.TransaccionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
@CrossOrigin(origins = "*")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    private TransaccionDTO convertirADTO(Transaccion entidad) {

        TransaccionDTO dto = new TransaccionDTO();
        dto.setIdTransaccion(entidad.getIdTransaccion());
        dto.setMonto(entidad.getMonto());
        dto.setFechaPago(entidad.getFechaPago());
        dto.setEstadoPago(entidad.getEstadoPago() != null ? entidad.getEstadoPago().name() : null);

        if (entidad.getPedido() != null) {
            dto.setIdPedido(entidad.getPedido().getIdPedido());
        }

        if (entidad.getMetodoPago() != null) {
            dto.setIdMetodoPago(entidad.getMetodoPago().getIdPago());
            dto.setNombreMetodoPago(entidad.getMetodoPago().getNombreMetodo());
        }

        return dto;
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody TransaccionDTO dto) {

        Transaccion t = transaccionService.crearTransaccion(
                dto.getIdPedido(),
                dto.getIdMetodoPago(),
                dto.getMonto()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(t));
    }

    @GetMapping
    public ResponseEntity<List<TransaccionDTO>> obtenerTodas() {

        List<TransaccionDTO> lista = transaccionService.obtenerTodasLasTransacciones()
                .stream()
                .map(this::convertirADTO)
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<TransaccionDTO>> obtenerPorPedido(@PathVariable Integer idPedido) {

        List<TransaccionDTO> lista = transaccionService.obtenerTransaccionesPorPedido(idPedido)
                .stream()
                .map(this::convertirADTO)
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(
            @PathVariable Integer id,
            @RequestParam String estado) {

        Transaccion.EstadoPago estadoEnum = Transaccion.EstadoPago.valueOf(estado.toUpperCase());
        Transaccion t = transaccionService.cambiarEstado(id, estadoEnum);
        return ResponseEntity.ok(convertirADTO(t));
    }

    @PatchMapping("/{id}/completar")
    public ResponseEntity<?> completar(@PathVariable Integer id) {

        Transaccion t = transaccionService.marcarComoExitosa(id);
        return ResponseEntity.ok(convertirADTO(t));
    }

    @PatchMapping("/{id}/fallar")
    public ResponseEntity<?> fallar(@PathVariable Integer id) {

        Transaccion t = transaccionService.marcarComoFallida(id);
        return ResponseEntity.ok(convertirADTO(t));
    }
}
