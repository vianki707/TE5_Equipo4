package modelo;

import java.time.LocalDateTime;
import java.util.List;

public record LineaPedido(
        String numeroPedido,           
        int numeroLinea,              
        int platoId,
        int cantidad,
        double precioUnitario,         
        double totalPedido,            
        LocalDateTime registradoEn,
        List<CambioEstado> historial   
) {}
