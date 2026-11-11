package modelo;

import java.time.LocalDateTime;

/** Un paso del historial de estados de un pedido (por ejemplo PAGADO a las 11:37:29). */
public record CambioEstado(String estado, LocalDateTime fechaHora) {}
