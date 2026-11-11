package modelo;

import java.time.LocalDateTime;

/** Un intento de pago de la pasarela. Un mismo pedido puede tener varios intentos. */
public record Pago(
        String referencia,        // número del pedido al que corresponde (REFERENCIA)
        double monto,
        String estado,            // APROBADA, RECHAZADA o SIN_RESPUESTA
        String codAutorizacion,   // vacío si no fue aprobada
        String motivoRechazo,     // vacío si fue aprobada
        LocalDateTime fechaHora
) {}
