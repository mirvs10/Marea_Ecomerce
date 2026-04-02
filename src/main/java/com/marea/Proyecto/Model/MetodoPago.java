package com.marea.Proyecto.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "metodopago")
public class MetodoPago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer idPago;
    
    @Column(name = "nombre_metodo")
    private String nombreMetodo;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    
    @OneToMany(mappedBy = "metodoPago", cascade = CascadeType.ALL)
    private List<Transaccion> transacciones;
    
   
    
    public Integer getIdPago() {
        return idPago;
    }
    
    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }
    
    public String getNombreMetodo() {
        return nombreMetodo;
    }
    
    public void setNombreMetodo(String nombreMetodo) {
        this.nombreMetodo = nombreMetodo;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public List<Transaccion> getTransacciones() {
        return transacciones;
    }
    
    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }
}