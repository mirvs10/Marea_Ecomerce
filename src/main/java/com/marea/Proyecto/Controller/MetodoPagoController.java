package com.marea.Proyecto.Controller;

import com.marea.Proyecto.dto.MetodoPagoDTO;
import com.marea.Proyecto.Model.MetodoPago;
import com.marea.Proyecto.Services.MetodoPagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metodos-pago")
@CrossOrigin(origins = "*")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService metodoPagoService;

    private MetodoPagoDTO convertirADTO(MetodoPago entidad) {
        MetodoPagoDTO dto = new MetodoPagoDTO();
        dto.setIdPago(entidad.getIdPago());
        dto.setNombreMetodo(entidad.getNombreMetodo());
        dto.setIdUsuario(entidad.getUsuario().getIdUsuario());
        dto.setNombreUsuario(entidad.getUsuario().getNombre());
        return dto;
    }

    @PostMapping
    public ResponseEntity<?> crearMetodoPago(@Valid @RequestBody MetodoPagoDTO dto) {
        try {
            MetodoPago metodoPago = metodoPagoService.crearMetodoPago(
                    dto.getIdUsuario(),
                    dto.getNombreMetodo()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(metodoPago));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        List<MetodoPagoDTO> dtos = metodoPagoService.obtenerMetodosPorUsuario(idUsuario)
                .stream()
                .map(this::convertirADTO)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        MetodoPago metodoPago = metodoPagoService.obtenerPorId(id);
        return ResponseEntity.ok(convertirADTO(metodoPago));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody MetodoPagoDTO dto) {
        MetodoPago actualizado = metodoPagoService.actualizarMetodoPago(id, dto.getNombreMetodo());
        return ResponseEntity.ok(convertirADTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        metodoPagoService.eliminarMetodoPago(id);
        return ResponseEntity.ok("Método eliminado");
    }
}
