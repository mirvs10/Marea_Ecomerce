package com.marea.Proyecto.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer idPedido;  

    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido = LocalDateTime.now();  
    @Column(name = "total")
    private Double total;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado = Estado.PENDIENTE;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_direccion")
    private DireccionEntrega direccion;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<DetallePedido> detalles;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Transaccion transaccion;

    public enum Estado {
        PENDIENTE, ENVIADO, ENTREGADO, CANCELADO
    }

    public Integer getIdPedido() { 
        return idPedido; 
    }
    
    public void setIdPedido(Integer idPedido) { 
        this.idPedido = idPedido; 
    }
    
    public LocalDateTime getFechaPedido() { 
        return fechaPedido; 
    }
    
    public void setFechaPedido(LocalDateTime fechaPedido) { 
        this.fechaPedido = fechaPedido; 
    }
    
    public Double getTotal() { 
        return total; 
    }
    
    public void setTotal(Double total) { 
        this.total = total; 
    }
    
    public Estado getEstado() { 
        return estado; 
    }
    
    public void setEstado(Estado estado) { 
        this.estado = estado; 
    }
    
    public Usuario getUsuario() { 
        return usuario; 
    }
    
    public void setUsuario(Usuario usuario) { 
        this.usuario = usuario; 
    }
    
    public DireccionEntrega getDireccion() { 
        return direccion; 
    }
    
    public void setDireccion(DireccionEntrega direccion) { 
        this.direccion = direccion; 
    }
    
    public List<DetallePedido> getDetalles() { 
        return detalles; 
    }
    
    public void setDetalles(List<DetallePedido> detalles) { 
        this.detalles = detalles; 
    }
    
    public Transaccion getTransaccion() { 
        return transaccion; 
    }
    
    public void setTransaccion(Transaccion transaccion) { 
        this.transaccion = transaccion; 
    }
}