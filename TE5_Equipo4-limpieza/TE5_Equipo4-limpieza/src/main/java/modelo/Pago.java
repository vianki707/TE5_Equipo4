package modelo;

import java.time.LocalDateTime;

public record Pago(
        String referencia,        
        double monto,
        String estado,            
        String codAutorizacion,   
        String motivoRechazo,     
        LocalDateTime fechaHora
) {}
