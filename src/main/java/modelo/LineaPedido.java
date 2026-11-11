package modelo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Una línea es un plato dentro de un pedido.
 * Además de los datos del plato, cada línea lleva los datos de su pedido
 * (total, hora de registro e historial) para que los filtros puedan trabajar
 * solo con List<LineaPedido>, como pide el enunciado.
 */
public record LineaPedido(
        String numeroPedido,           // "P-00001": identifica al pedido
        int numeroLinea,               // posición de la línea dentro del pedido (1, 2, 3...)
        int platoId,
        int cantidad,
        double precioUnitario,         // precio al momento del pedido (regla 6)
        double totalPedido,            // total del pedido completo (se repite en cada línea)
        LocalDateTime registradoEn,
        List<CambioEstado> historial   // historial de estados del pedido
) {}
