package metricas;

import modelo.LineaPedido;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TicketPromedio implements Metrica {

    @Override
    public String nombre() {
        return "Ticket Promedio por Pedido";
    }

    @Override
    public String calcular(List<LineaPedido> datos) {
        // Agrupamos por pedido único y obtenemos su total
        Map<String, Double> totalesPorPedido = datos.stream()
            .collect(Collectors.toMap(
                LineaPedido::numeroPedido,
                LineaPedido::totalPedido,
                (total1, total2) -> total1 // Si se repite, conservamos el primero
            ));

        double promedio = totalesPorPedido.values().stream()
            .mapToDouble(Double::doubleValue)
            .average()
            .orElse(0.0);

        return String.format("   - Promedio: $%.2f por pedido (basado en %d pedidos)", promedio, totalesPorPedido.size());
    }
}