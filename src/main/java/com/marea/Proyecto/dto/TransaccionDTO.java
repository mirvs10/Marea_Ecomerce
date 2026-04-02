package com.marea.Proyecto.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public class TransaccionDTO {
    
    private Integer idTransaccion;
    
    @NotNull(message = "El ID del pedido es obligatorio")
    private Integer idPedido;
    
    private Integer idMetodoPago;
    
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    private Double monto;
    
    private LocalDateTime fechaPago;
    private String estadoPago;
    
    private String nombreMetodoPago;

    public TransaccionDTO() {}

    public Integer getIdTransaccion() { return idTransaccion; }
    public void setIdTransaccion(Integer idTransaccion) { this.idTransaccion = idTransaccion; }
    
    public Integer getIdPedido() { return idPedido; }
    public void setIdPedido(Integer idPedido) { this.idPedido = idPedido; }
    
    public Integer getIdMetodoPago() { return idMetodoPago; }
    public void setIdMetodoPago(Integer idMetodoPago) { this.idMetodoPago = idMetodoPago; }
    
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    
    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }
    
    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }
    
    public String getNombreMetodoPago() { return nombreMetodoPago; }
    public void setNombreMetodoPago(String nombreMetodoPago) { this.nombreMetodoPago = nombreMetodoPago; }
}